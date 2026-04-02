package com.devsuperior.dscommerce.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")//Serve para salvar no banco de dados o Instant no padrão UTC
    private Instant moment;
    private OrderStatus status;
    //LADO DO MUITOS (MUITOS PARA UM/UM PARA MUITOS) - RELAÇAO ORDER E USER
    @ManyToOne //Estabelece a relação muitos para um
    @JoinColumn(name = "client_id") //Estabelece o nome da chave estrangeira
    private User client;

    //UM PARA UM: LADO UM NAO DEPENDENTE - RELAÇAO ORDER E PAYMENT
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    @OneToMany(mappedBy = "id.order") //associa ao OrderItem através da chave id.order que está dentro de OrderItemPK
    private Set<OrderItem> items = new HashSet<>();

    public Order() {
    }

    public Order(Long id, Instant moment, OrderStatus status, User client, Payment payment) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.client = client;
        this.payment = payment;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {

        this.moment = moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public List<Product> getProducts() {
        return items.stream().map(x -> x.getProduct()).toList();
    }
    /*O método getProducts acima serve para pegar os items do tipo OrderItem acima e converter
    para product através do os métodos stream().mpa(). Ou seja para cada x do OrderItem eu transformo
    em x.getProduct, com isso será construída uma lista de Product e não mais de OrderItem. Depois uso o
    método .toList() para transformar em lista novamente.*/

    /*Os métodos equals e hashCode servem para comparar uma entidade com a outra, nessa classe usamos o id*/
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
