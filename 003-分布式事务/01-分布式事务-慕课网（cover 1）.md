Spring事务机制

Spring事务抽象
```java
PlatformTransactionManager
TransactionDefinition
TransactionStatus
```

Spring事务抽象-事务管理器
```java
public interface PlatformTransactionManager {
    TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException;
    void commit(TransactionStatus status) throws TransactionException;
    void rollback(TransactionStatus status) throws TransactionException;
}
```
TransactionStatus就是运行时事务的事务句柄。

Spring事务抽象-事务定义
```java 
public interface TransactionDefinition {
    /** 事务传播行为 */
    int getPropagationBehavior();
    /** 事务隔离级别 */
    int getIsolationLevel();
    /** 事务名称 */
    String getName();
    /** 事务超时时间 */
    int getTimeout();
    /** 事务是否只读 */
    boolean isReadOnly();
}
```

Spring事务抽象-事务隔离机制
```java
// 使用数据库默认隔离级别
TransactionDefinition.ISOLATION_DEFAULT
// 读已提交
TransactionDefinition.ISOLATION_READ_COMMITTED
// 读未提交
TransactionDefinition.ISOLATION_READ_UNCOMMITTED
// 可重复读
TransactionDefinition.ISOLATION_REPEATABLE_READ
// 串行化
TransactionDefinition.ISOLATION_SERIALIZABLE
```

Spring事务抽象-事务创博机制
```java
// 支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择
TransactionDefinition.PROPAGATION_REQUIRED(Default)
// 支持当前事务，如果当前没有事务，就以非事务方式执行。
TransactionDefinition.PROPAGETION_SUPPORTS
// 支持当前事务，如果当前没有事务，就抛出异常。 
TransactionDefinition.PROPAGETION_MANDATORY
// 新建事务，如果当前存在事务，把当前事务挂起。 
TransactionDefinition.PROPAGETION_REQUIRES_NEW
// 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
TransactionDefinition.PROPAGATION_NOT_SUPPORTED
// 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。 
TransactionDefinition.PROPAGETION_NEVER
// 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则进行与PROPAGATION_REQUIRED类似的操作。
TransactionDefinition.PROPAGATION_NESTED
```

Spring事务抽象-事务管理器
```java
public interface TransactionStatus extends SavepointManager {
    boolean isNewTransaction();
    boolean hasSavepoint();
    void setRollbackOnly();
    boolean isRollbackOnly();
    boolean isCompleted();
}
```

Transactional标签方式实现  申明式事务
```java 
public OrderService {
    @Transactional
    public void buyTicket(BuyTicketDTO dto) {
        // save order
        // finish customer pay
        // ....
    }
}
```
实际上根据AOP的代理后class为
```java
public OrderServiceProxy {
    public void buyTicket(BuyTicketDTO dto) {
        // get and begin transaction man ager from context
        try {
            // do something

            // commit transaction
        } catch(Exception e) {
            // rollback transaction
        }
    }
}
```

编程式事务
```java
@Service
public OrderService {
    
    @Autowire
    private PlatformTransactionManager txManager;

    public void buyTicket(BuyTicketDTO dot) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            // do something
            txManager.commit(status);
        } catch (Exception e) {
            txManager.rollback(status);
        }
    }
}
```

PlatformTransactionManager的常见实现
```java
DataSourceTransactionManager
JpaTransactionManager
JmsTransactionManager
JtaTransactionManager
```

### Spring JMS事务支持
JmsConfiguration.java
```java 
@EnableJms
@Configuration
public class JmsConfiguration {
    @Bean
    public PlatformTransactionManager transactionManager(ConnectionFactory cf) {
        return new JmsTransactionManager(cf);
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory cf) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(cf);
        return jmsTemplate;
    }

    @Bean
    public JmsListenerContainerFacotry<?> msgFactory(ConnectionFactory cf,
        PlatformTransactionManager transactionManager,
        DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, cf);
        factory.setTransactionManager(transactionManager);
        return factory;
    }
}
```

```java
@Bean
@Slf4j
public class CustomerService {

    @Autowire
    private JmsTeplate jmsTemplate;

    @Transactional
    @JmsListener(destination="customer:msg:new", containerFacotry="msgFactory")
    public void handler(String msg) {
        log.info("Get msg: {}", msg);
        String reply = 'Reply-' + msg;
        jmsTemplate.convertAndSend("customer:msg:reply", reply);
        if(msg.contains("error")) {
            // throw some runtime exception
        }
    }
}
```

