package com.devsuperior.dscommerce.repositories;

import com.devsuperior.dscommerce.entities.OrderItem;
import com.devsuperior.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*Classe criada por causa da associação criada no OrderService. O tipo de id do OrderItem
é OrderItemPK.*/
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
