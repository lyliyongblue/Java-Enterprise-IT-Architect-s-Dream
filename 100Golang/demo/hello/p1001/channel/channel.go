package main

import (
	"fmt"
	"time"
)

// channel入参 <-chan 表示传入的这个chan只能读数据
func worker(id int, c <-chan int)  {
	/*
	for {
		// 通过两个参数获取，从channel中取值的结果
		if n, ok := <-c; ok {
			// 从channel中读取数据
			fmt.Printf("Worker %d received %c\n", id, n)
		}
	}
	*/
	// 通过for循环从channel中取值
	for n := range c {
		fmt.Printf("Worker %d received %c\n", id, n)
	}

}

// 将channel作为返回值，并且指定channel的方向，只能用于发送数据
func createdWorker(id int) chan<- int {
	c := make(chan int)
	go worker(id, c)
	return c
}

func chanDemo()  {
	var channels [10]chan<- int
	for i := 0; i < 10; i++ {
		channels[i] = createdWorker(i)
	}
	for i := 0; i < 10; i++ {
		channels[i] <- 'a' + i
	}
	for i := 0; i < 10; i++ {
		channels[i] <- 'A' + i
	}
	// 将主协程sleep 1 毫秒，让其他goroutine执行完成候退出
	time.Sleep(time.Millisecond)
}

func bufferChannel() {
	chanel := make(chan int, 3)
	go worker(0, chanel)
	chanel <- 'A'
	chanel <- 'B'
	chanel <- 'C'
	time.Sleep(time.Millisecond)
}

func closeChannel()  {
	channel := make(chan int)
	go worker(66, channel)
	channel <- 'A'
	channel <- 'B'
	channel <- 'C'
	close(channel)
	time.Sleep(time.Microsecond)
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
	fmt.Println("Channel as first-class citizen")
	chanDemo()
	fmt.Println("Buffered channel")
	bufferChannel()
	fmt.Println("Channel close and range")
	closeChannel()
}
