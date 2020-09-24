package main

import (
	"bufio"
	"fmt"
	"io"
	"strings"
)

// 斐波拉契数列的实现
func Fibonacci() intGen {
	a, b := 0, 1
	return func() int {
		a, b = b, a + b
		return b
	}
}

type intGen func() int

// 为函数类型实现Reader接口
func (g intGen) Read(p []byte) (n int, err error) {
	next := g()
	if next > 10000 {
		return 0, io.EOF
	}
	s := fmt.Sprintf("%d\n", next)
	// TODO 如果p太小的话，会出现问题
	return strings.NewReader(s).Read(p)
}

func printFileContents(reader io.Reader)  {
	scanner := bufio.NewScanner(reader)

	for scanner.Scan() {
		fmt.Println(scanner.Text())
	}
}

func main()  {
	f := Fibonacci()
	//fmt.Println(f())
	//fmt.Println(f())
	//fmt.Println(f())
	//fmt.Println(f())
	//fmt.Println(f())
	//fmt.Println(f())
	//fmt.Println(f())
	//fmt.Println(f())
	//fmt.Println(f())
	printFileContents(f)
}
