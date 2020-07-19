package main

import (
	"fmt"
	"log"
)

/*
recover() 配合 defer 使用，统一对函数异常处理
 */
func tryRecover()  {
	// 可以类似于Java中一个方法来一个try catch finally但是比Java代码优雅的太多
	defer func() {
		r := recover()
		if err, ok := r.(error); ok {
			log.Printf("Error Occurred: %s\n", err.Error())
		} else {
			panic(r)
		}
	}()
	//panic(errors.New("this is a Error"))
	b := 0
	a := 5/b
	fmt.Println(a)
}

func main() {
	tryRecover()
}
