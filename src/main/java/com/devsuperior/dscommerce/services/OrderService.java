package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Optional<Order> result = repository.findById(id);
        Order order = result.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
        OrderDTO dto = new OrderDTO(order);
        return dto;
    }
}
