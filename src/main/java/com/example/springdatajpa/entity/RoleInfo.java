package com.example.springdatajpa.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author G
 */
@Entity
@Table(name = "t_role_info")
public class RoleInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String roleName;
    /**
     * 多个用户拥有同一个角色
     */
    @OneToMany(mappedBy = "roles", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<UserInfo> users = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(Set<UserInfo> users) {
        this.users = users;
    }
}
