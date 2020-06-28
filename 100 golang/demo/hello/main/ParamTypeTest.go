package main

import "fmt"

var x, y int

// 这种因式分解关键字的写法一般用于声明全局变量
var (
	a int
	b bool
)
var c, d = 1, 2
var e, f = 123, "hello"

/*
参数类型定义
 */
func main()  {

	var age uint8 = 60
	fmt.Println("Age: ", age)

	var salary float32 = 3000.00
	fmt.Println("Salary: ", salary)

	var distance = 9562236666662.02
	fmt.Println("distance: ", distance)

	var firstName = "yong"
	fmt.Println("First Name: " + firstName)

	total := 10
	fmt.Printf("total: %v \n", total)

	fmt.Println(a, b, c, d, e, f , x, y)
}
