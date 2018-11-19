package web;

import java.io.Serializable;
import java.util.Arrays;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("usernameValidator")
public class UsernameValidator implements Validator, Serializable {

    private static final Logger logger = Logger.getLogger("web.usernameValidator");

    private String[] invalidUsernames = new String[] {
        "xpto",
        "admin",
        "user",
        "foo",
        "bar",
        "baz",
    };
    
    @Override
    public void validate(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
        try {
            //Your validation code goes here
            String username = (String) value;
            
            //If the validation fails
            if (Arrays.stream(invalidUsernames).anyMatch(username::startsWith)) {
                FacesMessage message = new FacesMessage("Error: invalid username.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                context.addMessage(toValidate.getClientId(context), message);
                ((UIInput) toValidate).setValid(false);
            }
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unkown error.", logger);
        }
    }
}
