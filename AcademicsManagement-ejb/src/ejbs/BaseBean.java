package ejbs;

import dtos.DTO;
import exceptions.EntityDoesNotExistException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import javax.ejb.EJBException;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BaseBean<E extends Serializable, D extends DTO> {       
    @PersistenceContext
    protected EntityManager em;
    
    protected ModelMapper mapper;
    
    public BaseBean() {
        mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }
    
    public D create(D dto) {
        try{
            E entity = toEntity(dto);
            entity = create(entity);
            return toDTO(entity);
        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }
    }
    
    public D update(D dto) throws EntityDoesNotExistException {
        E entity = toEntity(dto);
        entity = update(entity);
        return toDTO(entity);
    }
    
    protected E create(E entity) throws exceptions.EntityExistsException {       
        if (exists(entity)) {
            throw new exceptions.EntityExistsException("Entity already exists");
        }
        
        em.persist(entity);
        
        return entity;
    }
    
    protected E update(E entity) throws EntityDoesNotExistException {
        if (! exists(entity)) {
            throw new exceptions.EntityDoesNotExistException("Entity does not exists");
        }
        
        em.merge(entity);
        
        return entity;
    }
    
    public D createOrUpdate(D dto) throws EntityDoesNotExistException, exceptions.EntityExistsException {
        return toDTO(createOrUpdate(toEntity(dto)));
    }
    
    protected E createOrUpdate(E entity) throws EntityDoesNotExistException, exceptions.EntityExistsException {
        return exists(entity) ? update(entity) : create(entity);
    }
    
    private Class<E> getGenericEntityClass() {
        // 1. Get the generic type passed as argument (some E that is a Serializable)
        ParameterizedType genericType = (ParameterizedType) this.getClass().getGenericSuperclass();
        
        // 2. return the cast of E Type to E Class
        return (Class<E>) genericType.getActualTypeArguments()[0];
    }
    
    private Class<D> getGenericDTOClass() {
        // 1. Get the generic type passed as argument (some D that is a DTO)
        ParameterizedType genericType = (ParameterizedType) this.getClass().getGenericSuperclass();
        
        // 2. return the cast of D Type to D Class
        return (Class<D>) genericType.getActualTypeArguments()[1];
    }
    
    private <A extends Annotation, S extends Serializable> Stream<A> getAnnotations(@NotNull Class<S> tClass, @NotNull Class<A> aClass) {
        return Arrays.stream(tClass.getAnnotationsByType(aClass));
    }
    
    private <S extends Serializable> Stream<NamedQuery> getNamedQueriesForEntity(@NotNull Class<S> entityClass) {
        // 3. Get the @NamedQuery annotations of E
        Stream<NamedQuery> nq = getAnnotations(entityClass, NamedQuery.class);
        
        // 4. check also for @NamedQueries annotations.
        Stream<NamedQuery> nq2 = getAnnotations(entityClass, NamedQueries.class).map(NamedQueries::value).flatMap(Arrays::stream);
        
        return Stream.concat(nq, nq2);
    }
   
    public <S extends Serializable> boolean containsNamedQuery(@NotNull Class<S> entityClass, @NotNull String namedQuery) {
        Stream<NamedQuery> namedQueries = getNamedQueriesForEntity(entityClass);
        return namedQueries.map(NamedQuery::name).anyMatch(namedQuery::equals);
    }
    
    public <S extends Serializable> S find(@NotNull Class<S> resultClass, @NotNull Object primaryKey) {
        return em.find(resultClass, primaryKey);
    }
    
    public E find(@NotNull Object primaryKey) {
        return find(getGenericEntityClass(), primaryKey);
    }
        
    public <S extends Serializable> S findOrFail(@NotNull Class<S> resultClass, @NotNull Object primaryKey) {
        S entity = find(resultClass, primaryKey);
        
        if (entity == null) {
            String entityName = resultClass.getSimpleName();
            String pkName = getPrimaryKeyField(resultClass).getName();
            throw new EntityNotFoundException("Record not found in " + entityName + " for " + pkName + " = " + primaryKey);
        }
        
        return entity;
    }
    
    public E findOrFail(@NotNull Object primaryKey) {
        return findOrFail(getGenericEntityClass(), primaryKey);
    }
    
    private <S extends Serializable> String buildQueryEntityExistence(Class<S> entityClass) {
        String entityName = entityClass.getSimpleName();
        String pkName = getPrimaryKeyField(entityClass).getName();
        
        return buildQueryEntityExistence(entityName, pkName);
    }
        
    private String buildQueryEntityExistence(String entityName, String pkName) {
        return String.format("SELECT COUNT(e) FROM %1$s e WHERE e.%2$s = :%2$s", entityName, pkName);
    }
    
    public <S extends Serializable> boolean exists(@NotNull Class<S> entityClass, Object primaryKey) {
        String entityName = entityClass.getSimpleName();
        String pkName = getPrimaryKeyField(entityClass).getName();
        
        Query query = em.createQuery(buildQueryEntityExistence(entityName, pkName), Long.class);
        query = query.setParameter(pkName, primaryKey);
        
        return (Long) query.getSingleResult() > 0;
    }
    
    public <S extends Serializable> boolean exists(@NotNull S entity) {
        try {
            Class<? extends Serializable> entityClass = entity.getClass();
            String pkName = getPrimaryKeyField(entityClass).getName();

            String pkGetter = "get" + String.valueOf(pkName.charAt(0)).toUpperCase() + pkName.substring(1);

            Object primaryKey = entityClass.getMethod(pkGetter).invoke(entity);
            
            return exists(entityClass, primaryKey);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            return false;
        }
    }
    
    public <S extends Serializable> boolean exists(Object primarKey) {
        return exists(getGenericEntityClass(), primarKey);
    }
    
    protected E toEntity(D dto) {
        Class<E> entityClass = getGenericEntityClass();
        return mapper.map(dto, entityClass);
    }
    
    protected List<E> toEntities(List<D> dtos) {
        // Type listType = new TypeToken<List<E>>() {}.getType();
        // return mapper.map(dtos, listType);

        Class<E> entityClass = getGenericEntityClass();
        return dtos.parallelStream().map(d -> mapper.map(d, entityClass)).collect(Collectors.toList());
    }
    
    protected D toDTO(E entity) {
        return mapper.map(entity, getGenericDTOClass());
    }
    
    protected List<D> toDTOs(List<E> entities) {
        // Type listType = new TypeToken<List<D>>() {}.getType();
        // return mapper.map(entities, listType);
        
        Class<D> dtoClass = getGenericDTOClass();
        return entities.parallelStream().map(e -> mapper.map(e, dtoClass)).collect(Collectors.toList());
    }
        
    public List<D> getAll() {
        Class<E> entityClass = getGenericEntityClass();
        
        // 5. Compute the default query given the simple class name of E
        String genericClassName = entityClass.getSimpleName();
        String namedQuery = "getAll" + genericClassName + "s";
       
        // 6. If that NamedQuery is present in Entity, then we should use the default behaviour to getAll<GenericEntity>
        if (containsNamedQuery(entityClass, namedQuery)) {
            // With this, we ensure we respect, for instance, the business logic or simply the query order, for example.
            return toDTOs(em.createNamedQuery(namedQuery, entityClass).getResultList());
        }
        
        // 7. In case the default named query wasn't found or isn't set,
        // we assume the default app behaviour, query all from the generic Entity.
        String queryAll = String.format("SELECT entity FROM %s entity", genericClassName);
        return toDTOs(em.createQuery(queryAll, entityClass).getResultList());
    }
    
    private <S> List<Field> getAllDeclaredFields(Class<S> resultClass) {
        List<Field> fields = new LinkedList<>();
        
        Class<?> parentClass = resultClass.getSuperclass();
        
        if (parentClass != null) {
            fields.addAll(getAllDeclaredFields(parentClass));
        }
        
        fields.addAll(Arrays.asList(resultClass.getDeclaredFields()));
        
        return fields;
    }
    
    private <S extends Serializable> Field getPrimaryKeyField(Class<S> entityClass) {
        List<Field> fields = getAllDeclaredFields(entityClass);
        Predicate<Field> isPrimaryKey = field -> field.getAnnotationsByType(Id.class).length > 0;
        
        return fields.stream().filter(isPrimaryKey).findFirst().get();
    }
    
    public E remove(@NotNull Object primaryKey) {
        try {
            Class<E> resultClass = getGenericEntityClass();
            E entity = findOrFail(primaryKey);        
            
            em.refresh(entity);
            em.remove(entity);
            
            return entity;
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }
}
