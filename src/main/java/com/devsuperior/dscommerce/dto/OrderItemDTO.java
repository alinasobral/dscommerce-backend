package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.OrderItem;

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

PASSO 3: Aqui criamos a classe do terceiro objeto aninhado que é o items, então criamos
a classe OrderItemtDTO que terá os atributos de id, nome, preço e quantidade, o subtotal
criaremos um método getSubtotal e dentro colocaremos o cáculo de multiplicação da quanti-
dade pelo preço do produto. Colocamos o construtor vazio, o padrão e o da entidade OrderItem,
alé dos gets. No construtor da entidade no id usamos o getProduct e depois o getId, faremos
o mesmo para name, price e quantity podemos fazer direto o getPrice e o getQuantity.
Depois de termos criado todas as classes DTO referentes aos objetos aninhados, vamos cri-
ar a classe o pedido chamada de OrderDTO.*/
public class OrderItemDTO {
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;

    public OrderItemDTO() {
    }

    public OrderItemDTO(Long id, String name, Double price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderItemDTO(OrderItem entity) {
        id = entity.getProduct().getId();
        name = entity.getProduct().getName();
        price = entity.getPrice();
        quantity = entity.getQuantity();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getSubtotal() {
        return price * quantity;
    }
}
