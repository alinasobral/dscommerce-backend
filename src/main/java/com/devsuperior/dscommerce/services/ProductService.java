package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    //Método para buscar um produto pelo Id
    @Transactional(readOnly = true) //depois vai ser explicado
    public ProductDTO findById(Long id) {
        Optional<Product> result = repository.findById(id);
        Product product = result.get();
        ProductDTO dto = new ProductDTO(product);
        return dto;
        /*Se os atributos fosse copiados através do set, abaixo
        do new ProductDTO ficaria dt.setID(product.getId();
        dto.setName(product.getName(); e assim por diante.*/
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

    //Método para buscar todos os produtos e converter em formato DTO e gerar uma lista desses produtos em DTO
    /*public List<ProductDTO> findAll() {
        List<Product> result = repository.findAll(); // O método findAll() busca todos os produtos no banco de dados (entidades Product) e vai gerar uma lista

        return result.stream().map(x -> new ProductDTO(x)).toList();*/
        /*O método .stream() transforma a lista em um fluxo (pipeline)
        para processar elemento por elemento. É como se a lista fosse
        uma caixa com os produtos e ao usar .stream() se transforma em
        uma esteira de fábrica. O método .map serve para transformar
        cada item da lista. Então quer dizer que para cada Product (x),
        cria um novo ProductDTO baseado nele. Por fim, o método .toList()
        coleta tudo e transforma de volta em uma List<ProductDTO>
        FLUXO MENTAL:
                        [Product, Product, Product]
                                ↓ stream()
                    [Product] → [Product] → [Product]
                                ↓ map()
                      [DTO]     → [DTO]     → [DTO]
                                ↓ toList()
                            [DTO, DTO, DTO]*/
        /*Forma resumida de escrever o lambda:
        .map(x -> new ProductDTO(x)) = .map(ProductDTO::new)
        Quer dizer que para cada Product, crie um ProductDTO usando o construtor
    }*/
    /*Só que para precisamos que os produtos recuperados apareçam
    de forma paginada, pois imagina se o sistema tem mil produtos?
    Ficaria muito bagunçado listar tudo de uma vez. Então o método
    de busca de todos os produtos, converter para o formato DTO e
    mostrá-los de forma paginada vai ficar assim:
    */

    //Método para buscar todos os produtos e mostrar de forma paginada
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> result = repository.findAll(pageable);
        return result.map(x -> new ProductDTO(x));
    }

}

