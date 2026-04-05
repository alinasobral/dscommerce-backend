package com.devsuperior.dscommerce.repositories;

import com.devsuperior.dscommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/*Para criar um repository usando o Spring, basta colocar extends
e depois o tipo do Spring que é JpaRepository<Product, Long> pametrizando dentro
dos símbolos <> o nome da entidade que será acessado o banco de dados
e o tipo do ID da entidade.*/
public interface ProductRepository extends JpaRepository<Product, Long> {

}
