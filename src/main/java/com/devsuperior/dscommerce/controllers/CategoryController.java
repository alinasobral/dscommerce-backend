package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/*CRIANDO BUSCA DE LISTA DE CATEGORIAS - PASSO 3
Depois de criar o repository e o service de Category, agora criamos a camada
de controlador de Category, aqui vamos configurar a rota da requisição que se-
rá /categories, vamos instanciar o service de Category e criar o método de bus-
ca da requisição GET.*/
@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;
    @Autowired
    private DefaultTransactionDefinition defaultTransactionDefinition;

    /*Na resposta do método findAll teremos uma lista de CategoryDTO, dentro
    dele criamos a variávl list que é uma lista de CategoryDTO que receberá
    o chamado do método findAll que está no service. Por fim, o método irá
    retornar a resposta do método .ok passando como parâmetro a variável list
    que guarda a lista de categorias em formato DTO.*/
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }
}