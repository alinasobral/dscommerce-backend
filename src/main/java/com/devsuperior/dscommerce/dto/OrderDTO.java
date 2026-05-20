package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.entities.OrderItem;
import com.devsuperior.dscommerce.entities.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

PASSO 4: Aqui criamos a classe principal do pedido que terá os três objetos aninhados den-
tro dela que é a classe do pedido, então criamos a classe OrderDTO que terá os atributos
próprios de id, momento do pedido e status do pedido. Como terá objetos aninhados, então
instanciamos os objetos client, payment e a lista de items. Colocamos o construtor vazio,
o padrão que não levará a lista e o da entidade Order, além dos gets e no final um método
getTotal para somar todos os subtotais contidos na lista items.
Depois de criar todas as classes DTO, iremos criar a interface OrderRepository.*/
public class OrderDTO {
    private Long id;
    private Instant moment;
    private OrderStatus status;

    /*Instanciando objetos aninhados*/
    private ClientDTO client;
    private PaymentDTO payment;

    @NotEmpty(message = "Deve ter pelo menos um item")
    private List<OrderItemDTO> items = new ArrayList<>();

    public OrderDTO() {
    }

    public OrderDTO(Long id, Instant moment, OrderStatus status, ClientDTO client, PaymentDTO payment) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.client = client;
        this.payment = payment;
    }

    /*No construtor da entidade, na classe que se refere ao primeiro objeto aninhado que
    é client, instanciamos um novo objeto ClientDTO passando como parâmetro o entity.get-
    Client para pegar o id e nome do construtor da entidade User. Na classe que se refe-
    ao segundo objeto aninhado que é payment, usamos o operado ternário, pois o pagamento
    pode não ter sido feito então com o operador ternário podemos colocar a condição que
    se o payment for nulo ele irá receber null, do contrário ele receberá um novo objeto
    PaymentDTO passando como parâmetro o entity.getPayment para pegar o id e momento do
    construtor da entidade Payment. Para a classe que se refere ao terceiro objeto ani-
    nhado que é items, usamos um for pois se trata de uma lista, então a lógica do for
    é para cada OrderItem item que exista na lista do tipo DTO items(entity.getItems)
    criamos um objeto OrderItemDTO chamado de itemDto que recebe um novo OrderItemDTO
    passando como parâmetro a entidade item que será copiada para o tipo dto, depois adi-
    cionamos cada objeto criado na lista items (items.add(itemDto).*/
    public OrderDTO(Order entity) {
        id = entity.getId();
        moment = entity.getMoment();
        status = entity.getStatus();
        client = new ClientDTO(entity.getClient());
        payment = (entity.getPayment() == null) ? null : new PaymentDTO(entity.getPayment());
        for(OrderItem item : entity.getItems()) {
            OrderItemDTO itemDto = new OrderItemDTO(item);
            items.add(itemDto);
        }
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public ClientDTO getClient() {
        return client;
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    /*Método para somar os subtotais contidos em cada item da lista item: Criamos a vari-
    ável sum que recebe o valor 0.0, depois fazemos um for para percorrer e pegar os sub-
    totais. Então para cada objeto OrderItem chamado de item na lista items será feita a
    soma de sum com o subtotal do item pego com .getSubtotal. Ao final retorna a soma.*/
    public Double getTotal() {
        double sum = 0.0;
        for(OrderItemDTO item : items) {
            sum += item.getSubtotal();
        }
        return sum;
    }
}
