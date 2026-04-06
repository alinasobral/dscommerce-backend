package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/*A anotation @RestController serve para configurar o spring para que
quando a aplicação rodar o que for implementado nessa classe vai estar
respondendo pela web.*/
@RestController
@RequestMapping(value = "/products")/*(End Point) Essa anotation serve para configurar a rota do recurso products*/
public class ProductController {
    /*O controlador implementa o recurso(ou os recursos) na minha
    API REST*/

    /*PRIMEIRO TESTE DE API REST - FORMA ERRADA!!!
     Esta forma é errada porque o controller conversa com a camada
     de serviços e não com a camada de acesso a dados (repository)
    @GetMapping Essa anotation serve para que a string que vai retornar do método teste() responda pela rota /products e responda pelo método HTTP GET
    public String teste() {
        return "Olá, mundo!";
    }*/

    @Autowired /*Aqui estamos injetando um componente de ProductService*/
    private ProductService service;/*Aqui cria-se uma dependencia
    pois a camada de controladores depende da camada de serviços*/

    @GetMapping(value = "/{id}")//Esse parâmetro casa com o id abaixo
    public ProductDTO findById(@PathVariable Long id) {//e essa anotation serve para configurar isso, para que id de cima case o id de baixo
       ProductDTO dto = service.findById(id);
       return dto;

       /*FORMA RESUMIDA DO CÓDIGO ACIMA:
       return service.findById(id);*/
    }
    /*Esse método serve para listar todos os produtos e não apenas um
    e de forma paginada*/
    @GetMapping
    public Page<ProductDTO> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

}
