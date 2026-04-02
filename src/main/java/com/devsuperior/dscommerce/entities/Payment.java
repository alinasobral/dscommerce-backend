package com.devsuperior.dscommerce.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "tb_payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant moment;

    /*UM PARA UM: CLASSE DEPENDENTE. Pelo diagrama de  classes, a classe
    payment depende da classe order, pois um pedido pode não ter pagamento,
    mas um pagamento precisa ter um pedido. Então a multiplicidade é 0..1, ou
    seja, o mínimo é 0 e o máximo é um, pois ou não existe nenhum pagamento para
    o pedido ou existe um pagamento para o pedido.*/
    @OneToOne
    @MapsId //O id do payment vai ser o mesmo de order
    private Order order;

    public Payment() {
    }

    public Payment(Long id, Instant moment, Order order) {
        this.id = id;
        this.moment = moment;
        this.order = order;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
