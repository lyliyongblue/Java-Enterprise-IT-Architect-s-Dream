package main

import (
	"fmt"
	queue "hello/p0502/queue/impl"
)

func main()  {
	q := queue.Queue{1}
	fmt.Println(q)

	q.Push(2)
	q.Push(3)

	fmt.Println(q.Pop())
	fmt.Println(q.Pop())
	fmt.Println(q.IsEmpty())
	fmt.Println(q.Pop())
	fmt.Println(q.IsEmpty())

	mq := queue.MultQueue{1}
	fmt.Println(mq)

	mq.Push(2)
	mq.Push(3)

	fmt.Println(mq.Pop())
	fmt.Println(mq.Pop())
	fmt.Println(mq.IsEmpty())
	fmt.Println(mq.Pop())
	fmt.Println(mq.IsEmpty())
	mq.Push("abc")
	fmt.Println(mq.Pop())
}
