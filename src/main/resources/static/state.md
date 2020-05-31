>**EntityManager管理器**
> 通过源码我们可以看到save方法实质上是调用的EntityManager的方法完成的数据库操作，所以这里有必要介绍下EntityManager接口，在此之前得了解jpa中实体对象拥有的四种状态：
>
>- **瞬时状态（new/transient）**：没有主键，不与持久化上下文关联，即 new 出的对象（但不能指定id的值，若指定则是游离态而非瞬时态）
>- **托管状态（persistent）**：使用EntityManager进行find或者persist操作返回的对象即处于托管状态，此时该对象已经处于持久化上下文中（被EntityManager监控），任何对该实体的修改都会在提交事务时同步到数据库中。
>- **游离状态（detached）**：有主键，但是没有跟持久化上下文关联的实体对象。
>- **删除状态 （deleted）**：当调用EntityManger对实体进行remove后，该实体对象就处于删除状态。其本质也就是一个瞬时状态的对象。
>
>下面的图清晰的表示了各个状态间的转化关系：
>
>![状态间的转化关系](\state.png)
>
>下面介绍下EntityManager接口的几个常用方法：
>
>- **persist()**：将临时状态（无主键）的对象转化为托管状态。由于涉及数据库增删改，执行该语句前需启用事务
>
>
>
>```css
>entityManager.persist(modelObject);
>```
>
>- **merge()**：将游离状态（有主键）的对象转化为托管托管状态，不同于persist()，merger()对于操作的对象，如果对象存在于数据库则对对象进行修改，如果对象在数据库中不存在，则将该对象作为一条新记录插入数据库。
>
>
>
>```css
>entityManager.merge(modelObject);
>```
>
>- **find()与getReference()**：从数据库中查找对象。不同点：当对象不存在时，find()会返回null，getReference()则会抛出javax.persistence.EntityNotFoundException异常。
>
>
>
>```kotlin
>// 参数一：实体类的class，参数二：实体主键值
>entityManager.find(Class<T> ModelObject.class , int key);
>```
>
>- **remove()**：将托管状态的对象转化为删除状态。由于涉及数据库增删改，执行该语句前需启用事务
>
>
>
>```css
>entityManager.remove(entityManager.getReference(ModelObject.class, key));
>```
>
>- **refresh(Object obj)**：重新从数据库中读取数据。可以保证当前的实例与数据库中的实例的内容一致。该方法用来操作托管状态的对象。
>- **contains(Object obj)**：判断对象在持久化上下文（不是数据库）中是否存在，返回true/false。
>- **flush()**：立即将对托管状态对象所做的修改（包括删除）写入数据库。
>   从上面内容我们发现通过EntityManager对实体对象所做的操作实质是让对象在不同的状态间转换，而这些修改是在执行flush()后才会真正的写入数据库。正常情况下不需要手动执行flash()，在事务提交的时候，JPA会自动执行flush()一次性保存所有数据。
>   如果要立即保存修改，可以手动执行flush()。
>   同时我们可以通过`setFlushModel()`方法修改EntityManager的刷新模式。默认为`AUTO`，这种模式下，会在执行查询（指使用JPQL语句查询前，不包括find()和getReference()查询）前或事务提交时自动执行flush()。通过`entityManager.setFlushMode(FlushModeType.COMMIT)`设置为`COMMIT`模式，该模式下只有在事务提交时才会执行flush()。
>- **clear()**：把实体管理器中所有的实体对象（托管状态）变成游离状态，clear()之后，对实体类所做的修改也会丢失。

[https://my.oschina.net/u/3094602/blog/3009226]: https://my.oschina.net/u/3094602/blog/3009226

