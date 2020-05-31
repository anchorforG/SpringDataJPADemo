package com.example.springdatajpa.repository;

import com.example.springdatajpa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * FROM  https://my.oschina.net/u/3094602/blog/3009226#h3_5
 * @author G
 */
public interface UserRepository extends JpaRepository<User,Integer> , JpaSpecificationExecutor<User> {

    /**
     * 根据name查询,支持命名参数
     */
    @Query("select u from User u where u.name = :mame")
    List<User> findUserByName(@Param("name")String name);

    /**
     * 根据sex查询，缩影参数
     */
    @Query("select u from User u where u.sex = ?1")
    List<User> findUsersBySex(Integer sex);

    /**
     * 模糊查询
     */
    @Query("select u from User u where name like concat('%',?1,'%') ")
    List<User> findByName(String name);

    /**
     * 本地查询
     */
    @Query(value = "select * from user where name like CONCAT('%',?1,'%')", nativeQuery = true)
    List<User> findByNameLocal(String name);

    /**
     * 跟新密码，需要加@Modifying
     */
    @Modifying
    @Query("update user u set u.password = ?1 where u.id = ?2")
    Integer updatePasswordById(String password, String id);



    /**
     * and条件查询
     * 对应sql:select u from User u where u.name = ?1 and u.email = ?2
     * 参数名大写，条件名首字母大写，并且接口名中参数出现的顺序必须和参数列表中的参数顺序一致
     */
    User findByNameAndEmail(String name, String email);

    /**
     * or条件查询
     * 对应sql:select u from User u where u.name = ?1 or u.password = ?2
     */
    List<User> findByNameOrPassword(String name, String password);

    /**
     * between查询
     * 对应sql:select u from User u where u.id between ?1 and ?2
     */
    List<User> findByCreateTimeBetween(Date startTime, Date endTime);

    /**
     * less查询
     * 对应sql:select u from User u where u.id < ?1
     */
    List<User> findByCreateTimeLessThan(Date time);

    /**
     * greater查询
     * 对应sql:select u from User u where u.id > ?1
     */
    List<User> findByCreateTimeGreaterThan(Date time);

    /**
     * is null查询
     * 对应sql:select u from User u where u.name is null
     */
    List<User> findByNameIsNull();

    /**
     * Is Not Null查询
     * 对应sql:select u from User u where u.name is not null
     */
    List<User> findByNameIsNotNull();

    /**
     * like模糊查询
     * 这里的模糊查询并不会自动在name两边加"%"，需要手动对参数加"%"
     * 对应sql:select u from User u where u.name like ?1
     */
    List<User> findByNameLike(String name);

    /**
     * Not Like模糊查询
     * 对应sql:select u from User u where u.name not like ?1
     */
    List<User> findByNameNotLike(String name);

    /**
     * 倒序排序查询
     * 对应sql:select u from User u where u.password = ?1 order by u.id desc
     */
    List<User> findByPasswordOrderByCreateTimeDesc(String password);

    /**
     * <>查询
     * 对应sql:select u from User u where u.name <> ?1
     */
    List<User> findByNameNot(String name);

    /**
     * in 查询，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数
     * 对应sql:select u from User u where u.id in ?1
     */
    List<User> findByIdIn(List<String> ids);

    /**
     * not in 查询，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数
     * 对应sql:select u from User u where u.id not in ?1
     */
    List<User> findByIdNotIn(List<String> ids);

    /**
     * 分页查询，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数
     * 对应sql:select u from User u where u.id not in ?1 limit ?
     */
    Page<User> findByIdNotIn(List<String> ids, Pageable pageable);

}
