package main

import (
	"fmt"
)

// channel入参 <-chan 表示传入的这个chan只能读数据
func doWork(id int, c <-chan int, done chan<- bool) {
	// 通过for循环从channel中取值
	for n := range c {
		fmt.Printf("Worker %d received %c\n", id, n)
		go func() { done <- true }()
	}
}

// 将channel作为返回值，并且指定channel的方向，只能用于发送数据
func createdWorker(id int, done chan<- bool) chan<- int {
	c := make(chan int)
	go doWork(id, c, done)
	return c
}

func chanDemo()  {
	var channels [10]chan<- int
	done := make(chan bool)
	for i := 0; i < 10; i++ {
		channels[i] = createdWorker(i, done)
	}
	for i, channel := range channels {
		channel <- 'a' + i
	}
	//for i := 0; i < 10; i++ {
	//	<-done
	//}
	for i, channel := range channels {
		channel <- 'A' + i
	}
	for i := 0; i < 20; i++ {
		<-done
	}
}

/*
- channel
- buffered channel
- range
- 理论基础 Communication Sequential Process (CSP)

Don't communicate by sharing memory; share memory by communicating
不要通过共享内存来通信； 通过通信来共享内存
 */
func main()  {
	chanDemo()
}
