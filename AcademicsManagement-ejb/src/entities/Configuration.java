package entities;

/*Entidade Configuração:
	uma descrição, o seu estado (ativa, inativa, suspensa), o nome do software, a sua versão de base,
	os módulos que foram configurados/adicionados, o hardware/serviços de cloud utilizados, as
	licenças ativadas, as parametrizações e extensões realizadas e os dados do contrato de
	manutenção. Outras características a prever nestas configurações incluem a indicação dos
	repositórios dos artefactos do produto de software, como por exemplo do código fonte, das bases
	de dados e dos scripts e bibliotecas utilizadas. Será ainda necessário registar, para cada
	configuração, o conjunto de materiais de apoio ao utilizador e ao programador, como por exemplo
	manuais de utilizador/programador, tutoriais, links para os sites em produção, vídeos, artigos de
	conhecimento ou outros documentos de apoio.
	*/

import javax.validation.constraints.NotNull;

public class Configuration {


    private String descricao;

    @NotNull
    private Estado estado;

    @NotNull
    private String nomeSoftware;

    @NotNull
    private String versaoBase;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getNomeSoftware() {
        return nomeSoftware;
    }

    public void setNomeSoftware(String nomeSoftware) {
        this.nomeSoftware = nomeSoftware;
    }

    public String getVersaoBase() {
        return versaoBase;
    }

    public void setVersaoBase(String versaoBase) {
        this.versaoBase = versaoBase;
    }
//modulos, hardware, licenças, extensões - lista? string?



}
