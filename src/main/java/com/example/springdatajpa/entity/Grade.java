package com.example.springdatajpa.entity;

import javax.persistence.*;

/**
 * @author G
 */
@Entity
@Table(name = "t_grade")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String gradeName;
    /**
     * 一对一
     * mappedBy：维护外键关联关系
     * mappedBy = "grade"，此关系由学生实体中的grade维护
     */
    @OneToOne(mappedBy = "grade")
    private Student student;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", gradeName='" + gradeName + '\'' +
                '}';
    }
}
