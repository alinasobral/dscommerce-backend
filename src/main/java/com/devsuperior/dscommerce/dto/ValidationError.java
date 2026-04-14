package com.devsuperior.dscommerce.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/*
 * Classe que representa um erro de validação completo da requisição.
 *
 * 📌 Por que essa classe foi criada?
 * Quando há erro de validação, não basta retornar apenas uma mensagem genérica.
 * Precisamos informar TODOS os erros de cada campo para o cliente (frontend).
 *
 * Por isso, essa classe:
 * - herda de CustomError (erro geral)
 * - adiciona uma lista de erros de campos
 *
 * 📌 Estrutura:
 * - dados gerais (herdados de CustomError):
 *   timestamp, status, mensagem, path
 *
 * - lista de erros específicos:
 *   List<FieldMessage> errors
 *
 * 📌 Exemplo de resposta JSON:
 * {
 *   "timestamp": "...",
 *   "status": 422,
 *   "error": "Dados inválidos",
 *   "path": "/products",
 *   "errors": [
 *     {
 *       "fieldName": "name",
 *       "message": "Campo obrigatório"
 *     },
 *     {
 *       "fieldName": "email",
 *       "message": "Email inválido"
 *     }
 *   ]
 * }
 *
 * 📌 Método addError:
 * Serve para adicionar novos erros na lista de forma simples,
 * evitando ter que instanciar manualmente FieldMessage toda vez.
 *
 * Exemplo:
 * err.addError("email", "Email inválido");
 *
 * 📌 Por que ela herda de CustomError?
 * Porque um erro de validação ainda é um erro HTTP comum,
 * mas com informações extras (os campos com erro).
 *
 * 📌 Conceito importante:
 * Aqui temos um "erro composto":
 * um erro geral + vários erros menores dentro dele.
 */
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
