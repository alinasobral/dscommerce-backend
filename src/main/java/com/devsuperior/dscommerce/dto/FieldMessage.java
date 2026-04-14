package com.devsuperior.dscommerce.dto;

/*Essa classe foi criada para personalizar a mensagem de exceções de validação. Aqui criamos dois atributos que vão ser o nome do campo e a mensagem.*/
public class FieldMessage {
    private String fieldName;
    private String message;

    public FieldMessage(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }
}
