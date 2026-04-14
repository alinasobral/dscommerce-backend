package com.devsuperior.dscommerce.dto;


import com.devsuperior.dscommerce.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/*O pacote dto foi criado para que seja possível a conversão dos
dados vindos do bancos sejam convertidos no formato DTO.*/
public class ProductDTO {

    private Long id;

    @Size(min = 3, max = 80, message = "Nome precisa ter de 3 a 80 caracteres") //Essa é uma anotation de validação do jakarta, ela leva como argumento três métodos que é o min que define um tamanho mínimo, o método max que define o tamanho máximo e o método message que exibe uma mensagem de erro ao usuário.
    @NotBlank(message = "Campo requerido") //Essa é uma anotation de validação do jakarta, ela impede que o atributo seja nulo e que sejam colocados apenas espaços em branco, aceitando apenas sequências de caracteres. Essa anotation pode levar como parâmetro um método, o que usamos é o message, que serve para mostrar uma mensagem de erro ao usuário.
    private String name;

    @Size(min = 10, message = "Descrição precisa ter no mínimo 10 caracteres") //Essa é uma anotation de validação do jakarta, ela leva como argumento dois métodos que é o min que define um tamanho mínimo e o método message que exibe uma mensagem de erro ao usuário.
    private String description;

    @Positive(message = "O preço deve ser positivo") //Essa é uma anotation de validação do jakarta. Ela serve para permitir que sejam inseridos apenas valores positivos. Usamos o método message como parâmetro para exibir uma mensagem de erro ao usuário.
    private Double price;
    private String imgUrl;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    /*Esse construtor foi criado para que o novo objeto dto
    * instaciado em ProductService pegue esses dados do banco
    de dados. Essa é a forma manual, ela também pode ser feita com
    set ao invés do construtor, aí poderia colocar o set aqui nessa
    classe.*/
    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
    }


    //DTO não precisa ter set, porque os dados não vão ser alterados

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
