## Java语言规范

#### 语法规范：

> IfThenStatement if-then的什么格式

```java
if (Expression) Statement 
```

例子： 
```java
if(true) {
    doSomething();
}
```

> ArgumentList 参数列表  

	Argument
	ArgumentList, Argument

例子：
```java
add(a, b, c);
```	

词法结构：
- \u + 4个16进制数字 标识UTF-8
- 行终结符 CR / LF / CR LF
- 空白符 空格 tab \t 换页  \f 行终结符
- 注释
- 标识符
- 关键字

int

>Java中各种进制数据的表示

```java
// 2进制 0b开头
int a = 0b1111;

// 8进制 0开头
int b = 077;

// 16进制 0x开头
int c = 0xffff;



```

> Java的浮点数为什么会发生精度丢失？具体的精度范围是什么？


#### 变量规范


#### 类型规范


#### 文法规范




## JVM规范
Java语言规范定义了什么是Java语言  
JVM主要定义了二级制class文件和JVM指令集  
Java语言和JVM相对独立  

#### Class文件类型，Class文件格式


#### 运行时数据，数字的内部表示和存储
byte -128 to 127 (-2^7 to 2^7 - 1)

整数的表达
- 原码： 第一位符号位（0为正数，1为复数）
- 反码： 符号位不动，原码取反
- 复数补码： 符号位不动，反码加1
- 整数补码： 和原码相同

> 写一个算法，将十进制数字，转换成二进制字符串？

> 在计算机中，为什么需要补码？


#### ReturnAddress数据类型定义
指向操作码的指针，不对应Java数据类型，不能再运行时修改。finally实现需要。


#### 虚拟机内存模型
PC寄存器
堆
栈 帧栈
方法区


#### 虚拟机的启动


#### 虚拟机的指令集
类型转换  
- l2i

出栈入栈操作
- aload
- astore

运算
- iadd 
- isub

流程控制
- ifeq
- ifne

函数调用
- invokevirtual 
- invokeinterface  
- invokespecial  
- invokestatic 

> 使用javap反汇编class，简单理解JVM指令【实践】！！


#### JVM需要对Java Library提供那些支持？
反射 java.lang.reflect

ClassLoader

初始化class和interface

安全相关java.security

多线程

弱引用






