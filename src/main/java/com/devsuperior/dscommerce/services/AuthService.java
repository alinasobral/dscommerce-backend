package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.services.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*CONTROLE DE ACESSO PROGRAMÁTICO SELF OU ADMIN - PASSO 4
Essa classe foi criada para desenvolver regras de negócio relacionada a
controle de acesso deste sistema. Aqui vamos desenvolver o método onde
será feito o teste que vai dizer se o usuário é admin ou o dono do pe-
dido. Então chamamos esse método de validateSelfOrAdmin e passamos como
parâmetro o tipo Long junto com userId, dentro método vamos fazer um if
que irá testa que se o usuário que estiver logado não for ADMIN ou o id
do usuário não for o mesmo que consta no pedido, então se ele tentar aces-
sar o pedido vai ser lançada a ForbiddenException. Então instaciamos a clas-
se UserService para que dentro do método possamos usar o método .authenticated()
para pegar o usuário autenticado. Então criamos uma variável chamada me do tipo
User que vai receber userservice.authenticated(). Depois que essa informação for
guardada na variável me, então fazemos o teste lógico com if, se o usuário guar-
dado em me não é ADMIN e se o usuário guardado em me não é o mesmo do userId pas-
sado no parâmetro do método, para isso vamos usar o método hasRole que foi criado
dentro da classe User. Então se o me não for ADMIN (!me.hasRole("ROLE_ADMIN")) e
se o me não tiver o mesmo id passado no parâmetro (!me.getId().equals(userId)) então
será lançado a ForbiddenException levando como parâmetro uma mensagem personalizada
de erro (throw new ForbiddenException("Acesso negado"). Depois voltamos no OrderService
para usar esse método dentro do método findbyId para fazer o controle de acesso.*/
@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public void validateSelfOrAdmin(long userId) {
        User me = userService.authenticated();
        if(!me.hasRole("ROLE_ADMIN") && !me.getId().equals(userId)) {
            throw new ForbiddenException("Acesso negado");
        }
    }
}
