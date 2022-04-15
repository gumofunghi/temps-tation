package com.gumo.temps.model;

import javax.persistence.*;

@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private  RoleEnum role_name;

    public Role(RoleEnum role_name){
        this.role_name = role_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleEnum getRole_name() {
        return role_name;
    }

    public void setRole_name(RoleEnum role_name) {
        this.role_name = role_name;
    }
}
