package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.services.ProductService;
import jakarta.validation.Valid;
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

    /*Método de inserir um novo produto: Depois de configurar a
    classe ProductService (camada de serviços), configuramos a
    camada de controlador para receber a requisição POST vinda do
    frontend*/
    /*A anotation @Valid serve para sempre que um dto for recebido
    no corpo da requisição do post ele passe pelas validações que
    foram inseridas acima dos atributos na classe ProductDTO e a
    anotation @RequestBody serve para informar que o argumento
    ProductDTO dto vai ser o body (corpo da requisição JSON do
    frontend, ou seja, vai ser o body é o bloco de informações do
    produto.*/
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
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
        /*FORMA RESUMIDA:
         return service.insert(dto)*/
    }

    /*Método de atualizar um produto: Depois de configurar a
    classe ProductService (camada de serviços), configuramos a
    camada de controlador para receber a requisição PUT vinda do
    frontend*/
    /*Aqui fazemos quase que uma espécie de junção entre o método
    de buscar por Id e o método de inserir, pois como falei na
    observação do método de atualizar na camada de serviços, as
    informações do produto são puxadas pelo id, então usamos o
    método update e passamos como argumento o id e seu tipo e mais
    o tipo dos dados do corpo da requisição e a variável que lhe
    representa que no caso é dto. Além disso, usamos três anotations,
    uma que serve para configurar que o id de cima (id da rota) case
    com o id de baixo (id do argumento que vai ser passado)que é a
    anotation @PathVariable (usada no método de buscar por id), a
    outra é a anotation @Valid serve para sempre que um dto for re-
    cebido no corpo da requisição do post ele passe pelas valida-
    ções que foram inseridas acima dos atributos na classe ProductDTO
    e a anotation @RequestBody (usada no métedo de inserir) que ser-
    ve para configurar que o objeto DTO passado como argumento se-
    ja vinculado ao body da requisição HTTP vinda do frontend.
    Depois dizemos que para o sistema que o objeto dto deve receber
    o instanciamento de service com o método update, passando como
    argumento o id e o objeto dto.
    Por fim, o return retorna o objeto dto atualizado*/
    @PutMapping(value = "/{id}")//Esse parâmetro é referente a rota, pois ele espera o id para mostrar o produto
    public ResponseEntity<ProductDTO>  update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    /*Método para deletar um produto: Depois de configurar a
    classe ProductService (camada de serviços), configuramos a
    camada de controlador para receber a requisição DELETE vinda do
    frontend.
    Como é um método que não irá retornar nada, pois é de apagar
    algo, então colocamos o void com V maiúsculo dentro do Respon-
    seEntity para informar que irá retornar vazio. No método delete
    passamos como argumento o id e o tipo dele, pois é como o pro-
    duto será identificado e colocamos a anotation @PathVariable
    para configurar que o id de cima (id da rota) case com o id
    de baixo (id do argumento que vai ser passado).
    Depois instanciamos e service com o método .delete e passamos
    o id como parâmetro.
    Por fim, usamos os método .noContent() para que o ResponseEntity
    retorne vazio e o código que apareça no postman seja código de
    delete que é o número 204 e o método .build() para construir
    isso.*/
    @DeleteMapping(value = "/{id}")//Esse parâmetro é referente a rota, pois ele espera o id para identificar o produto
    public ResponseEntity<Void>  delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}