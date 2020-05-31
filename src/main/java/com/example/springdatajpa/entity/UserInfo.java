package com.example.springdatajpa.entity;

import javax.persistence.*;

/**
 * @author G
 */
@Entity
@Table(name = "t_user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userName;
    /**
     * 多个用户拥有同一个角色
     */
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleInfo roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public RoleInfo getRoles() {
        return roles;
    }

    public void setRoles(RoleInfo roles) {
        this.roles = roles;
    }
}
