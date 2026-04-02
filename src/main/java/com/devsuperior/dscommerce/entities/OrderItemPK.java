package com.devsuperior.dscommerce.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/*Essa classse é criada, pois o relacionamento Muitos-para-muitos entre Order e Product criou uma terceira tabela
que tem mais dois atributos próprios. Então essa terceira tabela terá chave primária composta (pela chave da tabela
order e pela chave da tabela product. Por isso, essa classe OrderItemPK é criada para colocar as chaves primárias de
order e product(as quais se tornaram chaves estrangeiras) e ela será "chamada" na terceira tabela OrderItem.*/
@Embeddable //significa que essa classe não será uma tabela real, ela vai estar dentro de outra tabela. É parte de outa entidade.
public class OrderItemPK {

    @ManyToOne
    @JoinColumn(name = "order_id") //Chave estrangeira de order
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id") //Chave estrangeira de product
    private Product product;

    public OrderItemPK() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
