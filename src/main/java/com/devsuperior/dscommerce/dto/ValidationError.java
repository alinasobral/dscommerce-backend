package com.devsuperior.dscommerce.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends CustomError{

    private List<FieldMessage> errors = new ArrayList<>();


    public ValidationError(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    /*Foi acrescentado a primeira linha que basicamente impede que apareçam duas
    mensagens diferentes para um mesmo elemento (FieldName), pois o prof disse
    que no frontend os erros são tratados um de cada vez, então esse método ajuda
    nisso. Então usamos a lista erros instanciada e junto com ela usamos o mé-
    todo .removeIf que tem a seguinte lógica: x representa a FieldMessage (men-
    sagem de erro) então pedimos para que a FieldMessage seja removida se o
    FieldName atribuído a essa FielMessage seja igual ao FieldName do parâmetro
    do método. Depois da mensagem de erro que já existia ser removida, só aí a
    nova mensagem é colocada.*/
    public void addError(String fieldName, String message) {
        errors.removeIf(x -> x.getFieldName().equals(fieldName));
        errors.add(new FieldMessage(fieldName, message));
    }
}
