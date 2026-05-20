package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/*CRIANDO BUSCA DE LISTA DE CATEGORIAS - PASSO 2
Depois de termos criado o repository de category, agora criamos o service.
Primeiramente instanciamos o repository de category e depois criamos o método
findAll para buscar todas as categorias em formato de lista. Depois de criarmos
o método e sua lógica, vamos criar a camada de controle de Category para criar a
requisição http da busca.*/
@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    /*Aqui criamos o método findAll que irá retornar uma lista de CategoryDTO.
    Dentro do método criamos uma variável result que é uma lista Category que
    receberá a busca do que será feita no repository através do método findAll.
    Por fim, como precisamos da lista em CategoryDTO, então fazemos o stream e
    depois o map para que a lista seja percorrida e cada objeto da lista seja
    transformado em um novo CategoryDTO e depois usamos o .toList para retornar
    ao formato de lista.*/
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> result = repository.findAll();
        return result.stream().map(x -> new CategoryDTO(x)).toList();
    }
}

