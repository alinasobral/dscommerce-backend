package com.devsuperior.dscommerce.dto;

/*
 * Classe auxiliar (DTO) que representa um erro de validação de UM campo específico.
 *
 * 📌 Por que essa classe existe?
 * Quando ocorre um erro de validação (ex: @NotBlank, @Email),
 * podem existir vários erros ao mesmo tempo, cada um relacionado a um campo diferente.
 *
 * Exemplo:
 * - campo "name" vazio
 * - campo "email" inválido
 *
 * Então precisamos de uma estrutura para representar CADA erro individualmente.
 *
 * 📌 O que essa classe guarda?
 * - fieldName → nome do campo que deu erro
 * - message → mensagem de erro daquele campo
 *
 * 📌 Exemplo de uso no JSON:
 * {
 *   "fieldName": "email",
 *   "message": "Email inválido"
 * }
 *
 * 📌 Importante:
 * Essa classe NÃO representa o erro da requisição inteira,
 * apenas um erro específico de um campo.
 *
 * 📌 Conceito importante:
 * Segue o princípio de responsabilidade única (SRP),
 * pois cada objeto representa apenas UM erro.
 */
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