> 如果通过JmsListener监听消息，那么监听消息+往customer:msg:reply发送消息，将处于同一个回话中。

> 加上Transactional，是为了保证在public void handler(String msg)被直接调用时也处于事务中。

### 利用JTA实现DB、MQ两个数据源事务同步
> JTA是java对XA规范的实现

Spring事务机制对JTA的整合
```java
TransactionManager
XAResource
XID
```
3个类

#### JTA实现单数据库事务管理
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jta-atomikos</artifactId>
        </dependency>
```

```java
@Service
@Slf4j
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Customer create(Customer customer) {
        log.info("CustomerService In Annotation create customer:{}", customer.getUsername());
        if (customer.getId() != null) {
            throw new RuntimeException("用户已经存在");
        }
        customer.setUsername("Annotation:" + customer.getUsername());
        return customerRepository.save(customer);
    }
}
```

> 说明： 引入了spring-boot-starter-jta-atomikos后JTA就自动注册成了事务管理器

#### JTA实现DB、MQ的事务管理
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-activemq</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jta-atomikos</artifactId>
        </dependency>
```

```java
@Service
@Slf4j
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional
    public Customer create(Customer customer) {
        LOG.info("CustomerService In Annotation create customer:{}", customer.getUsername());
        if (customer.getId() != null) {
            throw new RuntimeException("用户已经存在");
        }
        customer.setUsername("Annotation:" + customer.getUsername());
        jmsTemplate.convertAndSend("customer:msg:reply", customer.getUsername() + " created.");
        return customerRepository.save(customer);
    }
}
```
Springboot自动装配好JTA，MQ、DB的数据源

#### 使用Spring链式事务：DatasourceTransactionManager实现 MySQL + MySQL的事务管理
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jdbc</artifactId>
        </dependency>
        <!-- 链式事务管理器 -->
        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-commons</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- 据说性能很高的数据源 -->
        <dependency>
            <groupId>com. zaxxer</groupId>
            <artifactId>HikariCP</artifactId>
            <version>2.7.8</version> 
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
```

```java
@Configuration
public class DatabaseConfiguration {
    @Bean
    @Primary
    public DataSource userDataSource() {
        // 伪代码
        return new HikariDataSource();
    }

    @Bean
    @Private
    public JdbcTemplate userJdbcTemplate() {
        return new JdbcTemplate(userDataSource());
    }

    @Bean
    public DataSource orderDataSource() {
        // 伪代码
        return new HikariDataSource();
    }

    @Bean
    public JdbcTemplate orderJdbcTemplate() {
        return new JdbcTemplate(orderDataSource);
    }

    /** 通过创建链式事务管理器，去同时让两个数据源同在一个事务中 */
    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager userTm = new DataSourceTransactionManager(userDataSource());
        DataSourceTransactionManager orderTm = new DataSourceTransactionManager(orderDataSource());
        ChainedTransaqctionManager tm = new ChainedTransaqctionManager(userTm, orderTm);
        return tm;
    }
}
```

```java
@Service
public class CustomerService {
    @Autowired
    @Qualifier("userJdbcTemplate")
    private JdbcTemplate userJdbcTemplate;

    @Autowired
    @Qualifier("orderJdbcTemplate")
    private JdbcTemplate orderJdbcTemplate;

    /** 扣减余额SQL */
    private static final String SQL_UPDATE_DEPOSIT = "";
    /** 创建订单SQL */
    private static final String SQL_CREATE_ORDER = "";

    @Transactional
    public CustomerOrder createCustomerOrder(CustomerOrder order) {
        userJdbcTemplate.update(SQL_UPDATE_DEPOSIT, order.getAmount(), order.getCustomerId);
        orderJdbcTemplate.update(SQL_CREATE_ORDER, order.getCustomerId, order.getAmount());
    }
}
```
> 说明： 如果在第一个数据库提交成功，第二个数据提交前发生数据库连接异常，那么一样无法保证数据的一致性。


#### 使用Spring链式事务：JpaTransactionManager实现 MySQL + MySQL的事务管理
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <!-- 这里改成JPA -->
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <!-- 链式事务管理器 -->
        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-commons</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- 据说性能很高的数据源 -->
        <dependency>
            <groupId>com. zaxxer</groupId>
            <artifactId>HikariCP</artifactId>
            <version>2.7.8</version> 
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
```

