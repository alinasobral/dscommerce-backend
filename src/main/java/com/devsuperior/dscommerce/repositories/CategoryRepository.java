package com.devsuperior.dscommerce.repositories;

import com.devsuperior.dscommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/*CRIANDO BUSCA DE LISTA DE CATEGORIAS - PASSO 1
Primeiramente criamos o repository de category. Depois vamos criar uma cada de serviço
para category.*/
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
