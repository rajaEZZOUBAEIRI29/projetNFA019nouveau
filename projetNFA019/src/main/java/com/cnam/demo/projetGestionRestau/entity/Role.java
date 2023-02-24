package com.cnam.demo.projetGestionRestau.entity;

public enum Role {
    ADMIN,
    USER;
    public String getName() {
        return "ROLE_" + this.name();
    }
}