```java
@Configuration
public class DatabaseConfiguration {
    @Bean
    @Primary
    public DataSource userDataSource() {
        // 伪代码
        return new HikariDataSource();
    }

    // 移除JdbcTemplate的配置
    // @Bean
    // @Private
    // public JdbcTemplate userJdbcTemplate() {
    //     return new JdbcTemplate(userDataSource());
    // }
    @Bean
    public LocalContainerEntityManagerFacotryBean entityMangerFacotry() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        LocalContainerEntityManagerFacotryBean factory = new LocalContainerEntityManagerFacotryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setDataSource(userDataSource());
        factory.setPackagesToScan("com.........");
        return factory;
    }

    /** 通过创建链式事务管理器，去同时让两个数据源同在一个事务中 */
    @Bean
    public PlatformTransactionManager transactionManager() {
        // 这里相较于上一个实例两个事务管理器都使用DataSourceTransactionManager来说，
        // 仅仅只是将一个事务管理器替换成了JpaTransactionManager而已
        JpaTransactionManager userTm = new JpaTransactionManager();
        userTm.setEntityManagerFacotry(entityMangerFacotry().getObject());
        DataSourceTransactionManager orderTm = new DataSourceTransactionManager(orderDataSource());
        ChainedTransaqctionManager tm = new ChainedTransaqctionManager(userTm, orderTm);
        return tm;
    }

    @Bean
    public DataSource orderDataSource() {
        // 伪代码
        return new HikariDataSource();
    }

    @Bean
    public JdbcTemplate orderJdbcTemplate() {
        return new JdbcTemplate(orderDataSource);
    }
}
```

#### Spring实现ActiveMQ + MySQL两个数据，以最大努力一次提交： TransactionAwareConnectionFacotryProxy
#### 使用Spring链式事务：JpaTransactionManager实现 MySQL + MySQL的事务管理
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <!-- 这里改成JPA -->
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <!-- 链式事务管理器 -->
        <!--
        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-commons</artifactId>
        </dependency>
        -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-activemq</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
```

```java
@Configuration
public class DatabaseConfiguration {
    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
        TransactionAwareConnectionFacotryProxy proxy = new TransactionAwareConnectionFacotryProxy();
        proxy.setTargetConnectionFacotry(cf);
        proxy.setSynchedLocalTransactionAllowed(true);
        return proxy;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory cf) {
        JmsTemplate jmsTemplate = new JmsTemplate(cf);
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }
}
```

```java
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = "customer:msg:new")
    public void handler(String msg) {
        Customer customer = new Customer();
        // ... 值注入
        customerRepository.save(customer);

        jmsTemplate.convertAndSend("customer:msg:reply", msg);
    }
}
```

#### 分布式锁锁实现
http://www.hollischuang.com/archives/1716
分布式锁需要解决的问题：
1、锁的排他性
2、锁的可重入性
3、服务高可用性
4、锁无法正常释放问题
5、锁超时问题，自动超时的锁又存在锁续租问题
Netflix Curator


#### 幂等性
◆幂等操作:任意多次执行所产生的影响,与一次执行的影响相同
◆方法的幂等性: 使用同样的参数调用一个方法多次,与调用一次结果相同
◆接口的幂等性: 接口被重复调用,结果一致
◆数据库操作的幂等性：同一条操作重复执行后，对数据的影响是一致的。

#### 为什么在微服务的架构中必须考虑幂等性
◆重要性: 经常需要通过重试实现分布式事务的最终一致性
◆GET方法不会对系统产生副作用、具有幂等性
◆POST. PUT、DELETE方法的实现需要满足幂等性

##### Service方法实现幂等性
```java
public OrderService {
    public void ticketOrder(BuyTicketDTO dto) {
        // 可以获取全局GUID
        String guid = getGuid();
        // 判断本服务内是否已经处理过这个操作。
        if(!isDone()) {
            Order order = createOrder(dto);
        }
        // 调用其他服务
        userService.charge(dto);
    }

    private boolean isDone(String guid) {
        // 通过一定的分布式存储实现对guid是否已经处理进行判断。
    }
}
```

##### 基于SQL实现幂等性
```sql
UPDATE
    customer 
SET
    deposit = deposit - `value`, 
    paystatus = `PAID`
WHERE
    orderId = `orderId`
