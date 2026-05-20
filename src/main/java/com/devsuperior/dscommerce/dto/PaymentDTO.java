package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Payment;

import java.time.Instant;

/*CRIAÇAO DE RESUMO DE UMA COMPRA: No caso de uso, o último item pedido é o cenário de
Registrar pedido, sendo que o cenário principal de sucesso junta os dados do carrinho
de compras e o id do pedido. Mas, o prof nélio disse que é uma boa prática ver além do
que é pedido no projeto, desde que claro seja conversado com todos da equipe, então aqui
ele adicionou mais coisas nesse resumo do pedido que será além do carrinho de compras
(que deve ter as informações Id do produto, nome do produto, preço do produto, quantidade
e subtotal) e o Id do pedido, ele fez o seguinte: o objeto json será o pedido que terá
o id do pedido, o momento do pedido, o status do pedido, um objeto aninhado cliente que
terá dentro dele o id do cliente e o nome do cliente, um objeto aninhado pagamento que
terá dentro dele o id do pagamento e o momento do pagamento, um objeto aninhado items
que terá dentro dele o id do produto, o nome do produto, o preço do produto, a quantida-
de e o subtotal, e finalizando o objeto json terá o total do pedido.

PASSO 2: Aqui criamos a classe do segundo objeto aninhado que é o pagamento, então criamos
a classe PaymentDTO que terá os atributos de id e momento do pagamento. Colocamos o cons-
trutor vazio, o padrão e o da entidade Payment, alé dos gets.*/
public class PaymentDTO {
    private Long id;
    private Instant moment;

    public PaymentDTO() {
    }

    public PaymentDTO(Long id, Instant moment) {
        this.id = id;
        this.moment = moment;
    }

    public PaymentDTO(Payment entity) {
        id = entity.getId();
        moment = entity.getMoment();
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }
}
