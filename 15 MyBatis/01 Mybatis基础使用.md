#### 1 Mybatis简介
##### 1.1 ORM是什么? 
对象关系映射(ORM Object Relational Mapping)，ORM模型就是数据库的表与简单Java对象(POJO)的映射模型，
它主要解决数据库数据和POJO对象的相互映射;

##### 1.2 Mybatis是什么?
MyBatis前身是iBatis,其源于 "Internet" 和"ibatis" 的组合，本质是一种半自动的ORM框架,除了POJO和映射关系之外，
还需要编写SQL语句;  

MyBatis 的3要素：
> SQL
> 映射规则  
> POJO  

##### 1.3 为什么使用MyBatis?
传统的JDBC编程存在的弊端：
> 1、工作量大,操作数据库至少要5步;  
> 2、业务代码和技术代码耦合;  
> 3、连接资源手动关闭,带来了隐患;

ORM带来的好处：
> 更加贴合面向对象的编程语意  
> 技术和业务解耦, Java程序员无需对数据库相关的知识深入了解  
> 无需关心数据库连接资源的释放

MyBatis的优势：
> 几乎可以替换JDBC  
> 高度灵活  
> 基于底层SQL的优化能力  
> 学习门槛低，易于维护  

MyBatis的劣势： 
> 开发工作量相对较大

#### 2 MyBatis简单实例
创建Gradle项目结构
```gitignore
mybatis-basic
    │  build.gradle
    │  gradlew
    │  gradlew.bat
    │  settings.gradle
    │
    └─src
        ├─main
        │  ├─java
        │  │  └─com
        │  │      └─yong
        │  │          └─mybatis
        │  │              └─basic
        │  │                  ├─dao
        │  │                  │      IUserInfoDao.java
        │  │                  │
        │  │                  └─entity
        │  │                          UserInfo.java
        │  │
        │  └─resources
        │      │  mybatis-config.xml
        │      │
        │      └─mapper
        │              IUserInfoDao.xml
        │
        └─test
            ├─java
            │  └─com
            │      └─liyong
            │          └─mybatis
            │              └─basic
            │                      QuickStart.java
            │
            └─resources
                    db.properties
```

配置依赖
```groovy
plugins {
    id 'java-library'
}

dependencies {
    api 'org.apache.commons:commons-math3:3.6.1'
    implementation 'org.springframework:spring-context:5.2.6.RELEASE'
    implementation 'com.google.guava:guava:28.0-jre'
    implementation 'org.mybatis:mybatis:3.5.5'
    runtimeOnly("mysql:mysql-connector-java:8.0.20")
    testImplementation 'junit:junit:4.12'
}

tasks.withType(JavaCompile) {  
    options.encoding = "UTF-8"  
}
```

创建数据库：
```sql
CREATE TABLE `USER_INFO`  (
  `USER_ID` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USERNAME` varchar(126) NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(126) NOT NULL COMMENT '密码',
  `AGE` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`USER_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000;
```

创建实体：UserInfo.java
```java
package com.yong.mybatis.basic.entity;

public class UserInfo {
	private Long userId;
	private String username;
	private String password;
	private Integer age;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
}
```

创建接口：IUserInfoDao.java
```java
package com.yong.mybatis.basic.dao;

import com.yong.mybatis.basic.entity.UserInfo;

import java.util.Optional;

public interface IUserInfoDao {
	Optional<UserInfo> selectUserInfo(Long userId);
    int insert(UserInfo userInfo);
}
```

