package main

import (
	"bufio"
	"fmt"
	"os"
)

func tryDefer()  {
	defer fmt.Println(1)
	defer fmt.Println(2)
	fmt.Println(3)
	// 即使panic抛出异常， def都会执行
	// panic("error occurred")
	fmt.Println(4)
}

// 斐波拉契数列的实现
func fibonacci() func() int {
	a, b := 0, 1
	return func() int {
		a, b = b, a + b
		return b
	}
}

func writeFile(filename string) {
	// 创建文件
	// file, err := os.Create(filename)
	file, err := os.OpenFile(filename, os.O_EXCL|os.O_CREATE, 0666)
	// 自己创建err实例
	// err = errors.New("this is a custom error")
	if err != nil {
		// 通过err.(xxxx)进行类型强转
		if pathError, ok := err.(*os.PathError); !ok {
			panic(err)
		} else {
			fmt.Printf("%s, %s, %s\n", pathError.Op, pathError.Path, pathError.Err)
		}
		return
	}
	defer file.Close()

	writer := bufio.NewWriter(file)
	defer writer.Flush()

	f := fibonacci()
	for i := 0; i < 20; i++ {
		_, _ = fmt.Fprintln(writer, f())
	}
}

func tryDefer2()  {
	for i := 0; i<100; i++ {
		defer fmt.Println(i)
		if i == 20 {
			panic("printed too many")
		}
	}
}

/*
defer调用
- 确保调用在函数结束时发生
- 参数在defer语句时计算
- defer语句为先进后出

- Open/Close
- Lock/Unlock
- PrintHeader/PrintFooter
 */
func main()  {
	tryDefer()
	// 写文件，并且通过defer关闭文件，Flush流
	writeFile("flb.txt")
	//tryDefer2()
}
