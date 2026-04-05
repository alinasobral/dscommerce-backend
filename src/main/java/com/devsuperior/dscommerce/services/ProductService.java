package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/*Cria-se o pacote services para ser a cama da serviços da aplicação.
Então o certo é fazer a comunicação com a camada de acesso a dados
(banco de dados) nesta camada e não na camada Controllers (camada
de controladores) como foi feito no primeiro teste.*/
@Service
public class ProductService {

    //TESTE DE API REST COM O REPOSITORY PRODUCTREPOSITORY
    @Autowired /*Aqui estamos injetando um componente de ProductRpository*/
    private ProductRepository repository; /*Aqui cria-se uma dependência
    pois a camada de acesso a dados depende da camada de serviços*/

    @Transactional(readOnly = true) //depois vai ser explicado
    public ProductDTO findById(Long id) {
        Optional<Product> result = repository.findById(id);
        Product product = result.get();
        ProductDTO dto = new ProductDTO(product);
        return dto;

        /*FORMA RESUMIDA DO CÓDIGO ACIMA:
        Product product = repository.findById(id).get();
        return new ProductDTO(product);*/
    }
    /*Foi também criado o pacote DTO para fazer a converção dos dados
    que vem do banco de dados para o formato DTO e vice-versa. Dentro
    da classe ProductDTO estão os atritbutos referentes a entidade Product.
    O .findById serve para buscar um Id no banco de dados, então
    ele deve ser colocado no método do DTO e passa-se como argumento
    o atributo Id e o tipo (Long).
    Optional é porque o findById por padrão retorna um objeto Optional
    do Java, então no caso de não existir o Id ele vai retornar
    o Optional sem o produto lá dentro. Entre os símbolos <> coloca-se
    o tipo(a entidade) que se quer buscar o Id, result é a variável
    que vai ser armazenado o resultado da busca e o repository.finById(id)
    é o comando de busca.
    Depois para pegar o resultado guardado em result, criamos um
    objeto Product e chamamos de product mesmo e usando o .get() para
    pegar o resultado guardado em result.
    Após isso, para fazer a conversão para DTO do resultado que foi
    armazenado no objeto product, instaciamos novo um objeto do tipo
    ProductDTO com parâmetro product(que por sua vez vai pegar os dados
    referentes ao construtor em ProductDTO).
    Por fim, pedimos para retornar o resultado do novo objeto dto
    que foi criado*/
}

