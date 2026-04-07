package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    @Autowired
    private DefaultTransactionDefinition defaultTransactionDefinition;

    /*Método para buscar um produto pelo Id*/
    /*Foi acrescentado o método ResponseEntity para personalizar
    a resposta no postman, que é onde aparece o código e um OK
    ao lado, sendo código de mostrar os produtos é o 200.
    Dentro dos símbolos <> colocamos o tipo de retorno que é
    ProductDTO. No return ao invés de ficar só dto como estava
    antes, colocamos ResponseEntity.ok que é o código de requisição
    bem sucedida número 200 de mostrar os produtos e dentro
    passamos o dto como parâmetro*/
    @GetMapping(value = "/{id}")//Esse parâmetro casa com o id abaixo
    public ResponseEntity<ProductDTO>  findById(@PathVariable Long id) {//e essa anotation serve para configurar isso, para que id de cima case o id de baixo
       ProductDTO dto = service.findById(id);
       return ResponseEntity.ok(dto);

       /*FORMA RESUMIDA DO CÓDIGO ACIMA (SEM O RESPONSEENTITY):
       return service.findById(id);*/
    }
    /*Esse método serve para listar todos os produtos e não apenas um
    e de forma paginada*/
     /*Foi acrescentado o método ResponseEntity para personalizar
    a resposta no postman, que é onde aparece o código e um OK
    ao lado, sendo código de mostrar os produtos é o 200.
    Dentro dos símbolos <> colocamos o tipo de retorno que é
    Page<ProductDTO>. No return ao invés de ficar só dto como estava
    antes, colocamos ResponseEntity.ok que é o código de requisição
    bem sucedida número 200 de mostrar os produtos e dentro
    passamos o dto como parâmetro*/
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {
        Page<ProductDTO> dto = service.findAll(pageable);
        return ResponseEntity.ok(dto);
    }

    /*Método de inserir um novo produto. Depois de configurar a
    classe DTO, configuramos a camada de controlador para receber
    a requisição POST vinda do frontend*/
    /*A anotation @RequestBody serve para informar que o argumento
    ProductDTO dto vai ser o body da requisição JSON do frontend*/
     /*Foi acrescentado o método ResponseEntity para personalizar
    a resposta no postman, que é onde aparece o código e um OK
    ao lado. Neste método, como é de inserir, ou seja, criar um
    novo produto, então o código certo que deveria aparecer é o
    número 201, mas por padrão aparece o 200 que na verdade é o
    código de mostrar os produtos. Então usamos o método Respon-
    seEntity para personalizar e colocar o código 201 que é o có-
    digo created. Dentro dos símbolos <> colocamos o tipo de
    retorno que é ProductDTO.
    No return ao invés de ficar só dto como estava
    antes, colocamos ResponseEntity.created() que é o código de
    requisição bem sucedida número 201 de criar um novo objeto,
    porém o .created() recebe como argumento/parâmetro um objeto
    do tipo URI, então deve ser instaciado um objeto desse tipo
    antes do return.
    Para instanciar esse objeto URI colocamos esses 5 métodos que
    estão descritos abaixo juntos, sendo que no método .path pas-
    samos como argumento a rota id e no método .buildAndExpand
    passamos o argumento para pegar o id novo do objeto dto que
    será criado, para isso usamos o dto.getId() e no final usamos
    .toURI.
    Por fim, voltando para o return, colocamos o objeto URI que
    foi instaciado acima do return dentro do método created e
    usamos o método .body para construir o copor da respota lá
    no postman passando o argumento dto, pois é o objeto que será
    criado*/
    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
        /*FORMA RESUMIDA:
         return service.insert(dto)*/
    }

}
