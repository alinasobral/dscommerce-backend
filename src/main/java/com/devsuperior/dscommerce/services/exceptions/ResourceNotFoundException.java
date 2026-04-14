package com.devsuperior.dscommerce.services.exceptions;

/*Essa classe foi criada para personalizar a mensagem de
erro que der quando o recurso (produto) que eu estiver buscando não
for encontrado.
Essa classe fica dentro do pacote exceptions (erros) dentro do
pacote services, assim essa pasta fica exclusiva para tratamento
de erros dentro da camada de serviços.
Para que essa classe exception responda como uma exception do java,
usamos o extends para que ela herde uma classe mãe referente erros
da biblioteca do java, mas não usamos a classe Exception, porque ela
exige o uso do bloco try/catch em termos de compilação, então usamos
a classe RuntimeException que não exige que bloco try/catch seja usa-
do, então a classe ResourceNotFoundException se torna a classe filha
da classe mãe RuntimeException.
Dentro da classe criamos um construtor que recebe como parâmetro
uma mensagem por isso usamos a variável msg e o tipo String.
Por fim, dentro do construtor usamos a palavra-chave (keyword) do
java chamada super que chama o constutor da classe mãe RuntimeException
e passando como parâmetro msg que vai ser a mensagem que será exi-
bida no erro.*/
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String msg) {

        super(msg);
    }
}
