package main

import (
	"fmt"
	"time"
)

func worker(c chan int) {
	for {
		// 从channel中读取数据赋值到n
		n := <- c
		fmt.Println(n)
	}
}

func chanDemo()  {
	c := make(chan int)
	// 将channel传递给函数执行
	go worker(c)
	// 往channel中写入1
	c <- 1
	// 往channel中写入2
	c <- 2
	// 将主协程sleep 1 毫秒，让其他goroutine执行完成候退出
	time.Sleep(time.Millisecond)
}

func main()  {
	chanDemo()
}
