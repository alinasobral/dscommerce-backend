package com.devsuperior.dscommerce.entities;

public enum OrderStatus {
    WAITING_PAYMENT, PAID, SHIPPED, DELIVERED, CANCELED;
}

/*Os métodos equals e hashCode não precisam ser colocados na classe enum*/
