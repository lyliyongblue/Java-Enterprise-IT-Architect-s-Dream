package main

import "fmt"

func passByVal(a int)  {
	a++
}

/*
*int 表示 int指针类型
 */
func passByRef(pa *int)  {
	// *pa 表示从 pa指针中获取应用数据
	*pa++
}

func swap1(a, b int) {
	a, b = b, a
}

func swap2(a, b *int) {
	*a, *b = *b, *a
}

func swap3(a, b int) (int, int) {
	return b, a
}

/**
go只能进行值传递；但是可以指针传递，来实现类似于引用传递
 */
func main()  {
	var a = 3
	passByVal(a)
	fmt.Printf("After pass_by_val: %d \n", a)

	// &a 获取 a 的指针
	passByRef(&a)
	fmt.Printf("After pass_by_ref: %d \n", a)

	// 值传递交换无效
	a, b := 3, 4
	swap1(a, b)
	fmt.Println(a, b)

	// 传递指针，然后指针对应的数据进行交换，有效；但不是最优的实现
	a, b = 3, 4
	swap2(&a, &b)
	fmt.Println(a, b)

	// 无需值传递，通过返回值对a b重新赋值，来实现交换；是最优实现
	a, b = 3, 4
	a, b = swap3(a, b)
	fmt.Println(a, b)
}