AND
    paystatus = `UNPAID`
```
> 说明： 通过paystatus实现更新操作的幂等性


#### 分布式事务，基于消息驱动
微服务架构的事务问题解决
◆方法1:减少服务间调用
◆方法2:没有服务间调用,通过消息驱动调用服务

注意的问题
◆消息中间件需要支持事务
◆如何处理重试的消息
◆发生业务异常时回滚操作

系统错误的处理
◆方法1: 将出错未处理的消息写到的失败队列,进行相应回滚操作
◆方法2: 通过定时任务检查超时订单,对未完成的订单做自动回滚
◆方法3: 保存出错消息,人工处理

问题解决方案：
◆ActiveMQ作为消息中间件【ActiveMQ支持事务】
◆错误处理:定时任务检查超时并回滚【发生未知异常时系统补漏】
◆幂等性:实现方法的幂等性【解决消息重试，请求重试】

线程安全问题解决方案：
◆利用@JmsListener设置一个消费者,不适用于多实例
◆使用事务和数据库锁的特性
◆分布式锁

#### 分布式事务，基于事件溯源实现实现
优点
◆历史重现:从事件中重新生成视图数据库
◆方便的数据流处理 与报告生成
◆性能
◆服务的松耦合
缺点
◆只能保证事务的最终-致性
◆设计和开发思维的转变、学习成本
◆事件结构的改变
◆扩展性: Event Store的分布式实现,事件的分布式处理

数据一致性
◆一个事件只处理一个服务的数据
◆保证事件的至少一次处理、幂等性
◆业务请求的错误处理:多次重试失败、网络异常、服务不可用

##### Axon框架实现事件溯源方式的分布式事务
Axon框架介绍
◆实现Event Sourcing和CQRS模式的框架
◆实现了命令、事件的分发、处理、聚合、查询、存储
◆提供标签式开发,易维护,并提供了Spring Boot的集成
◆提供Command和Event
◆可扩展，可用于分布式环境,如Spring Cloud

Axon框架的构成
◆聚合: Aggregate
◆聚合的资源库: Repository
◆Command: Command Bus和Command Handler
◆Event: Event Bus、Event Handler和Event Store
◆Saga :基于事件的流程管理模式
◆Query:执行数据查询操作的特殊Command

Axon框架的构成可扩展性
◆分布式Command分发
◆通过AMQP实现分布式Event分发和处理

Axon处理Event过程
◆CommandHandler执行apply来触发一个event
◆EventBus在这个event上执行拦截器等
◆EventBus将这个event保存到EventStore
◆EventBus调用在这个event上注册的所有处理方法(在UnitOfWork中执行)
◆在EventHandler中更新聚合数据、保存视图数据库、触发其他Command


Axon Command和Event的区别
◆Command
●表示某种业务动作
●只被处理一次
●可以有返回值
●只做条件检查,触发相应Event去更新数据

◆Event
●表示系统内发生的事件,某种业务状态的更新
●可以被多次处理
●没有返回值
●更新聚合数据并保存在Event Store中,用于重新生成聚合数据

使用Axon框架的设计过程
◆领域模型设计
◆业务- Command - Command处理
◆数据- Event - Event处理
◆将数据保存到数据库:聚合数据-映射到-视图数据
◆查询- Query

使用Axon框架的设计过程
◆领域模型 :账户Account
◆业务Command :创建账户、存款、取款
◆事件Event :账户创建、存款、取款
◆将账户信息保存到数据库中,方便查询
◆查询Command :查询账户

Axon Saga实现
◆StartSaga - SagaEventHandler - EndSaga
◆使用associate将不同的事件关联到同一个Saga流程中
◆正常的结束业务 都通过EndSaga标签触发,超时使用EventScheduler ,触发一个EndSaga
◆一次业务流程的执行对应一个saga实例
◆Saga实例状态和关联的事件会保存在数据库中


Axon框架事务实现
◆UnitOfWork同步事务
◆聚合对象内处理Command和Event时线性处理
◆聚合对象和Entity共用时,使用for update锁对象
◆Saga处理Event时使用聚合Id和序号避免并发


#### 基于TX-LCN框架实现分布式事务
锁定事务单元（lock）
确认事务模块状态(confirm)
通知事务(notify)

LCN、TCC、TXC
https://txlcn.org/zh-cn/index.html
https://shardingsphere.apache.org/index_zh.html