package com.devsuperior.dscommerce.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/*Essa classe foi criada para fazer uma lista das mensagens de erro de validação dos campos em que colocamos as valicações. Ela subclasse da superclasse CustomError*/
public class ValidationError extends CustomError{

    private List<FieldMessage> errors = new ArrayList<>();


    public ValidationError(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    //Método para adicinar mensagens a lista:
    public void addError(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }
}
