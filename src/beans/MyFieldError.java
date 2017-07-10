package beans;

import org.springframework.validation.FieldError;

/**
 * Created by Shinelon on 2017/7/8.
 */
public class MyFieldError {
    private String fieldName;
    private String message;

    public MyFieldError(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public MyFieldError(FieldError fieldError){
        if(fieldError!=null) {
            fieldName = fieldError.getObjectName();
            message=fieldError.getDefaultMessage();
        }
    }
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
