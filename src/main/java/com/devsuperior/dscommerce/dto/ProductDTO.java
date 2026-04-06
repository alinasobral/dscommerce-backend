package com.devsuperior.dscommerce.dto;


import com.devsuperior.dscommerce.entities.Product;

/*O pacote dto foi criado para que seja possível a conversão dos
dados vindos do bancos sejam convertidos no formato DTO.*/
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
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
