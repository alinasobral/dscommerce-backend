package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

    /*Método para buscar um produto pelo Id: Esse método serve para
    buscar um produto pelo seu id, ele recupera/mostra o produto
    pelo seu id e retorna em formato DTO. O método tem o nome de
    findById(procure pelo id), recebe um argumento que é o id e
    o tipo dele e retorna um objeto ProductDTO.
    Dentro do método pedimos pra aplicação buscar o produto pelo
    id no repository (camada de dados) instanciando um objeto
    repository e usando o método .findById e passando como parâmetro
    o id, essa busca vai retornar um Product só que usamos ele den-
    tro do Optional, que é como se fosse uma "caixinha": se encon-
    trou o produto, a caixinha vem com um Product dentro, se não en-
    controu, a caixinha vem vazia.
    Depois instanciamos um objeto Product que foi chamado de product
    mesmo para pegar o resultado dessa busca, se houver um produto
    dentro do Optional, pega esse produto normalmente, se o Optio-
    nal estiver vazio, lança uma exceção, por isso é usado um méto-
    do do Optional que é o .orElseThrow() para personalizar a mensa-
    gem de erro dessa exceção.
    Depois de ter criado a classe ResourceNotFoundException que tra-
    ta dessa exceção dentro do pacote exceptions, usamos uma expres-
    são lambda dentro do método .orElseThrow, onde essa expressão
    funciona como uma instrução ao java que diz "Se precisar lan-
    çar erro, crie esse novo objeto de exceção", esse erro só se-
    rá criado se realmente não existir produto no Optional e dentro
    do novo objeto de exceção colocamos a mensagem de erro persona-
    lizada.
    Depois de garantir que o produto existe, o código cria um Pro-
    ductDTO a partir do objeto Product encontrado. Product é a en-
    tidade ligada ao banco de dados ProductDTO é um objeto usado
    para transferir os dados de forma mais controlada. Normalmen-
    te o DTO é usado para: enviar apenas os dados necessários, não
    expor a entidade diretamente eorganizar melhor a comunicação
    da aplicação com o mundo externo (ex.: API).
    Por fim, retorna o DTO já pronto. Ou seja: o método não devol-
    ve a entidade Product, mas sim um ProductDTO com os dados do
    produto encontrado*/
    @Transactional(readOnly = true) //depois vai ser explicado
    public ProductDTO findById(Long id) {
        Optional<Product> result = repository.findById(id);
        Product product = result.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
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
    @Transactional(readOnly = true) //dá um lock no banco para fazer apenas leitura
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> result = repository.findAll(pageable);
        return result.map(x -> new ProductDTO(x));
    }

    /*Método para inserir um novo produto: Essa geralmente é uma
    requisição que vem do frontend, como quando o usuário clica
    em adicionar novo produto, preenche o formulário com os dados
    do produto e clica em salvar. Esses dados vão em formato JSON
    para a camada de controladores REST (essa classe que estamos)
    e vão para a camada de serviços no formato DTO que chamamos
    de body (todos os dados juntos é o corpo). No método de inserir,
    a gente cria o novo objeto, copia dos dados da entidade e salva
    no repositório (camada de banco de dados). Método copyDtoToEntity
    foi criado porque a função de copiar os dados se repete no
    método de atualizar os dados de um produto*/
    /*Para testar essa requisição vinda do frontend, usamos o
    postman. Lá criamos uma nova requisição, colocamos o tipo
    como POST, e configuramos o body, a requisição para raw e co-
    locamos o formato em JSON, que é o formato que vem do frontend*/
    @Transactional
    public ProductDTO insert(ProductDTO dto) {
            Product entity = new Product(); //instancia novo objeto
            copyDtoToEntity(dto, entity); //copia os dados DTO da entidade
            entity = repository.save(entity); //salva no repositório (banco de dados)
            return new ProductDTO(entity);
    }

    /*Método para atualizar dados de um produto: Esse método é
    também uma requisição vinda do frontend, quando o usuário
    clica na canetinha para mudar os dados de um produto. Assim,
    como no método criar um novo produto, esses dados vão em
    formato JSON para a camada de controladores REST (essa classe
    que estamos) e vão para a camada de serviços no formato DTO,
    que chamamos de body (todos os dados juntos é o corpo).
    O método de atulizar é parecido com o método de inserir, mas
    ao invés de instaciar um novo objeto, nós pedimos para o sis-
    tema pegar a refência dos dados do produto pelo Id, por isso
    usa-se o método .getReferenceById e passamos o argumento id.
    Como usamos o Id do produto, então no update passamos como
    argumento não só o corpo que é um ProductDTO, mas também o Id
    e o tipo dele. Depois pegar os dados pelo Id, os dados são
    para a entidade e depois é salvo no repositório(camada de dados)*/
    /*Para testar essa requisição vinda do frontend, usamos o
    postman. Lá criamos uma nova requisição, colocamos o tipo
    como PUT, e configuramos o body, a requisição para raw e co-
    locamos o formato em JSON, que é o formato que vem do frontend.
    No body nós colocamos o dado com as informações atualizadas
    do produto.*/
    /*Na aula sobre tratamento de exceções foi adicionado um try/ca-
    tch para pegar e o erro que dá quando o usuário tenta atualizar
    um produto que o recurso(id) não é encontrado. Esse erro vai ser
    capturado pelo try/catch porque aqui não temos um objeto do ti-
    po Optional. Esse erro vai ser tratado pelo método resourceNot-
    Found da classe ControllerExceptionHandler, pois é um erro de
    recurso não encontrado.
    Então colocamos o try logo abaixo do método e colocamos o código
    do método que já estava pronto dentro do try, depois de fechar
    as chaves do try, abaixo da chave de fechamos colocamos o catch
    que recebe como parâmetro o nome do erro EntityNotFoundException
    que o Spring acusou e junto dele colocamos a variável "e" que
    serve para pegar a mensagem de erro.
    Depois abrimos as chaves e dentro das chaves do catch colocamos
    o throw new que serve para lançar uma nova exceção e em seguida
    colocamos o tipo da exceção que é o ResourceNotFoundException e
    dentro dos parênteses colocamos a frase do erro que será exibi-
    da para o usuário.*/
    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getReferenceById(id); //instacia com a referência (ou seja, puxa os dados pelo id)
            copyDtoToEntity(dto,entity); //copia os dados DTO para a entidade
            entity = repository.save(entity); //salva no repositório (camada do banco de dados)
            return new ProductDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    //Método de copiar o dado DTO para a Entidade:
    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
    }

    /*Método para deletar um prduto: Esse método é também uma
    requisição vinda do frontend, quando o usuário clica no
    ícone de lixeiro para excluir um produto listado.
    Assim, é um método de deletar, ele não irá retornar nenhum
    valor, então usamos o void, pois é um método que não tem
    um retorno.
    No método delete passamos como argumento o id e seu tipo, pois
    é através dele que o produto é identificado e deletado na ca-
    mada de acesso a dados (banco de dados).
    Por fim, usamos o método .deleteById que tem a função de dele-
    tar pelo Id e passamos como argumento o id do produto.*/
    /*Na aula sobre tratamento de exceções foram adicionados os tra-
    tamentos de dois erros: quando o usuário pedir para deletar um
    recurso(id) e ele não foi encontrado e quando o usuário tenta
    apagar um recurso(id) que está vinculado a um pedido.
    -Erro de recurso(id) não encontrado: foi adicionado um if e den-
    tro dele colocamos um !repository.existsById(id), sendo que o
    símbolo de exclamação significa "não/contrário de verdadeiro" e
    o método repository junto com o método .exitsBuId passando o pa-
    râmetro id serve para verificar se o objeto existe pelo seu id,
    então !repository.existisById(id) quer dizer "se o produto não
    for encontrando pelo id informado", depois colocamos o throw new
    com o tipo da exceção que é ResourceNotFoundException recebendo
    como parâmetro a frase de erro que será exibida para o usuário.
    -Erro quando o usuário tenta apagar um recurso(id) que está vin-
    culado a um pedido: Para tratar esse erro usamos o try/catch.
    Dentro do try colocamos o repository.deleById(id) como estava an-
    tes de inserir os códigos de tratameto. No catch, passamos como
    parâmtro o tipo de erro que é o DataIntegrityViolationException
    seguido da variável "e" para pegar a mensagem de erro. Depois usa-
    mos o throw new que serve para lançar uma nova exceção e em se-
    guida colocamos o tipo da exceção que é o DatabaseException (que
    foi criada a classe dentro do pacote exception) e dentro dos pa-
    rênteses colocamos a frase do erro que será exibida para o usuá-
    rio.
    OBS: Para encontrar o nome da exceção basta procurar no log a ex-
    ção do Spring.*/
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

}

