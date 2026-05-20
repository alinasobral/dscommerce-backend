package com.devsuperior.dscommerce.services.exceptions;

/*CONTROLE DE ACESSO PROGRAMÁTICO SEL OU ADMIN - PASSO 2
Aqui criamos essa classe para que caso o client tente aces-
sar um pedido que não é o seu, então dará esta exception.
Depois de criada a classe da exception, devemos tratá-la
nos handlers.*/
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String msg) {
        super(msg);
    }
}
