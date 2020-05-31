package com.example.springdatajpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 用户实体类
 *
 * @author G
 */
@Getter
@Setter
@Entity
@Table(name="user")
public class UserTest {

  /**
   * PK
   */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    private String realName;

}
