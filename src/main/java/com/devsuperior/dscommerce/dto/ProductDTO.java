package com.devsuperior.dscommerce.dto;


import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO {

    private Long id;

    @Size(min = 3, max = 80, message = "Nome precisa ter de 3 a 80 caracteres")
    @NotBlank(message = "Campo requerido")
    private String name;

    @Size(min = 10, message = "Descrição precisa ter no mínimo 10 caracteres")
    private String description;

    @Positive(message = "O preço deve ser positivo")
    private Double price;
    private String imgUrl;

    /*No caso de uso, para inserir um novo produto ou atualiza um produto, o admin deve
    informar o nome, preço, descrição, imagem e categoria do produto e, também na área de
    carrinho do e-commerce as categorias também devem aparecer, que no caso é quando se faz
    a busca por Id. Então aqui no ProductDTO devemos criar uma lista de categorias para
    que essa exigência do caso de uso seja atendida. Só que, essas categorias devem ser
    em formato DTO, então antes de criar a lista devemos criar a classe CategoryDTO.
    Após criar a classe CategoryDTO voltamos para cá para criar a lista de categorias.
    Depois criamos o método getter dessa lista e alteramos o construtor da entidade.
    No caso de uso, também diz que cada produto deve te pelo menos uma categoria, então
    acima da lista colocamos a annotation de validação @NotEmpty, para dizer que a lista
    não pode estar vazia. Depois de fazer as alterações aqui no ProductDTO, vamos até
    o ProductService e alteramos o método copyDtoToEntity.*/
    @NotEmpty(message = "Deve ter pelo menos uma categoria")
    List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO() {
    }
    /*O construtor padrão não levará as categorias, mas podemos com getCategories adi-
    cionar elementos a lista.*/
    public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    /*Aqui no construtor da entidade fazemos um for para percorrer, copiar as categorias
    da entidade e colocar na lista de categorias dto. Para cada elemento Category chamado
    de cat que existe dentro da lista categories (entity.getCategories()), usamos o .add
    para adicionar na lista categories um novo objeto CategoryDTO pegando da entidade Ca-
    tegory através da variável cat. Então conseguimos copiar não só os dados básicos, mas
    também as categorias, criando dtos de categoria associados ao produto.*/
    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
        for(Category cat : entity.getCategories()) {
            categories.add(new CategoryDTO(cat));
        }
    }

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

    public List<CategoryDTO> getCategories() {
        return categories;
    }
}
