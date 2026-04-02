package com.devsuperior.dscommerce.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity //@Entity serve para mapear a entidade user numa tabela de banco de dados
@Table(name = "tb_user") //@Table serve para dizer como o nome da tabela deve aparecer no banco de dados
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //para que o id seja auto incrementável no banco
    private Long id;
    private String name;

    @Column(unique = true)/*Configuramos para que quando essa coluna for mapeada para o banco de dados, o conteúdo
    dela será único, pois um e-mail é de apenas um usuário, então ele é único.*/
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String password;

    //LADO DO UM (MUITOS PARA UM/UM PARA MUITOS)
    @OneToMany(mappedBy = "client") //significa um para muitos mapeado pelo atributo client (que foi instanciado em order)
    private List<Order> orders = new ArrayList<>();

    public User() {
    }

    public User(Long id, String name, String email, String phone, LocalDate birthDate, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Order> getOrders() {
        return orders;
    }
    /*OBS: LISTA TEM APENAS GET, POIS UMA LISTA NUNCA É TROCADA,
    APENAS TEM ELEMENTOS REMOVIDOS OU ADICIONADOS*/
}
