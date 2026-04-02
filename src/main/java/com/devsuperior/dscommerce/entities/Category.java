package com.devsuperior.dscommerce.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_caterogy")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "categories")//Quer dizer que está sendo mapeado pela lista categories que está na classe Product.
    private Set<Product> products = new HashSet<>();/*(Definição de produtos associados a categoria) Aqui usamos
     o Set/HashSet, pois os ids de products e categories não podem se repetir. Então para fazer a lista usamos
     et/HashSet e não List/ArrayList.*/

    public Category() {
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public Set<Product> getProducts() {
        return products;
    }
}
