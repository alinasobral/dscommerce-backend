package com.devsuperior.dscommerce.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(columnDefinition = "TEXT")/*Aqui configuramos que na hora que essa coluna for mapeada
    para o banco de dados, ela será do tipo TEXT. Como geralmente é colocado varchar(255) automaticamente
    pelo hibernate, então mudamos para TEXT porque a descrição pode ser maior do que 255 caracteres.*/
    private String description;
    private Double price;
    private String imgUrl;

    //RELAÇAO MUITOS PARA MUITOS - PRODUCT E CATEGORY
    /*Como na relação M:N criamos uma terceira tabela que fica entre as duas principais, aqui também temod que
    fazer isso. Então a anotation @JoinTable serve para juntar as tabelas. Então o primeiro nome é desse terceira
    tabela, depois no joinColumns colocamos o nome da tabela desta classe e no inverseJoinColumns colocamos
    o nome da outra tabela da relação.*/
    @ManyToMany
    @JoinTable(name = "tb_product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();/*Aqui usamos o Set/HashSet, pois os ids de products
     e categories não podem se repetir. Então para fazer a lista usamos Set/HashSet e não List/ArrayList.*/

    @OneToMany(mappedBy = "id.product") //associa ao OrderItem através da chave id.product que está dentro de OrderItemPK
    private Set<OrderItem> items = new HashSet<>();

    public Product() {
    }

    public Product(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public List<Order> getOrders() {
        return items.stream().map(x -> x.getOrder()).toList();
    }
}
