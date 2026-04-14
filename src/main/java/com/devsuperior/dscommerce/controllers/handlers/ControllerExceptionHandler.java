package com.devsuperior.dscommerce.controllers.handlers;

import com.devsuperior.dscommerce.dto.CustomError;
import com.devsuperior.dscommerce.dto.ValidationError;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

/*A classe ResourceNotFoundException serviu para personalizar a
mensagem de erro, agora nesta que estamos serve para tratar o erro.
Como essa classe refere a uma tratamento de controllers (controladores),
então criamos um pacote dentro do pacote controller e chamamos de handlers.
que significa algo como tratador/manipulador/responsável por lidar com
alguma coisa.
Esta classe se chama ControllerExceptionHandler porque é responsável
por tratar exceções lançadas nos controladores (exceções que possam
ocorrer na camada de controladores).
Usamos a anotation @ControllerAdvice em cima da clase, pois ela diz
ao Spring que essa classe será uma classe especial para tratamento
global, ou seja, significa que ela não trata o erro de apenas um con-
troller, mas sim de qualquer controller da aplicação. Se essa anotation
teria que ser usado o try/catch para cada controller.
Dentro da classe, criamos um método para fazer esse tramento. Acima
dele usamos a anotation @ExceptionHandler que ser para informar ao
Spring que o método abaixo é um tratador de exceção. Essa anotation
recebe um parâmetro que é para informar o tipo de exceção que o mé-
todo irá tratar. Então colocamos como parâmetro a classe ResourceNot-
FoundException usamos o .class que serve para fazer referência a clas-
se em si e não a um objeto dela. Enão de forma geral, a anotation quer
dizer que "Se em algum ponto da aplicação for lançada uma exceção Re-
sourceNotFoundException, chame este método para tratá-la".
No método usamos a classe do Sprin ResponseEntity (a mesma que usamos
nos métodos os controladores para personalizar o status da resposta
HTTP, ela serve para definir o ststus da resposta HTTP, o corpo (body)
da respota e o cabeçalho dela. Dentro dos símbos <> colocamos a classe
CustomError que é a classe que tem os atributos do copor da respota
HTTP. Ao colocar esse classe dentro do ResponseEntity, significa que
o corpo da resposta será um objeto do tipo CustomError, ou seja, o
cliente da API vai receber um JSON baseado nesse objeto.
Demos o nome desse método de resourceNotFound() e ele recebe dois pa-
râmetros: primeiro o tipo da exceção capturada que no caso é a exceção
ResourceExceptionNotFound junto com a variável e (com o "e" podemos pe-
gar mensagem de erro com o e.getMessage() e tipo da exceção com e.get-
Class()), ou seja, se em algum lugar da aplicação acontecer "throw new
ResourceNotFoundException("Recurso não encontrado")" o objeto dessa ex-
ceção vai cair dentro da variável e; segundo parâmtro é a requisição
HTTP que foi feita pelo cliente que é representada pela classe HttpServletRequest
e a variável request, com essa classe podemos obter várias informações
sobre a requisição como URI acessada (rota da requisição ex: /products/26),
parâmetros, cabeçalhos e método HTTP.
Depois criamos uma variável status do tipo da classe enum HttpStatus,
e guardamos nela o status enum HttpStatus.NOT_FOUND que representa o
erro 404 que signigia recurso solicitado não foi encontrado.
Depois criamo o objeto de erro, aqui instanciamos um objeto da classe
CustomError e damos o nome de err, esse objeto guardará os dados do
erro que serão enviados para o cliente. Esses dados serão os atributos
que estão no corpo da requisição, logo o novo objeto leva como parâme-
tro esses atributos com os métodos para pegar cada um deles: Instant.now()
que serve pegar os dados de data e hora do erro, status.value() para
pegar o número do erro que foi guardado na variável status, e.getMessa-
ge() para pegar a mensagem da exceção guardada na variável "e" e por
fim request.getRequestURI para pegar a rota da requisição.
Por fim, o return usamos a classe ResponseEntity para montar e devol-
ver a resposta HTTP, seguido do método .status(status) que define o
status da resposta (o que aparece no postaman 404 Not Found) e usamos
o método .body() que define o corpo da resposta que leva como o parâme-
tro a variável err que guardou os dados do erro, ou seja, o conteúdo
enviado ao cliente será o objeto err. Esse objeto normalmente é conver-
tido em JSON automaticamente pelo Spring.*/
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
    /*O método abaixo foi criado para tratar o erro que dá quando o
    usuário tenta apagar um recurso(id) que está vinculado a um pe-
    dido(order) e esse erro dá falha de integridade referencial. E-
    le é muito parecido com o método resourceNotFound, apenas o que
    muda é o nome da classe que é DatabseException serviu para per-
    sonalizar a mensagem de erro para essa exception. Mudamos tam-
    bém o nome do método que é database, mudamos também o tipo de
    exceção capturada que o método recebe como parâmtro e por fim
    foi mudado o status que irá retornar no corpo da requisição
    que colocamos como .BAD_REQUEST que é o número 400. Os nomes
    dos status de erros mais comuns estão no pdf do capítulo.*/
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> database(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_CONTENT;
        ValidationError err = new ValidationError(Instant.now(), status.value(), "Dados inválidos", request.getRequestURI());
        for(FieldError f : e.getBindingResult().getFieldErrors()) {
            err.addError(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }
}
