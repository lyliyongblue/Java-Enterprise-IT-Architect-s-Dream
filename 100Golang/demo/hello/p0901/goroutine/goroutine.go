package main

import (
	"fmt"
	"time"
)

// 协程Coroutine
// 轻量级“线程”
// 非抢占式多任务处理，由协程主动交出控制权
// 编译器/解释器/虚拟机层面的多任务
// 多个协程可能在一个或者多个线程上运行

// 子程序是协程的一个特例

// goroutine的定义

func main() {
	var a [10]int
	for i:=0; i<10; i++ {
		go func(i int) {
			for {
				//a[i]++
				fmt.Printf("hello from goroutine %d\n", i)
				// 让出控制权
				//runtime.Gosched()
			}
		}(i)
	}
	time.Sleep(time.Minute)
	fmt.Println(a)
}