创建XML映射：IUserInfoDao.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.yong.mybatis.basic.dao.IUserInfoDao">
	<select id="selectUserInfo" resultType="UserInfo">
		select * from USER_INFO where USER_ID = #{userId}
	</select>

	<insert id="insert" parameterType="UserInfo">
		insert into USER_INFO
		(USER_ID, USERNAME, PASSWORD, AGE)
		values
		(#{userId}, #{username}, #{password}, #{age})
	</insert>
</mapper>
```

创建数据库配置文件：db.properties
```properties
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://192.168.10.30:3306/test?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&createDatabaseIfNotExist=true
username=root
password=123456
```

创建MyBatis配置文件：mybatis-config.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <properties resource="db.properties"/>

    <typeAliases>
        <package name="com.yong.mybatis.basic.entity"/>
    </typeAliases>

    <environments default="dev">
        <environment id="dev">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
        <mapper resource="mapper/IUserInfoDao.xml"/>
    </mappers>

</configuration>
```

编写测试类：QuickStart.java
```java
package com.yong.mybatis.basic;

import com.yong.mybatis.basic.dao.IUserInfoDao;
import com.yong.mybatis.basic.entity.UserInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class QuickStart {

	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void init() throws IOException {
		String resource = "mybatis-config.xml";
		try (InputStream inputStream = Resources.getResourceAsStream(resource)) {
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		}
	}

	@Test
	public void quickStart() {
		try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
			IUserInfoDao userInfoDao = sqlSession.getMapper(IUserInfoDao.class);
			Optional<UserInfo> userInfo = userInfoDao.selectUserInfo(10L);
			System.out.println(userInfo.isPresent());
		}
	}

	@Test
	public void testInsert() {
		try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
			IUserInfoDao userInfoDao = sqlSession.getMapper(IUserInfoDao.class);
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(11L);
			userInfo.setUsername("u1");
			userInfo.setPassword("p1");
			userInfo.setAge(18);
			int count = userInfoDao.insert(userInfo);
			System.out.println("insert success count: " + count);
			sqlSession.commit();
		}
	}
}
```

执行测试用例：
quickStart/testInsert即可完成相关查询、插入测试了


mybatis-config.xml配置说明  
`<properties resource="db.properties"/>` 用于引入数据库相关配置属性，用于xml中进行替换。  
`<typeAliases>` 设置实体类型别名，在sql的 `映射xml` 中 `resultType="UserInfo"` 可以直接使用类名进行引用。   
`<environments default="dev">` 用于多个配置数据源，`default` 用于指定一个默认的数据源。  
`<mappers>` 用于引入`实体映射XML`  

| 序号 | 属性名              | 说明 | 备注 |
| ---- | ----               | ---- | ---- |
| 1    | properties         | 定义配置，配置的属性可以在整个配置文件中其他位置进行引用; | 重要，优先使用 | property配置文件解耦 |
| 2    | settings           | 设置，用于指定MyBatis的一些全局配置属性,这些属性非常重要，后面专门说明 | 它们会改变MyBatis的运行时行为; |
| 3    | typeAliases        | 别名，为Java类型设置一个短的名字 ，映射时方便使用;分为系统定可以通过xm和注解配置 | 义别名和自定义别名; |
| 4    | typeHandlers       | 用于jdbcType与javaType之间的转换; | 无特殊需求不需要调整; | 后面专题说明 |
| 5    | objectFactory      | MyBatis每次创建结果对象的新实例时,它都会使用对象工厂 | 大部分场景下无需修改 | (objectFactory)去构建POJO |
| 6    | plugins            | 插件, MyBatis允许你在已映射的语句执行过程中的某-点进行拦截调用; | 后面专题说明 | 
| 7    | environments       | 用于配置多个数据源,每个数据源分为数据库源和事务的配置; | 在多数据源环境使用 |
| 8    | databaseIdProvider | MyBatis可以根据不同的数据库厂 商执行不同的语句,用于一个系统内多厂商数据源支持。 | 大部分场景下无需修改 |
| 9    | mappers            | 配置引入映射器的方法。可以使用相对于类路径的资源引用、或完全后面会限定资源定位符(包括file///的URL )， 或类名和包名等等 | 专题说明 |

`org.apache.ibatis.type.TypeHandlerRegistry` 提供了JavaType JDBCType的映射关系  

MyBatis为什么是半自动的ORM框架？
> ORM是Object和Relation之间的映射，包括Object->Relation和Relation->Object两方面。Hibernate是个完整的ORM框架，而MyBatis完成的是Relation->Object，也就是其所说的Data Mapper Framework。
>  
> JPA是ORM框架标准，主流的ORM框架都实现了这个标准。MyBatis没有实现JPA，它和ORM框架的设计思路不完全一样。MyBatis是拥抱SQL，而ORM则更靠近面向对象，不建议写SQL，实在要写，则推荐你用框架自带的类SQL代替。MyBatis是SQL映射框架而不是ORM框架，当然ORM和MyBatis都是持久层框架。
>  
> 最典型的ORM 框架是Hibernate，它是全自动ORM框架，而MyBatis是半自动的。Hibernate完全可以通过对象关系模型实现对数据库的操作，拥有完整的JavaBean对象与数据库的映射结构来自动生成SQL。而MyBatis仅有基本的字段映射，对象数据以及对象实际关系仍然需要通过手写SQL来实现和管理。
>  
> Hibernate数据库移植性远大于MyBatis。Hibernate通过它强大的映射结构和HQL语言，大大降低了对象与数据库（oracle、mySQL等）的耦合性，而MyBatis由于需要手写SQL，因此与数据库的耦合性直接取决于程序员写SQL的方法，如果SQL不具通用性而用了很多某数据库特性的SQL语句的话，移植性也会随之降低很多，成本很高。


##### Mybatis配置environments

- environment 元素是配置一个数据源的开始,属性id是它的唯一标识
- transactionManager 元素配置数据库事务,其中type属性有三种配置方式
    - jdbc 采用jdbc的方式管理事务;
    - managed 采用容器的方式管理事务,在JNDI数据源中使用;
    - 自定义 自定义数据库事务管理办法;
- dataSource 元素配置数据源连接信息, type属性是连接数据库的方式配置,有四种配置方式
    - UNPOOLED 非连接池方式连接
    - POOLED 使用连接池连接
    - JNDI 使用JNDI数据源
    - 自定义数据源

##### MyBatis配置Mapper
- 1、用classpath下的资源引入
```xml
    <mappers>
        <mapper resource="mapper/IUserInfoDao.xml"/>
    </mappers>
```

- 2、用类注册方式引入
```xml
    <mappers>
        <mapper class="com.yong.mybatis.basic.dao.IUserInfoDao"/>
    </mappers>
```

- 3、使用包名引入
```xml
    <mappers>
        <package name="com.yong.mybatis.basic.dao"/>
    </mappers>
```

- 4、用文件的全路径引入
>PS:第一种方式用的推荐使用，类文件和mapper文件可以不需要放在一个文件夹中，xm|文件也不会和java文件混合在一起;

##### MyBatis的setting
| 设置参数 | 描述               | 有效值 | 默认值 |
| ----     | ----              | ----   | ----   |
| cacheEnabled                 | 该配置影响的所有映射器中配置的缓存的全局开关 | true/false | true |
| lazyLoadingEnabled           | 延迟加载的全局开关。<br>当开启时,所有关联对象都会延迟加载。<br>特定关联关系中可通过设置fetchType属性来覆盖该项的开关状态 | true/false | false |
| aggressiveLazyLoading        | 当启用时，对任意延迟属性的调用会使带有延迟加载属性的对象完整加载；<br>反之每种属性将会按需加载。  | true/false | true |
| multipleResultSetsEnabled    | 是否允许单一语句返回多结果集(需要兼容驱动)。 | true/false | true |
| useColumnLabel               | 使用列标签代替列名。不同的驱动在这方面会有不同的表现，<br>具体可参考相关驱动文档或通过测试这两种不同的模式来观察所用驱动的结果。  | true/false | true |
| useGeneratedKeys             | 允许JDBC支持自动生成主键，需要驱动兼容。<br>如果设置为true则这个设置强制使用自动生成主键,尽管一些驱动不能兼容但仍可正常工作(比如Derby). | true/false | False |
| autoMappingBehavior          | 指定MyBatis应如何自动映射列到字段或属性。<br>NONE表示取消自动映射; PARTIAL只会自动映射没有定义嵌套结果集映射的结果集。<br>FULL 会自动映射任意复杂的结果集(无论是否嵌套)。 | NONE, PARTIAL FULL  | PARTIAL |
| defaultExecutorType          | 配置默认的执行器。SIMPLE 就是普通的执行器; <br>REUSE执行器会重用预处理语句( prepared statements ) ; BATCH执行器将重用语句并执行批量更新。 | SIMPLE、REUSE、BATCH | SIMPLE |
| defaultStatementTimeout      | 设置超时时间 ,它决定驱动等待数据库响应的秒数。 | Any positive integer | Not Set(null) |
| safeRowBoundsEnabled         | 允许在嵌套语句中使用分页(RowBounds). | true/false | False |
| mapUnderscoreToCamelCase     | 是否开启自动驼峰命名规则(camel case)映射,<br>即从经典数据库列名A_COLUMN到经典Java属性名aColumn的类似映射。 | true/false | False |
| localCacheScope              | MyBatis利用本地缓存机制(Local Cache)防止循环引用(circular references)和加速重复嵌套查询。<br>默认值为SESSION，这种情况下会缓存一个会话中执行的所有查询。<br>若设置值为STATEMENT，本地会话仅用在语句执行上，<br>对相同SqlSession的不同调用将不会共享数据。 | SESSION/STATEMENT | SESSION |
| jdbcTypeForNull              | 当没有为参数提供特定的JDBC类型时,为空值指定JDBC类型。<br>某些驱动需要指定列的JDBC类型,多数情况直接用一般类型即可，<br>比如NULL. VARCHAR 或OTHER. | JdbcType枚举，最常见的是: <br> NULL VARCHAR and OTHER | OTHER |
| lazyLoadTriggerMethods       | 指定哪个对象的方法触发一次延迟加载。 | 如果是方法列表用逗号隔开; | equals,clone, <br> hashCode,toString
| callSettersOnNulls           | 指定当结果集中值为null的时候是否调用映射对象的setter(map对象时为put)方法,<br>这对于有Map.keySet0依赖或null值初始化的时候是有用的。<br>注意基本类型(int、boolean等)是不能设置成null的。 | true/false | false
| logPrefix                    | 指定MyBatis增加到日志名称的前缀。 | Any String | Not set
| logImpl                      | 指定MyBatis所用日志的具体实现,未指定时将自动查找。 | SLF4J/LOG4J/L0G4J2/ <br>JDK_LOGGING/ <br>COMMONS_LOGGING/ <br>STDOUT_LOGGING/ <br>NO_LOGGING | Not set
| proxyFactory                 | 指定Mybatis创建具有延迟加载能力的对象所用到的代理工具。 | CGLIB |JAVASSIST | 版本3.3.0以上JAVASSIS

##### 基于XML配置的mapper
- cache 给定命名空间的缓存配置。
- cache-ref 其他命名空间缓存配置的引用。
- resultMap 是最复杂也是最强大的元素,用来描述如何从数据库结果集中来加载对象。
- sql 可被其他语句引用的可重用语句块。
- insert 映射插入语句
- update 映射更新语句
- delete 映射删除语句
- select 映射查询语句

###### select元素
- 自动映射
    - 前提: SQL列名和JavaBean的属性是- 致的;
    - 自动映射等级autoMappingBehavior设置为PARTIAL ,需要谨慎使用FULL；
    - 使用resultType;
    - 如果列名和JavaBean不一致,但列名符合单词下划线分割, Java是驼峰命名法，则mapUnderscoreToCamelCase可设置为true ;
- 传递多个查询入参
    - 使用map传递参数;可读性差,导致可维护性和可扩展性差,杜绝使用;
    - 使用注解传递参数;直观明了,当参数较少-般小于5个的时候,建议使用;
    - 使用Java Bean的方式传递参数;当参数大于5个的时候,建议使用;

| 元素          | 说明 | 备注 |
| ----          | ---- | ---- |
| id            | 它和Mapper的命名空间组合起来是唯一的 ，提供给MyBatis调用 | 如果命名空间和id组合起来不唯一，<br> 会抛出异常
| parameterType | 传入参数的类型;可以给出类全名,也可以给出类别名,<br>使用别名必须是MyBatis内部定义或自定义的，<br>基本数据类型: int, String , long, date(不知是sql.date还是util.date);<br>复杂数据类型:类和Map | 可以选择JavaBean，Map等复杂的参数类型传递给SQL
| resultType    | 从这条语句中返回的期望类型的类的完全限定名或别名。<br> 注意如果是集合情形，那应该是集合可以包含的类型,而不能是集合本身。 <br> 使用resultType或resultMap，但不能同时使用定义类的全路径，<br> 在允许自动匹配的情况下，结果集将通过JavaBean的规范映射; <br> 或者定义为int,double,float等参数..<br> 也可以使用别名，但是要符合别名规范，不能和resultMap同时使用。 | 它是我们常用的参数之一，<br>比如我们总计总条数，<br>就可以把它的值设为int
| resultMap     | 外部resultMap的命名引用。使用resultMap或resultType ,但不能同时使用; <br> 它是映射集的引用，将执行强大的映射功能，<br> 我们可以使用resultType或者resultMap其中的一个。<br> resultMap可以给予我们自定义映射规则的机会 | 它是MyBatis最复杂的元素.可以配置映射规则 ,级联, typeHandler等
| flushCache    | 它的作用是在调用SQL后，<br>是否要求MyBatis清空之前查询的本地缓存和级缓存 | true/false .默认为false
| useCache      | 启动二级缓存开关,是否要求MyBatis将此次结果缓存 | true/false ,默认为true
| timeout       | 设置超时时间，超时之后抛出异常，秒 | 默认值为数据库厂商提供的JDBC驱动所设置的秒数
| fetchSize     | 获取记录的总条数设定 | 默认值是数据库厂商提供的JDBC驱动所设的条数

###### resultMap元素属性
- resultMap元素是MyBatis中最重要最强大的元素。它可以让你从90%的JDBC ResultSets数据提取代码中解放出来,在对复杂语句进行联合映射的时候，它很可能可以代替数千行的同等功能的代码。
- ResultMap的设计思想是，简单的语句不需要明确的结果映射，而复杂一点的语句只需要描述它们的关系就行了。

| 属性          | 描述 |
| ----          | ---- |
| id            | 当前命名空间中的一个唯标识，用于标识一个result map. |
| type          | 类的完全限定名,或者一个类型别名 (内置的别名可以参考上面的表格). |
| autoMapping   | 如果设置这个属性，MyBatis将会为这个ResultMap开启或者关闭自动映射。<br>这个属性会覆盖全局的属性autoMappingBehavior.默认值为: unset。 |

###### resultMap元素的子元素
- constructor 用于在实例化类时,注入结果到构造方法中
    - idArg ID参数;标记出作为ID的结果可以帮助提高整体性能
    - arg 将被注入到构造方法的一个普通结果
- id 一个ID结果;标记出作为ID的结果可以帮助提高整体性能
- result 注入到字段或JavaBean属性的普通结果
- association 一个复杂类型的关联;许多结果将包装成这种类型
    - 嵌套结果映射 - 关联可以指定为一个resultMap元素,或者引用一个
- collection 一个复杂类型的集合
    - 嵌套结果映射 - 集合可以指定为一个resultMap元素,或者引用一个
- discriminator 使用结果值来决定使用哪个resultMap
    - case 基于某些值的结果映射
        - 嵌套结果映射 - 一个case也是-一个映射它本身的结果,因此可以包含很多相同的元素,或者它可以参照一个外部的resultMap
        
###### id & result
- id和result都将一个列的值映射到一个简单数据类型(字符串,整型,双精度浮点数日期等)的属性或字段
- 两者之间的唯一不同是，id 表示的结果将是对象的标识属性,这会在比较对象实例时用到。这样可以提高整体的性能,尤其是缓存和嵌套结果映射(也就是联合映射)的时候
        
| 属性 | 描述 |
| ----          | ---- |
| property | POJO中映射到列结果的字段或者属性。 如果POJO的属性匹配的是存在的,和给定SQL列名(column元素)相同的， 那么MyBatis就会自动映射; |
| column | SQL中的列名,或者是列的别名。 一般情况下,这和传递给resultSet.getString(columnName)方法的参数一样。 |
| javaType | 配置的Java的类; |
| jdbcType | 配置的数据库的类型; |
| typeHandler | 类型处理器，使用这个属性，你可以覆盖默认的类型处理器。 这个属性值是一个类型处理器实现类的完全限定名,或者是类型别名。 |

###### constructor
- 一个pojo不存在没有参数的构造方法,就需要使用constructor;
- 为了通过名称来引用构造方法参数,你可以添加@Param注解,指定参数名称的前提下,以任意顺序编写arg元素

| 属性              | 描述 |
| ----              | ---- |
| id                | 命名空间中的唯一标识符，可被用来代表这条语句。 |
| parameterType     | 将要传入语句的参数的完全限定类名或别名。<br>这个属性是可选的,因为MyBatis可以通过TypeHandler推断出具体传入语句的参数，默认值为unset. |
| flushCache        | 将其设置为true，任何时候只要语句被调用，<br>都会导致本地缓存和二级缓存都会被清空，默认值: true (对应插入、更新和删除语句) . |
| timeout           | 这个设置是在抛出异常之前,驱动程序等待数据库返回请求结果的秒数。<br>默认值为unset (依赖驱动)。 |
| statementType     | STATEMENT，PREPARED或CALLABLE的一个.<br>这会让MyBatis分别使用Statement，PreparedStatement或CallableStatement，默认值: PREPARED. |
| useGeneratedKeys  | (仅对insert和update有用)这会令MyBatis 使用JDBC的getGeneratedKeys方法来取出由数据库内部生成的主键(比如:像MySQL和SQL Server这样的关系数据库管理系统的自动递增字段)，默认值: false. |
| keyProperty       | (仅对insert和update有用)唯一标记个属性，<br>MyBatis会通过getGeneratedKeys的返回值或者通过insert语句的selectKey子元素设置它的键值，<br>默认: unset.如果希望得到多个生成的列，也可以是逗号分隔的属性名称列表。 |
| keyColumn         | (仅对insert和update有用)通过生成的键值设置表中的列名,<br>这个设置仅在某些数据库(像PostgreSQL )是必须的，<br>当主键列不是表中的第- -列的时候需要设置。<br>如果希望得到多个生成的列,也可以是逗号分隔的属性名称列表。 |
| databaseld        | 如果配置了databaseldProvider, <br>MyBatis会加载所有的不带databaseld或匹配当前databaseld的语句; <br>如果带或者不带的语句都有，则不带的会被忽略。 |

###### sql元素和参数
- sql元素: 用来定义可重用的SQL代码段,可以包含在其他语句中;
- 参数: 向sql语句中传递的可变参数
    - 预编译 #{} :将传入的数据都当成-个字符串,会对自动传入的数据加一个双引号,能够很大程度防止sql注入;
    - 传值${}传入的数据直接显示生成在sql中,无法防止sql注入;
    - 表名、选取的列是动态的, order by和in操作，可以考虑使用$

###### 注解方式配置
注解方式就是将SQL语句直接写在接口上,对于需求比较简单的系统,效率较高.缺点在于,每次修改sq|语句都要编译代码,对于复杂的sq|语句可编辑性和可读性都差, 一般不建议使用这种配置方式;
- @Select
- @Results
- @Insert
- @Update
- @Delete

###### selectKey元素
| 属性          | 描述 |
| ----          | ---- |
| keyProperty   | selectKey语句结果应该被设置的目标属性。<br>如果希望得到多个生成的列，也可以是逗号分隔的属性名称列表。 |
| keyColumn     | 匹配属性的返回结果集中的列名称如果希望得到多个生成的列,<br>也可以是逗号分隔的属性名称列表。 |
| resultType    | 结果的类型。MyBatis 通常可以推算出来,<br>但是为了更加确定写上也不会有什么问题。<br>MyBatis允许任何简单类型用作主键的类型，包括字符串。<br>如果希望作用于多个生成的列，则可以使用一个包含期望属性的Object 或一个Map. |
| order         | 这可以被设置为BEFORE或AFTER。<br>如果设置为BEFORE,那么它会首先选择主键,<br>设置keyProperty然后执行插入语句。如果设置为AFTER,<br>那么先执行插入语句,然后是selectKey元素，这和像Oracle的数据库相似，<br>在插入语句内部可能有嵌入索引调用。 |
| statementType | 与前面相同，MyBatis支持STATEMENT,PREPARED和CALLABLE语句的映射类型，<br>分别代表PreparedStatement和CallableStatement类型。 |

###### 动态SQL元素
| 元素                      | 作用                      | 备注 |
| ----                      | ----                     | ---- |
| if                        | 判断语句                  | 单条件分支判断 |
| choose、when、otherwise    | 相当于java的case-when     | 多条件分支判断 |
| Trim、where、 set         | 辅助元素                   | 用于处理sql拼装问题 |
| foreach                   | 循环语句                  | 在in语句等列举条件常用,常用于实现,批量操作 |


###### 批量操作
- 通过foreach动态拼装SQL语句
- 使用BATCH类型的executor

##### MyBatis Generator(MBG)
MyBatis Generator: MyBatis的开发团队提供了一个很强大的代码生成器,
代码包含了数据库表对应的实体类、Mapper接口类、Mapper XML文件和Example对象等,
这些代码文件中几乎包含了全部的单表操作方法.使用MBG可以极大程度上方便我们使用MyBatis。
还可以减少很多重复操作;

- generatorConfiguration 根节点
    - properties 用于指定一个需要在配置中解析使用的外部属性文件;
    - classPathEntry 在MBG工作的时候,需要额外加载的依赖包;
    - context 用于指定生成一组对象的环境
        - property (0个或多个) 设置一些固定属性
        - plugin (0个或多个) 定义一个插件,用于扩展或修改通过MBG生成的代码
        - commentGenerator (0个或1个) 该标签用来配置如何生成注释信息
        - jdbcConnection (1个) 必须要有的,使用这个配置链接数据库
        - javaTypeResolver (0个或1个) 指定JDBC类型和Java类型如何转换
        - javaModelGenerator (1个) java模型创建器
        - sqlMapGenerator (0个或1个) 生成SQL map的XML文件生成器
        - javaClientGenerator (0个或1个) 生成Mapper接口
        - table (1个或多个) 选择一个table来生成相关文件,可以有一个或多个table

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
    <!-- context:生成 组对象的环境
            id: 必选，上下义id,用于在生成错误时提示
            defaultModelType: 指定生成对象的样式
                1. conditional: 类似hierarchical;
                2. flat: 所有内容(主键，blob)等全部生成在一个对象中，推荐使用:
                3. hierarchical: 主键生成 个XXKey对象(key class), Blob等单独生成个对象，其他简单属性在个对象中(record class)
            targetRuntime :
                1. MyBatis3: 默认的值，生成店fMyBatis3.x以上版本的内容，包括xXxBySample;
                2, MyBatis3Simple 类似MyBatis3， 只是不生成XXXBySample;
    -->
    <context id= "context1" targetRuntime="MyBatis3" defaultModelType="flat">
        <commentGenerator>
            <!-- 是否去除自动生成的注释true: 是: false:否 -->
            <property name="suppressAllComments" value= "false" />
            <!-- 阻止注释中包含时间戳true:是: false:否 -->
            <property name="suppressDate" value= "true" />
            <!-- 注释是否包含数据库表的注释信息true: 是: false:否 -->
            <property name="addRemarkComments" value= "true" />
        </commentGenerator>

        <!-- java模型创建器， 是必须要的元素负贞: 
                1. key类(见context的defaultModelType): 
                2. java类; 
                3. 查询类
            targetPackage: 生成的类要放的包，真实的包受enableSubPackages属性控制:
            targetProject: 目标项目，指定一个存在的目录下，生成的内容会放到指定目录中，如果目录不存在，MBG不公自动建目录
        -->
        <javaModelGenerator targetPackage="com.enjoylearning.mybatis.entity" targetProject="${project_src}">
            <!-- 设置一个根对象，
                如果设置了这个根对象，那么生成的keyClass或者recordClass会继承这个类；
                在Table的rootClass属性中可以覆盖该选项
                注意: 如果在key class或者record class中有root class相同的属性，MBG就不会重新生成这些俩性了，包括:
                    1，属性名相同，类型相同，们相同的getterfsetter方法;
            -->
            <property name= "rootcClass" value= "com. enjoylearning. mybatis. entity. BaseEntity" />
        </javaModelGenerator>
        
        <!-- 生成SQL map的XML文件生成器，
            targetPackage: 生成的类要放的包，真实的包受enableSubPackages属性控制;
            targetProject: 目标项目，指定一个存在的目录下，生成的内容会放到指定目录中，如果目录不存在，MBG不公自动建目录
        -->
        <sqlMapGenerator targetPackage="." targetProject="${project_ mapper_ xml}">
        </sqIMapGenerator>

        <!-- 对于mybatis来说，即生成Mapper接口，注意，如果没有配置该尤索，那么默认不公生成Mapper接口
                type: 选择怎么生成mapper接口 (在MyBatis3/MyBatis3Simple下)
                    1，ANNOTATEDMAPPER: 会生成使用Mapper接口 + Annotation的方式创建(SQL生成在annotation中)，不会生成对应的XML:
                    2，MIXEDMAPPER: 使用混合配置，会生成Mapper接口，并适当添加合适的Annotation，但是XML会生成在XML中:
                    3，XMLMAPPER: 会生成Mapper接口，接口完全依赖XML;
            注意，如果context是MyBatis3Simple: 只支持ANNOTATEDMAPPER和XMLMAPPER
        -->
        <javaClientGenerator targetPackage= "com. enjoyl earning . mybatis. mapper" targetProject="${project_ src}" type= "XILMAPPER" />
        
        <!-- schema 数据库tableName表明 -->   <!-- %生成所有表 -->
        <table schema="${jdbc_username}" tableName="%" enableCountByExample="false"
            enableUpdateByExample="false" enableDeleteByExample="false"
            enableSelectByExample="false" selectByExampleQueryId="false">
            <generatedKey column="id" sqlstatement= "MySql" />
        </table>
        
    </context>
</generatorConfiguration>
```

> 为什么不建议使用Example？
> 
> 示范choose when的使用
>
> 演示example类的使用
>
> 批量操作怎么拿到POJO的ID?


#### 关联查询
在关系型数据库中,我们经常要处理一对一、一对多的关系。例如，一辆汽车需要有一个引擎,这是一对一的关系。一辆汽车有4个或更多个轮子,这是一对多的关系。关联元素就是专门用来处理关联关系的;
- 关联元素
    - association 一对一关系
    - collection 一对多关系
    - discriminator 鉴别器映射
- 关联方式
    - 嵌套结果: 使用嵌套结果映射来处理重复的联合结果的子集
    - 嵌套查询: 通过执行另外一个SQL映射语句来返回预期的复杂类型

##### 嵌套结果
- association 标签 嵌套结果方式 常用属性:
    - property 对应实体类中的属性名,必填项。
    - javaType 属性对应的Java类型。
    - resultMap 可以直接使用现有的resultMap, 而不需要在这里配置映射关系。
    - columnPrefix 查询列的前缀,配置前缀后,在子标签配置result的column时可以省略前缀

> Tips:  
> 1.resultMap可以通过使用extends实现继承关系 ,简化很多配置工作量;  
> 2.关联的表查询的类添加前缀是编程的好习惯;  
> 3.通过添加完整的命名空间,可以引用其他xm|文件的resultMap;

##### 一对一嵌套查询
- association 标签 嵌套查询方式常用属性:
    - select :另一个映射查询的id, MyBatis会额外执行这个查询获取嵌套对象的结果。
    - column : 列名(或别名),将主查询中列的结果作为嵌套查询的参数。
    - fetchType :数据加载方式,可选值为lazy和eager ,分别为延迟加载和积极加载,这个配置会覆盖全局的lazyLoadingEnabled配置;

> Tips: ”N+1 查询问题”
> 概括地讲,N+1查询问题可以是这样引起的:  
> - 你执行了-个单独的SQL语句来获取结果列表(就是"+1" )。  
> - 对返回的每条记录,你执行了-个查询语句来为每个加载细节(就是"N" )。  
> 这个问题会导致成百.上千的SQL语句被执行。这通常不是期望的。  
> 解决办法:使用"fetchType=lazy" 并且全局setting进行改善:  
```xml
<setting name= "aggessiveLazyL oading" value= "false"/>
```


> 为什么互联网公司喜欢使用嵌套查询？

    
##### 一对多
- 1、collection 支持的属性以及属性的作用和association完全相同
- 2、mybatis会根据id标签 ,进行字段的合并,合理配置好ID标签可以提高处理的效率;

> Tips:
> 如果要配置一个相当复杂的映射, -定要从基础映射开始配置,每增加一些配置就进行对应的测试,在循序渐进的过程中更容易发现和解决问题。


##### discriminator鉴别器映射

在特定的情况下使用不同的pojo进行关联，鉴别器元素就是被设计来处理这个情况的。鉴别器非常容易理解,因为它的表现很像Java语言中的switch语句： 
- discriminator标签常用的两个属性如下:
    - column :该属性用于设置要进行鉴别比较值的列.
    - javaType :该属性用于指定列的类型,保证使用相同的Java类型来比较值。
- discriminator 标签可以有1个或多个case标签，case标签包含以下三个属性。
    - value :该值为discriminator指定column用来匹配的值。
    - resultMap :当column的值和value的值匹配时 ,可以配置使用resultMap指定的映射, resultMap优先级高于resultType。
    - resultType :当column的值和value的值匹配时,用于配置使用resultType指定的映射。

##### 多对多？
> 先决条件一: 多对多需要一种中间表建立连接关系;  
> 先决条件二: 多对多关系是由两个一对多关系组成的，一对多可以也可以用两种方式实现;

#### 缓存
MyBatis包含一个非常强大的查询缓存特性,使用缓存可以使应用更快地获取数据,避免频繁的数据库交互;
- 一级缓存(也叫应用缓存):
    - 一级缓存默认会启用,想要关闭- -级缓存可以在select标签上配置flushCache= "true” ;
    - 一级缓存存在于SqISession的生命周期中,在同一个SqISession中查询时，
    MyBatis 会把执行的方法和参数通过算法生成缓存的键值,将键值和查询结果存入-个Map对象中。
    如果同一个SqISession中执行的方法和参数完全一致,那么通过算法会生成相同的键值,
    当Map缓存对象中己经存在该键值时,则会返回缓存中的对象;
    - 任何的INSERT、UPDATE、 DELETE操作都会清空一级缓存;
- 二级缓存(也叫应用缓存)：
    - 二级缓存存在于SqISessionFactory的生命周期中,可以理解为跨sqISession ;缓存是以namespace为单位的,不同namespace下的操作互不影响。
    - setting参数cacheEnabled ,这个参数是二级缓存的全局开关,默认值是true ,如果把这个参数设置为false ,即使有后面的级缓存配置,也不会生效;
    - 要开启二级缓存,你需要在你的SQL映射文件中添加配置:
    - 字面上看就是这样。这个简单语句的效果如下:
        - 映射语句文件中的所有select 语句将会被缓存。
        - 映射语句文件中的所有insert,update和delete语句会刷新缓存。
        - 缓存会使用Least Recently Used(LRU,最近最少使用的)算法来收回。
        - 根据时间表(比如no Flush Interval没有刷新间隔),缓存不会以任何时间顺序来刷新。
        - 缓存会存储列表集合或对象(无论查询方法返回什么)的 1024个引用。
        - 缓存会被视为是read/write(可读可写)的缓存;
```xml
<cache eviction="FIFO" flushInterval="60000" size="512" readOnly="true" />
```
> Tips:使用二级缓存容易出现脏读,建议避免使用二级缓存,在业务层使用可控制的缓存代替更好;

#### MyBatis-Spring是什么
- Mybatis-spring用于帮助你将MyBatis代码无缝地整合到Spring中。
    - Spring将会加载必要的MyBatis工厂类和session类
    - 提供一个简单的方式来注入MyBatis数据映射器和SqlSession到业务层的bean中。
    - 方便集成spring事务
    - 翻译MyBatis的异常到Spring的DataAccessException异常(数据访问异常)中.
- Mybatis-spring兼容性
    - MyBatis-Spring要求Java5及以上版本还有下面列出的MyBatis和Spring版本:

##### 集成配置最佳实践
1.准备spring项目一个  
2.在pom文件中添加mybatis-spring的依赖  
3.配置SqlSessionFactoryBean  
4.配置MapperScannerConfigurer  
5.配置事务  

##### SqlSessionFactoryBean
- 在MyBatis-Spring中，Sq|SessionFactoryBean 是用于创建Sq| SessionFactory的。
    - dataSource : 用于配置数据源,该属性为必选项,必须通过这个属性配置数据源, 这里使用了上一节中配置好的dataSource数据库连接池。
    - mapper Locations :配置SqlSessionFactoryBean扫描XML映射文件的路径,可以使用Ant风格的路径进行配置。
    - configLocation : 用于配置mybatis config XML的路径,除了数据源外,对MyBatis的各种配直仍然可以通过这种方式进行,
    并且配置MyBatis settings 时只能使用这种方式。
    但配置文件中`任意环境,数据源和MyBatis的事务管理器`都会被忽略; 
    - typeAliasesPackage :配置包中类的别名,配置后,
    包中的类在XML映射文件中使用时可以省略包名部分,直接使用类名。
    这个配置不支持Ant风格的路径,当需要配置多个包路径时可以使用分号或逗号进行分隔。
- 通过MapperScannerConfigurer类自动扫描所有的Mapper接口,使用时可以直接注入接口。
MapperScannerConfigurer中常配置以下两个属性。
    - basePackage :用于配置基本的包路径。可以使用分号或逗号作为分隔符设置多于一个的包路径，每个映射器将会在指定的包路径中递归地被搜索到。
    - annotationClass :用于过滤被扫描的接口,如果设置了该属性,那么MyBatis的接口只有包含该注解才会被扫描进去











