有点难……

#### JPA简介

JPA 是一种规范，内部由一系列的接口和抽象类组成。

#### 文档

[SpringData JPA 官方文档](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#preface)

#### JPA

###### 相关配置文件

```java
server:
  port: 8080
  servlet:
    context-path: /
spring:db_jpa
  datasource:
    url: jdbc:mysql://localhost:3306/db_jpa?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=false
    username: root
    password: 123456
  jpa:
    database: MySQL
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
    show-sql: true
    hibernate:
      ddl-auto: update
```

ddl-auto

- `create`：每次运行程序时，都会重新创建表，故而数据会丢失

- `create-drop`：每次运行程序时会先创建表结构，然后待程序结束时清空表

- `upadte`：每次运行程序，没有表时会创建表，如果对象发生改变会更新表结构，原有数据不会清空，只会更新（推荐使用）

- `validate`：运行程序会校验数据与数据库的字段类型是否相同，字段不同会报错

- `none`: 禁用DDL处理

#### SpringData JPA

###### Spring Data Jpa UML类图

![Spring Data Jpa UML类图](\SpringData JPA UML类图.png)

#### 实体类注解

1. **<span style='color:red;'>@Entity</span>**：JPA实体类

2. **<span style='color:red;'>@Table(name="表名")</span>**：实体类数据库对应表

   - name: 表名
   - catalog: 对应关系数据库中的catalog
   - schema：对应关系数据库中的schema
   - UniqueConstraints:定义一个UniqueConstraint数组，指定需要建唯一约束的列

3. @**SecondaryTable**：一个entity class可以映射到多表，SecondaryTable用来定义单个从表的名字，主键名字等属性。

   - name: 表名

   - catalog: 对应关系数据库中的catalog

   - schema：对应关系数据库中的schema

   - pkJoin: 定义一个PrimaryKeyJoinColumn数组，指定从表的主键列

   - UniqueConstraints: 定义一个UniqueConstraint数组，指定需要建唯一约束的列

     ```java
     @Entity
     @Table(name="CUSTOMER")
     @SecondaryTable(name="CUST_DETAIL",pkJoin=@PrimaryKeyJoinColumn(name="CUST_ID"))
     public class Customer { ... }
     
     Tips:Customer类映射到两个表，主表名是CUSTOMER，从表名是CUST_DETAIL，从表的主键列和主表的主键列类型相同，列名为CUST_ID。
     ```

4. @**SecondaryTable**：当一个entity class映射到一个主表和多个从表时，用SecondaryTables来定义各个从表的属性。

   - value： 定义一个SecondaryTable数组，指定每个从表的属性。

     ```java
     @Table(name = "CUSTOMER")
     @SecondaryTables( value = {
     	@SecondaryTable(name = "CUST_NAME", pkJoin = { @PrimaryKeyJoinColumn(name = "STMO_ID", referencedColumnName = "id") }),
     	@SecondaryTable(name = "CUST_ADDRESS", pkJoin = { @PrimaryKeyJoinColumn(name = "STMO_ID", referencedColumnName = "id") }) 
     })
     public class Customer {...}
     ```

5. **<span style='color:red;'>@UniqueConstraint</span>**：UniqueConstraint定义在Table或SecondaryTable元数据里，用来指定建表时需要建唯一约束的列。

   - columnNames:定义一个字符串数组，指定要建唯一约束的列名。

     ```java
     @Entity
     @Table(name="EMPLOYEE", uniqueConstraints={@UniqueConstraint(columnNames={"EMP_ID", "EMP_NAME"})}
     )
     public class Employee { ... }
     ```

6. **<span style='color:red;'>@Id</span>**：设置变量为主键ID

7. **<span style='color:red;'>@GeneratedValue（strategy = GenerationType.AUTO）</span>**：设置主键的生成策略

   - TABLE：JPA机制，使用一个特定的数据库表格来保存主键
   - SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列。
   - IDENTITY：主键由数据库自动生成（主要是支持自动增长的数据库，如mysql）
   - AUTO：主键由程序自动选择主键生成策略

8. **@Basic**：指定实体属性的加载方式，如@Basic(fetch=FetchType.LAZY)。

9. **<span style='color:red;'>@Column(...)</span>**：表示属性所对应字段名进行个性化设置

   - name：字段在数据库表中所对应字段的名称
   - unique：该字段是否为唯一标识，默认为false。如果表中有一个字段需要唯一标识，则既可以使用该标记，也可以使用@Table标记中的@UniqueConstraint。
   - nullable：该字段是否可以为null值，默认为true。
   - insertable：表示在使用“INSERT”脚本插入数据时，是否需要插入该字段的值。
   - updatable：表示在使用“UPDATE”脚本插入数据时，是否需要更新该字段的值。
     - insertable和updatable属性一般多用于只读的属性，例如主键和外键等。这些字段的值通常是自动生成的。
   - columnDefinition：表示创建表时，该字段创建的SQL语句，一般用于通过Entity生成表定义时使用。
   - table：表示当映射多个表时，指定表的表中的字段。默认值为主表的表名。
   - length：表示字段的长度，当字段的类型为varchar时，该属性才有效，默认为255个字符。
   - precision和scale：precision属性和scale属性表示精度，当字段类型为double时，precision表示数值的总长度，scale表示小数点所占的位数。

10. **<span style='color:red;'>@Transient</span>**：表示属性并非数据库表字段的映射,ORM框架将忽略该属性

11. **<span style='color:red;'>@Temporal</span>**：当我们使用到java.util包中的时间日期类型，则需要此注释来说明转化成java.util包中的类型。

    　　注入数据库的类型有三种：

        　　　　TemporalType.DATE（2020-06-01）
        
        　　　　TemporalType.TIME（20:00:00）
        
        　　　　TemporalType.TIMESTAMP（2020-06-01 20:00:00.000000001）

12. **@IdClass**：当entity class使用复合主键时，需要定义一个类作为id class。

      - id class必须符合以下要求:类必须声明为public，并提供一个声明为public的空构造函数。必须实现Serializable接口，覆写 equals() 和 hashCode()方法。entity class的所有id field在id class都要定义，且类型一样。

13. **@EmbeddedId**：复合主键，结合**@Embeddable**一起使用

      - ```
           @Embeddable
           public class PeopleKey implements Serializable  {
                
               @Column(name = "name")
               private String name;
                
               @Column(name = "idcardno")
               private String idcardno;
               // 省略setter,getter方法
               }
           }　
           
           @Entity
           @Table(name = "people")
           public class People extends PeopleKey{
           　　　　// 复合主键要用这个注解
               @EmbeddedId
               private PeopleKey id;
            
               // 省略setter,getter方法
           }
           ```

      - 

14. **@Enumerated**：使用此注解映射枚举字段，以String类型存入数据库

      注入数据库的类型有两种：EnumType.ORDINAL（Interger）、EnumType.STRING（String）

15. **@Embedded**、**@Embeddable**：当一个实体类要在多个不同的实体类中进行使用，而其不需要生成数据库表。

      - @Embedded将几个字段组合成一个类,并作为整个Entity的一个属性.
           例如User包括id,name,city,street,zip属性.
           我们希望city,street,zip属性映射为Address对象.这样,User对象将具有id,name和address这三个属性.
           Address对象必须定义为@Embededable

          @Embeddable：注解在类上，表示此类是可以被其他类嵌套
          
          @Embedded：注解在属性上，表示嵌套被@Embeddable注解的同类型类

      ```
      只使用其中一个注解，嵌套类中有被嵌套类属性
      覆盖@Embeddable类中字段的列属性@AttributeOverrides和@AttributeOverride。
      这两个注解是用来覆盖@Embeddable类中字段的属性的。
      @AttributeOverrides：里面只包含了@AttributeOverride类型数组；
      @AttributeOverride：包含要覆盖的@Embeddable类中字段名name和新增的@Column字段的属性；
      
      Person类如下：
      @Entity
      public class Person implements Serializable{
          @Embedded
          @AttributeOverrides({
          	@AttributeOverride(name="country", column=@Column(name = "person_country", length = 25, nullable = false)),
              @AttributeOverride(name="city", column = @Column(name = "person_city", length = 15))
          })
          private Address address;
      
      	private static final long serialVersionUID = 8849870114127659929L;
          //setter、getter、
      }
      
      Address类如下：
      @Embeddable
      public class Address implements Serializable{
      
          @Column(nullable = false)
          private String country;
          @Column(unique = true)
          private String city;
          
          private static final long serialVersionUID = 8849870114128959929L;
          // setter、getter
      }
      
      tips: 可多层嵌入实体类属性
      ```

16. **@ElementCollection**：集合映射

    - @ElementCollection主要用于映射非实体(embeddable或basic)，而@OneToMany用于映射实体。

17. **@OrderBy**：在加载数据的时候可以为其指定顺序。一般和 @OneToMany 一起使用@OrderBy("role_name DESC") 关联查询的时候的排序

18. **@MapKey**：在一对多，多对多关系中，我们可以用Map来保存集合对象。默认用主键值做key，如果使用复合主键，则用id class的实例做key，如果指定了name属性，就用指定的field的值做key。

    - name: 用来做key的field名字

    - ```
       Person和Book之间是一对多关系。Person的books字段是Map类型，用Book的no字段的值作为Map的key。
       
       @Table(name = "PERSON")
       public class Person {
       	@OneToMany(targetEntity = Book.class, cascade = CascadeType.ALL, mappedBy = "person")
       	@MapKey(name = "no")
       	private Map books = new HashMap();
           }
       }
       ```

19. **@CreatedDate**、**@CreatedBy**、**@LastModifiedDate**、**@LastModifiedBy**：

      - 表示字段为创建时间字段（insert自动设置）、创建用户字段（insert自动设置）、最后修改时间字段（update自定设置）、最后修改用户字段（update自定设置）

      - 使用：首先申明实体类，需要在类上加上注解`@EntityListeners(AuditingEntityListener.class)`，其次在application启动类中加上注解`EnableJpaAuditing`，同时在需要的字段上加上`@CreatedDate`、`@CreatedBy`、`@LastModifiedDate`、`@LastModifiedBy`等注解。这个时候，在jpa.save方法被调用的时候，时间字段会自动设置并插入数据库，但是CreatedBy和LastModifiedBy并没有赋值，因为需要实现`AuditorAware`接口来返回你需要插入的值。

20. **@MappedSuperclass**：注解的类将不是完整的实体类，不会映射到数据库表，但其属性将映射到子类的数据库字段。实现将实体类的多个属性分别封装到不同的非实体类中。

      - ```
           注解的类将不是完整的实体类，不会映射到数据库表，但其属性将映射到子类的数据库字段
           注解的类不能再标注@Entity或@Table注解，也无需实现序列化接口
           
           注解的类继承另一个实体类 或 标注@MappedSuperclass类，他可使用@AttributeOverride 或 @AttributeOverrides注解重定义其父类属性映射到数据库表中字段。
           ```

21. **@Version**： Version指定实体类在乐观事务中的version属性。

22. 其他注解查询资料……



######<span style='color:red;'>映射关系的注解</span>

```
级联关系类型：
	CascadeType.REFRESH：级联刷新，当多个用户同时作操作一个实体，为了用户取到的数据是实时的，在用实体中的数据之前就可以调用一下refresh()方法
	CascadeType.REMOVE：级联删除，当调用remove()方法删除Order实体时会先级联删除OrderItem的相关数据
	CascadeType.MERGE：级联更新，当调用了Merge()方法，如果Order中的数据改变了会相应的更新OrderItem中的数据
	CascadeType.ALL：包含以上所有级联属性
	CascadeType.PERSIST：级联保存，当调用了Persist() 方法，会级联保存相应的数据
```

1. **<span style='color:red;'>OneToOne</span>**：**一对一**的关联

   ![](\oneToOne.png)

   ```java
   案例：一个学生对应一个班级，添加学生的同时添加班级，Student类的内容如下：
   @OneToOne(cascade = CascadeType.PERSIST)
   //关联的外键字段 grade_id 指的是t_student表中的字段，cascade属性设置级联操作
   @JoinColumn(name = "grade_id")
   private Grade grade;
   
   如果需要双向关联，Grade类的内容如下：
   @OneToOne(mappedBy = "grade")
   private Student student;
   ```

   

2. **<span style='color:red;'>ManyToOne</span>**：**多对一**的映射,该注解标注的属性通常是数据库表的外键

   **<span style='color:red;'>OneToMany</span>**： **一对多**的关联,该属性应该为集体类型,在数据库中并没有实际字段

   -  ![](\oneToMany & ManyToOne.png)

3. **<span style='color:red;'>ManyToMany</span>**：**多对多**的关联。多对多关联上是两个一对多关联,但是在ManyToMany描述中,中间表是由ORM框架自动处理。

   - ![](\ManyToMany.png)

4. **<span style='color:red;'>@JoinColumn</span>**：指定一个实体组织或实体集合。用在“多对一”和“一对多”的关联中。一个关联字段。如果在entity class的field上定义了关系（one2one或one2many等），我们通过JoinColumn来定义关系的属性。JoinColumn的大部分属性和Column类似。

  1. 用法：@JoinColumn 主要配合 @OneToOne、@ManyToOne、@OneToMany 一起使用，单独使用没有意义。 @JoinColumn 定义多个字段的关联关系

  - name:列名。

  - referencedColumnName:该列指向列的列名（建表时该列作为外键列指向关系另一端的指定列）

  - unique: 是否唯一

  - nullable: 是否允许为空

  - insertable: 是否允许插入

  - updatable: 是否允许更新

  - columnDefinition: 定义建表时创建此列的DDL

  - secondaryTable: 从表名。如果此列不建在主表上（默认建在主表），该属性定义该列所在从表的名字。

    ```java
    示例：Custom和Order是一对一关系。在Order对应的映射表建一个名为CUST_ID的列，该列作为外键指向Custom对应表中名为ID的列。
    public class Custom {
        @OneToOne
    	@JoinColumn(name="CUST_ID", referencedColumnName="ID", unique=true, nullable=true, updatable=true)
    	public order getOrder() {
    		return order;
    	}
    }
    ```

5. **<span style='color:red;'>@JoinColumns</span>**：如果在entity class的field上定义了关系（one2one或one2many等），并且关系存在多个JoinColumn，用JoinColumns定义多个JoinColumn的属性。

   - value: 定义JoinColumn数组，指定每个JoinColumn的属性。

   - ```
      示例：Custom和Order是一对一关系。在Order对应的映射表建两列，一列名为CUST_ID，该列作为外键指向Custom对应表中名为ID的列,另一列名为CUST_NAME，该列作为外键指向Custom对应表中名为NAME的列。
      public class Custom {
          @OneToOne
      	@JoinColumns({
          	@JoinColumn(name="CUST_ID", referencedColumnName="ID"),
          	@JoinColumn(name="CUST_NAME", referencedColumnName="NAME")
      	})
      	public order getOrder() {
      		return order;
      	}
      }
      ```

6. **<span style='color:red;'>@JoinTable</span>**：

   1. ```
      @ManyToMany
      @JoinTable(
      	name = "user_role",
      	joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
          inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
      )
      private Set<Role> roles; // 角色外键
      ```

   

###### **<span style='color:red;'>验证注解</span>**

1. @Pattern
   String
   通过正则表达式来验证字符串
   @Pattern(regex=”[a-z]{6}”)

2. @Length
   String
   验证字符串的长度
   @length(min=3,max=20)

3. @Email
   String
   验证一个Email地址是否有效
   @email

4. @Range
   Long
   验证一个整型是否在有效的范围内
   @Range(min=0,max=100)

5. @Min
   Long
   验证一个整型必须不小于指定值
   @Min(value=10)

6. @Max
   Long
   验证一个整型必须不大于指定值
   @Max(value=20)

7. @Size
   集合或数组
   集合或数组的大小是否在指定范围内
   @Size(min=1,max=255)

   Tips：以上每个注解都可能性有一个message属性，用于在验证失败后向用户返回的消息



**Tips：**

1. **使用SpringDataJPA与Lombok时会遇到问题**

2. **循环引用问题**

   - `@JsonIgnore`：是Jackson提供的注解，在实体类的属性上加上该注解，这样在json序列化时会将java bean中的对应的属性忽略掉，同样jpa在查询时也会忽略对应的属性，便可以解决循环查询的问题。
   - `@JsonIgnoreProperties`注解：是Jackson提供的注解，`@JsonIgnoreProperties`和`@JsonIgnore`用法差不多，`@JsonIgnoreProperties`可以更加细粒度的选择忽略关联实体中的属性。如果需要关联查询，又想控制关联查询的类的属性，可以使用该注解。
   - 返回自定义包装类：注意此时是一个接口，里面的属性是实体类中对应属性的 get 方法。使用Vo可以灵活决定返回结果的字段。

3. **N+1查询问题**

   1. 实体图（EntityGraph）

   - ```
     @NamedEntityGraphs注解定义实体图，可以定义多个实体图
     
     @Entity
     @Table(name = "user") //对应数据库中的表名
     // EntityGraph(实体图)，解决查询N+1问题
     @NamedEntityGraphs({        
     	@NamedEntityGraph(name = "user.all",                
     		attributeNodes = { // attributeNodes 用来定义需要立即加载的属性                        				@NamedAttributeNode("articleList"), // 无延伸                        							@NamedAttributeNode("roles"), // 无延伸                
     		}        
     	),
     })
     public class User extends BaseData {
         ...
     }
     
     //重写findAll(Pageable pageable)方法,用实体图查询
     @EntityGraph(value = "user.all", type = EntityGraph.EntityGraphType.FETCH)
     @Override
     Page<User> findAll(Pageable pageable);
     ```

4. **外键维护**

   ​		单向使用一边注解即可，无需mappby，多的一方获取少的一方默认是左外连接，区别于SpringData默认接口查询方法是懒加载。

   ​		双向必须通过mappedBy指定关系维护端。

   ```
   ##双向一对多：  （顾客、订单为例）
   	1=被维护端@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.REMOVE},mappedBy="customer")
   	n=关系维护端@JoinColumn(name="CUSTOMER_ID")		//可选 因为必须通过mapperby指定关系，否则生成表数错误 如果两者都没，就会在两边都建立外键造成删除不了表，执行以下sql才可删除	
   	set @@foreign_key_checks=OFF;临时关闭约束	
   	SET FOREIGN_KEY_CHECKS=0;关闭约束
   	 @ManyToOne(fetch=FetchType.LAZY)
   	*保存
   	1）建议先保存1的一方	顾客表采用表主键策略，订单id自增	5条语句：查更+3条insert
   	2）先保存n的一方  7条sql：2条oder insert+2条主键查更+1条customer insert+2条order update
   	*不能先删除1的一端，有外键约束，设置级联可连带删除n的一端
   	*在1的一方设置懒加载策略，即两次查询，否则默认左外连接
   	*默认查1的一方即是会左外连接连带查出n的一方的，只是可以设置加载策略为懒加载
   ##双向一对一：（经理、部门为例）
   	1=@OneToOne(fetch=FetchType.LAZY)
   	1=@OneToOne(mappedBy="mgr") 必须有mapperby 否则两边都生成外键无法删除
   	1）先保存没有外键的一方,2条sql
   	2）先保存外键一方，3条其中最后一条更新
   	*加载策略可查出来优化 如部门类设置懒加载，经理默认，查部门，3条sql：先查部门，再经理，懒加载部门第二条
   	*先查经理默认左外连接 1条sql
   	
   ##双向多对多：（商品类目、商品为例）
   	n=@JoinTable(name="ITEM_CATEGORY",
   		joinColumns={@JoinColumn(name="ITEM_ID", referencedColumnName="ID")},
   		inverseJoinColumns={@JoinColumn(name="CATEGORY_ID", referencedColumnName="ID")})
   	@ManyToMany
   	n=@ManyToMany(mappedBy="categories")
   	*一定要有维护关系且只能通过mapperby指定，不能通过@JoinColumn指定，使用一个或多个@JoinColumn都将生成表个数错误 都是4张
   	*多对多保存数据8条sql：2+2+4条桥表
   	*多对多查数据，两边查都一样的sql语句，默认就是懒加载，区别于上两种默认左外连接
   ```

   

5. 

#### Dao层

创建Dao接口继承JpaRepository接口，JpaRepository需要泛型接口参数，第一个参数是实体，第二则是主键的类型，也可以是Serializable。

JpaRepository同时继承了PagingAndSortingRepository和QueryByExampleExecutor接口。

PagingAndSortingRepository接口包含了全表查询时的分页查询和排序。

QueryByExampleExecutor接口是用来做复杂查询的

```dart
@NoRepositoryBean
public interface JpaRepository<T, ID> extends PagingAndSortingRepository<T, ID>, QueryByExampleExecutor<T> {
    List<T> findAll();

    List<T> findAll(Sort var1);

    List<T> findAllById(Iterable<ID> var1);

    <S extends T> List<S> saveAll(Iterable<S> var1);

    void flush();

    <S extends T> S saveAndFlush(S var1);

    void deleteInBatch(Iterable<T> var1);

    void deleteAllInBatch();

    T getOne(ID var1);

    <S extends T> List<S> findAll(Example<S> var1);

    <S extends T> List<S> findAll(Example<S> var1, Sort var2);
}

```

分页及排序

```
Sort sort = new Sort(Sort.Direction.DESC, "updateTime","createTime");
Pageable pageable = PageRequest.of(page, size, sort);
```

#### 接口规范方法名查询

关键字

![关键字](\keyword.png)

#### 自定义查询Using @Query  

-   JPQL语句

  - 	@Query("update User u set u.age = ?1 where u.name = ?2")
    	@Modifying
      	Integer updateAgeByName(int age, String name);
      			
      	@Query("from User where id=?1")
      	String getNameFromIdTypeOne(Long id);
      	
      	@Query(" from User where userName like %:userName% ")
      	String getNameFromIdTypeOne(@Param("userName") String userName);
      	
      	@Query("select u.name from User u where u.id=?1")
      	String getNameFromIdTypeTwo(Long id);

- 本地查询 (nativeQuery= true)  

  - ```
    /** 
    * 编写原生SQL语句
    * 本地查询
    */
    @Query(value = "select * from user where name like CONCAT('%',?1,'%')", nativeQuery = true)
    List<User> findByNameLocal(String name);
    ```



#### 自定义接口实现

自定义接口，自定义实现方式

####Projections

自定义返回接收实体

1. 创建一个接口，接口中定义需要的所有属性的get方法；

2. 将接口作为方法返回值

   - 字段重组：在接口属性get方法使用spring的@value注解

     ```java
     interface NamesOnly {
       @Value("#{target.firstname + ' ' + target.lastname}") // SpEL的语法，target即原始类型的对象
       String getFullName();
       …
     }
     
     JAVA8 接口的default方法：
     interface NamesOnly {
       String getFirstname();
       String getLastname();
     
       default String getFullName() {
         return getFirstname.concat(" ").concat(getLastname());
       }
     }
     ```

动态Projection使用泛型实现

#### Specifications动态查询

1.  继承接口JpaSpecificationExecutor
2. 重写Sepcfication接口的toPredicate方法

```
@Autowired
private UserDao userDao;

public List<User> findUserByNameAndSex(String name, Integer sex) {
    return userDao.findAll(new Specification<User>() {
        @Override
        public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            // 构建查询条件并返回
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("name"), name),
                    criteriaBuilder.equal(root.get("sex"), sex)
            );
        }
    });
}


/**
 * 表关联查询
 * @param articleId
 * @param roleId
 * @return
 */
public List<User> findUserByArticleAndRole(String articleId, String roleId) {
    return userDao.findAll(new Specification<User>() {
        @Override
        public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            // 方式1
            ListJoin<User, Article> articleJoin = root.join(root.getModel().getList("articleList", Article.class), JoinType.LEFT);
            SetJoin<User, Role> roleJoin = root.join(root.getModel().getSet("roles", Role.class), JoinType.LEFT);
            // 方式2
            //Join<User, Article> articleJoin = root.join("articleList", JoinType.LEFT);
            //Join<User, Role> roleJoin = root.join("roles", JoinType.LEFT);

            Predicate predicate = criteriaBuilder.or(
                    criteriaBuilder.equal(articleJoin.get("id"), articleId),
                    criteriaBuilder.equal(roleJoin.get("id"), roleId)
            );
            return predicate;
        }
    });
}
```

