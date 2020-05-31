package com.example.springdatajpa.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @author G
 */
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userName;

    /**
     * 一对多配置演示
     * 级联保存、更新、删除、刷新;延迟加载。当删除用户，会级联删除该用户的所有文章
     * 拥有mappedBy注解的实体类为关系被维护端
     * mappedBy="user"中的user是Article中的user属性
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Article> articleList;

    /**
     * 多对多配置演示
     */
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role", // 定义中间表的名称
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}, // 定义中间表中关联User表的外键名
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")} // 定义中间表中关联role表的外键名
    )
    private Set<Role> roles;

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

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
