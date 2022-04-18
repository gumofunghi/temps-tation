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
    private  RoleEnum name;

    public Role(RoleEnum role_name){
        this.name = role_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleEnum getRole_name() {
        return name;
    }

    public void setRole_name(RoleEnum role_name) {
        this.name = role_name;
    }
}
