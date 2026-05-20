package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.dto.OrderItemDTO;
import com.devsuperior.dscommerce.entities.*;
import com.devsuperior.dscommerce.repositories.OrderItemRepository;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

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

PASSO 6: Depois de criar a classe OrderRepository, vamos criar a classe da camada de ser-
viço que é a OrderService. Aqui apenas copiamos o método findById que já tinha na classe
ProductService, já que é apenas uma busca por id do pedido, e trocamos onde tinha Product
para Order. Por fim, criamos a última classe que é a classe da camada de controle que pos-
ssui as requisições http que é a classe OrderController.*/
@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AuthService authService;

    /*FAZENDO CONTROLE DE ACESSO PROGRAMÁTICO SELF OU ADMIN
    PASSO 1: Aqui vamos criar um controle de acesso onde só poderá acessar todos os pe-
    didos quem for ADMIN e o CLIENT só poderá acessar o pedido que for dono. Então para
    isso primeiro criamos uma exception chamada ForbiddenException no pacote de exceptions.
    Depois de ter criado a classe da exception e de ter feito o tratamento dela nos handlers,
    vamos desenvolver lógica desse controle de acesso. Para isso criamos uma classe chamada
    AuthService para desenvolver regras de negócio relacionada a controle de acesso. Depois
    de criada a classe e criado o método para fazer o teste lógico que resultará no controle
    de acesso, nós instanciamos essa classe aqui e usamos esse método dentro do método abaixo.
    então usamos objeto AuthService instanciado junto com o método criado validateSelfOrAdmin
    passando como parâmetro o id do cliente do pedido, então usando o objeto order junto com
    .getClient() para pegar as informações do cliente e depois .getId() para pegar o Id desse
    cliente que é dono do pedido.*/
    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Optional<Order> result = repository.findById(id);
        Order order = result.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
        authService.validateSelfOrAdmin(order.getClient().getId());
        OrderDTO dto = new OrderDTO(order);
        return dto;
    }

    /*SALVANDO UM NOVO PEDIDO
    PASSO 2: O método insert retornará um objeto OrderDTO e levará como parâmetro tam-
    bém um OrderDTO chamado dto. Dentro desse método primeiramente instanciamos um novo
    objeto Order chamado order, pois ao fazer uma compra será criado um novo pedido. A es-
    trutura de um pedido é essa:
        "{
          "id": 1,
          "moment": "2022-07-25T13:00:00Z",
          "status": "PAID",
          "client": {
                        "id": 1,
                        "name": "Maria Brown"
                    },
          "payment": {
                        "id": 1,
                        "moment": "2022-07-25T15:00:00Z"
                      },
          "items": [
                    {
                      "productId": 1,
                      "name": "The Lord of the Rings",
                      "price": 90.5,
                      "quantity": 2,
                      "subTotal": 181.0
                    },
                    {
                      "productId": 3,
                      "name": "Macbook Pro",
                      "price": 1250.0,
                      "quantity": 1,
                      "subTotal": 1250.0
                    }
          ],
          "total": 1431.0
        }"
    O que vai ser passado no postman é a apenas a lista de itens, mas no resultado mos-
    trará tudo. Então depois de instaciado o novo objeto Order, devemos setar cada atri-
    buto do resumo do pedido. Então fazemos um order.SetMoment e passamos como parâmetro
    Instant.now() que pega o exato instante da requisição, depois fazemos um order.setStatus
    e passamos como parâmetro OrderStatus.WAITING.PAYMENT, pois um pedido que acaba de ser
    feito está aguardando o pagamento. Depois temos que setar os dados do cliente autenti-
    cado, para isso temos o método authenticated() que fica na classe UserService, então
    instanciamos a classe UserService aqui, depois instanciamos um objeto User chamado
    de user que recebe os dados do usuário autenticado através de userService.authenticated,
    por fim fazemos um order.setClient e passamos como parâmetro a variável user que guar-
    dou os dados do usuário autenticado. O pagamento não vai entrar na lista, porque como
    está aguardando o pagamento, então ele aparecerá como null. E então temos a lista de
    items, como é uma lista devemos usar um for para percorê-la. A lógica do for
    é para cada OrderItemDTO itemDto dentro da lista items (dto.getItems) que foi a lista
    de items do pedido passadas na requsição post, vamos fazer o seguinte: dentro desse
    for precisamos fazer a associação entre as classes OrderItem e Product e OrderItem e
    Order, então começaremos primeiro com a associação OrderItem e Product. Para essa as-
    sociação instanciamos o ProductRepository com o nome productRepository, depois já den-
    tro do for criamos uma variável Product e chamamos de product, essa variável irá re-
    ceber productRepository.getReferenceById que recebe como referêncua itemDto.getProduct-
    Id, ou seja, eu pego o id do produto e instancio um objeto Product recebendo uma refe-
    rência a partir do id contido no itemDto passado na requisição POST. Depois devemos
    instanciar a classe OrderItem. Então criamos um objeto OrderItem chamado de item que
    recebe um  novo objeto OrderItem que recebe como parâmetro os elementos do construtor
    da sua classe (Order order, Product product, Integer quantity, Double price) represen-
    tados pelo objeto order instanciado, pelo objeto product instanciado, item.getQuantity
    para pegar a quantidade e product.getPrice para pegar o preço do produto diretamente
    da classe Product. Por fim, devemos associar o OrderItem com Order, então pegamos o
    objeto order e usamos o getItems para ele pegar os items e usamos o .add para adici-
    onar o objeto OrderItem item ao objeto order. Dessa forma toda a associação é feita.
    Depois devemos salvar no banco de dados, para isso usamos o OrderRepository que foi
    chamado de repository junto com o método .save passando como parâmetro o objeto order.
    Como foi usada a classe de associação OrderItem, então é necessário também criar um
    repository para essa entidade e instanciar ela aqui para salvar os items. Depois de
    ter instanciado o OrderItemRepository, usamos o objeto dele instanciado junto com o
    método .saveAll passando como parâmetro order.getItems() que significa que estará
    salvando todos os items associados ao order. Por fim, retornamos o OrderDTO a partir
    do order.
    */

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        Order order = new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        User user = userService.authenticated();
        order.setClient(user);
        for(OrderItemDTO itemDto : dto.getItems()) {
            Product product = productRepository.getReferenceById(itemDto.getProductId());
            OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice());
            order.getItems().add(item);
        }
        repository.save(order);
        orderItemRepository.saveAll(order.getItems());
        return new OrderDTO(order);
    }
}
