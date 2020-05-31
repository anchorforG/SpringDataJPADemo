package com.example.springdatajpa.entity;

import javax.persistence.*;

/**
 * @author G
 */
@Entity
@Table(name = "article")
public class Article {

    @Column(nullable = false, length = 50)
    private String title;

    /**
     * @Lob // 大对象，映射 MySQL 的 Long Text 类型
     * @Basic(fetch = FetchType.LAZY) // 懒加载
     * @Column(nullable = false) // 映射为字段，值不能为空
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private String content;

    /**
     * 多对一配置演示：
     * 可选属性optional=false,表示sysUser不能为空
     * 配置了级联更新（合并）和刷新，删除文章，不影响用户
     *
     * @JoinColumn(name = "user_id") // 设置在article表中的关联字段(外键)名
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
