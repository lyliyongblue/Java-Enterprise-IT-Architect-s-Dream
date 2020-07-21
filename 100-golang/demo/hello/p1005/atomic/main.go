package main

import (
	"fmt"
	"sync"
	"time"
)

type atomicInt struct {
	value int
	lock  sync.Mutex
}

func (a *atomicInt) increment()  {
	fmt.Println("safe increment")
	func (){
		a.lock.Lock()
		defer a.lock.Unlock()
		a.value++
	}()
}

func (a *atomicInt) get() int {
	a.lock.Lock()
	defer a.lock.Unlock()
	return a.value
}

/*
go run -race main.go 可以检查其安全性
传统的同步机制：
- WaitGroup
- Mutex
- Cond
一般很少使用，需要使用到的地方尽量选择使用channel进行通信

go 面向服务、面向网络的编程语言
 */
func main() {
	var a atomicInt
	a.increment()
	go func() {
		a.increment()
	}()
	time.Sleep(time.Millisecond)
	fmt.Println(a.get())
}
