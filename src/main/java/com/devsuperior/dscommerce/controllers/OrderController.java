package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

PASSO 7: Depois de criar a classe OrderRepository, vamos criar a classe da camada de
controle que é a OrderController. Aqui apenas copiamos tudo da classe ProductController,
trocando apenas a rota da annotation @RequestMapping para /orders, instanciamos a classe
OrderService, como é uma busca por id do pedido, usamos o método findById que já existia
na classe ProductController e trocamos onde tinha Product para Order. Além disso, co-
locamos a annotation @PreAuthorize para a busca por id dos pedidos só ser permitida pa-
ra com usuário com a role ADMIN.*/
@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    /*Aqui fizemos o controle de acesso programático onde o usuário só poderá acessar to-
    dos os pedidos se for admin ou se for o dono do pedido, então aqui deve ficar ROLE_ADMIN
    e ROLE_CLIENT.*/
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO>  findById(@PathVariable Long id) {
       OrderDTO dto = service.findById(id);
       return ResponseEntity.ok(dto);
    }

    /*SALVANDO UM NOVO PEDIDO
    PASSO 1: No caso de uso temos a exigência do salvamento do pedido, ou seja, salvar
    o pedido quando a compra acabou de ser realizada pelo cliente. Então aqui no con-
    troller precisamos criar a requisição POST, ela será autorizada apenas para o usu-
    ário que tiver o role de cliente. Aqui copiamos a requisição POST do ProductContro-
    ller e mudamos apenas de ProductDTO para OrderDTO, o restante foi mantido. Além dis-
    so, adicionamos na classe OrderDTO acima da lista OrderItemDTO a validação @NotEmpty,
    pois o pedido deve ter pelo menos um item. Depois disso, vamos para a classe Order-
    Service para implementar o método insert.*/
    @PreAuthorize("hasAnyRole('ROLE_CLIENT')")
    @PostMapping
    public ResponseEntity<OrderDTO> insert(@Valid @RequestBody OrderDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

}