package main

import (
	"fmt"
	"math/rand"
	"time"
)

// channel入参 <-chan 表示传入的这个chan只能读数据
func worker(id int, c <-chan int) {
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

func generator() <-chan int {
	channel := make(chan int)
	go func() {
		i := 0
		time.Sleep(time.Duration(rand.Intn(1500)) * time.Millisecond)
		channel <- i
		i++
	}()
	return channel
}

func main() {
	var c1, c2 = generator(), generator()
	worker := createdWorker(0)
	for {
		var activeWorker chan<- int
		var hasValue bool
		if hasValue {
			activeWorker = worker
		}
		var n int
		select {
		case n = <-c1:
			hasValue = true
		case n = <-c2:
			hasValue = true
		case activeWorker <- n:
			hasValue = false
			activeWorker = nil
		}
	}
}
