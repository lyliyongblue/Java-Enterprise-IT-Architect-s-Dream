package main

import (
	"fmt"
	"sync"
)

// channel入参 <-chan 表示传入的这个chan只能读数据
func doWork(id int, w worker) {
	// 通过for循环从channel中取值
	for n := range w.in {
		fmt.Printf("Worker %d received %c\n", id, n)
		w.done()
	}
}

type worker struct {
	in chan int
	done func()
}

// 将channel作为返回值，并且指定channel的方向，只能用于发送数据
func createdWorker(id int, wg *sync.WaitGroup) worker{
	w := worker{
		in: make(chan int),
		done: func() {
			wg.Done()
		},
	}
	go doWork(id, w)
	return w
}

func chanDemo()  {
	var workers [10]worker
	// 等待组，类似于Java中的CountDownLatch
	var wg sync.WaitGroup
	for i := 0; i < 10; i++ {
		workers[i] = createdWorker(i, &wg)
	}
	wg.Add(20)
	for i, w := range workers {
		w.in <- 'a' + i
	}
	for i, w := range workers {
		w.in <- 'A' + i
	}
	wg.Wait()
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
