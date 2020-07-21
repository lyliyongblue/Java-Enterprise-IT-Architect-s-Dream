package main

import (
	"fmt"
	"math/rand"
	"time"
)

// channel入参 <-chan 表示传入的这个chan只能读数据
func worker(id int, c <-chan int) {
	for n := range c {
		time.Sleep(time.Second)
		fmt.Printf("Worker %d received %d\n", id, n)
	}
}

// 将channel作为返回值，并且指定channel的方向，只能用于发送数据
func createdWorker(id int) chan<- int {
	c := make(chan int)
	go worker(id, c)
	return c
}

func generator(init int) <-chan int {
	channel := make(chan int)
	go func() {
		i := 0 + init
		for {
			time.Sleep(time.Duration(rand.Intn(1500)) * time.Millisecond)
			channel <- i
			i++
		}
	}()
	return channel
}

/*
使用select来进行调度（通过channel通信CSP来实现内存数据共享）
- select的使用
- 定时器的使用
- 在select中使用nil channel禁止向channel中送数据
 */
func main() {
	var c1, c2 = generator(0), generator(100000)
	worker := createdWorker(0)
	// 定时器，time.After也通过channel来进行通知
	timeWait := time.After(time.Second * 10)
	// 循环定时提醒
	tick := time.Tick(time.Second * 3)

	var queue []int
	n := 0
	for {
		var activeWorker chan<- int
		var activeValue int
		if len(queue) > 0 {
			activeWorker = worker
			activeValue = queue[0]
		}
		select {
		case n = <-c1:
			queue = append(queue, n)
		case n = <-c2:
			queue = append(queue, n)
		case activeWorker <- activeValue:
			queue = queue[1:]
		case <-tick:
			fmt.Printf("queue length: %d\n", len(queue))
		case <-time.After(time.Millisecond * 800):
			// 从c1, c2中如果800毫秒都没获取到数据，那么该定时器的channel将发送数据，即表示800毫秒到了，从而实现超时判断
			fmt.Println("timeout ...")
		case <-timeWait:
			fmt.Println("bye")
			return
		}
	}
}