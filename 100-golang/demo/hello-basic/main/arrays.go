package main

import "fmt"

func printArray(arr [5]int)  {
	for i, v := range arr {
		fmt.Println(i, " ", v)
	}
}

func main()  {
	var arr1 [5]int
	arr2 := [3]int{1, 3, 5}
	arr3 := [...]int{2, 4, 6, 8, 10}
	var grid [2][3]bool
	fmt.Println(arr1, arr2, arr3)
	fmt.Println(grid)

	// 数组的遍历
	for i := 0; i < len(arr3); i++ {
		fmt.Print(arr3[i], " ")
	}
	fmt.Println()

	// 使用range关键字对数组进行遍历
	for i := range arr3 {
		fmt.Print(arr3[i], " ")
	}
	fmt.Println()

	// 使用range关键字遍历下标和值
	for i, v := range arr3 {
		fmt.Println(i, " ", v)
	}

	// 使用range关键字遍历值，不要下标，使用 _ 来忽略i
	for _, v := range arr3 {
		fmt.Print(v, " ")
	}

	/*
	为什么要用range
		- 意义明确，美观
		- C++ :没有类似能力
		- Java/Python :只能for each value ,不能同时获取i, v
	*/


	/*
	数组是值类型
		- [10]int 和[20]int是不同类型
		- 调用func f(arr [10]int)会拷贝数组
		- 在go语言中一般不直接使用数组
	*/
	// 参数传递时，arr1/arr3会进行值拷贝
	printArray(arr1)
	printArray(arr3)
}
