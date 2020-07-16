package main

import "fmt"

/*
Slice 切片
 */

/*
入参类型为 []int 是Slice切片，通过对切片的修改，可以对原数组进行修改
 */
func rewrite(arr []int)  {
	arr[0] = 100
}

func main()  {
	arr := [...]int {0, 1, 2, 3, 4, 5, 6, 7}

	fmt.Println(arr[2:])
	fmt.Println(arr[:5])
	fmt.Println(arr[2:5])
	fmt.Println(arr[:])

	s := arr[:]
	rewrite(s)
	fmt.Println(s)

	s = s[:5]
	fmt.Println("Reslice", s)

	s = s[:3]
	fmt.Println("Reslice", s)

	/*
	s1的值为[2345] , s2的值为[5 6]
	slice可以向后扩展,不可以向前扩展
	*/
	arr = [...]int {0, 1, 2, 3, 4, 5, 6, 7}
	// s1= [2 3 4 5]
	s1 := arr[2:6]
	// s2= [5 6]
	s2 := s1[3:5]
	fmt.Println("s1=", s1, "  len=", len(s1), "  cap=", cap(s1))
	fmt.Println("s2=", s2, "  len=", len(s2), "  cap=", cap(s2))

	/*
	向Slice添加元素
		添加元素时如果超越cap ,系统会重新分配更大的底层数组
		由于值传递的关系,必须接收append的返回值
		S = append(s, val)
	*/
	s2 = append(s2, 10)
	// append s2= [5 6 10]   len= 3   cap= 3
	fmt.Println("append s2=", s2, "  len=", len(s2), "  cap=", cap(s2))
	s2 = append(s2, 20)
	// append append s2= [5 6 10 20]   len= 4   cap= 6
	fmt.Println("append append s2=", s2, "  len=", len(s2), "  cap=", cap(s2))
	// append append arr= [0 1 2 3 4 5 6 10]   len= 8   cap= 8 s2进行第一次append时，arr的cap还足够，所以将原本的7 替换成了 10；
	// 第二次append是，arr的cap不够，go会重新开辟一个数组对象出来，s2引用新数组的view
	fmt.Println("append append arr=", arr, "  len=", len(arr), "  cap=", cap(arr))


	fmt.Println("通过append动态创建数组")
	// Zero value for slice is nil
	var ss []int
	for i := 0; i < 10; i++ {
		printSlice(ss)
		ss = append(ss, 2 * i + 1)
	}
	printSlice(ss)

	fmt.Println("通过make动态创建数组")
	ss1 := []int{2, 4, 6, 8}
	printSlice(ss1)

	ss2 := make([]int, 16)
	printSlice(ss2)

	ss3 := make([]int, 10, 32)
	printSlice(ss3)

	fmt.Println("复制 Slice")
	copy(ss3, ss1)
	printSlice(ss3)

	fmt.Println("删除 Slice 中间数据")
	// [2 4 6 8 0 0 0 0 0 0] 将ss3中的 8 删掉
	// ss3[4:]... 这里可以获取其中的每一个元素
	ss3 = append(ss3[:3], ss3[4:]...)
	printSlice(ss3)

	fmt.Println("删除 Slice 头")
	head := ss3[0]
	ss3 = ss3[1:]
	fmt.Println("head element: ", head)
	printSlice(ss3)

	fmt.Println("删除 Slice 尾巴")
	tail := ss3[len(ss3) - 1]
	ss3 = ss3[:len(ss3) - 1]
	fmt.Println("tail element: ", tail)
	printSlice(ss3)

}

func printSlice(arr []int)  {
	fmt.Printf("%v, len: %d, cap: %d \n", arr, len(arr), cap(arr))
}
