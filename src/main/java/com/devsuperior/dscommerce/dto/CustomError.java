package com.devsuperior.dscommerce.dto;

import java.time.Instant;

/*Essa classe foi criada para colocar os atributos que são retornados
em uma consulta no formato JSON para o usuário. Ela será usada para
instanciar um objeto dela dentro do método que irá tratar o erro de
ResourceNotFoundException, que é o erro de recurso não encontrado.*/

public class CustomError {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;

    public CustomError(Instant timestamp, Integer status, String error, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }
}
