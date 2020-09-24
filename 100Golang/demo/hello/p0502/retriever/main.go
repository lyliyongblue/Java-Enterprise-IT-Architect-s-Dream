package main

import (
	"fmt"
	"hello/p0502/retriever/mock"
	real2 "hello/p0502/retriever/real"
	"time"
)

/*
实现接口是隐式的，只需要实现接口的方法即可
接口由使用者定义
 */
type Retriever interface {
	Get(url string) string
}

type Poster interface {
	Post(url string, form map[string]string) string
}

const url = "http://www.imooc.com"

func download(r Retriever) string {
	return r.Get(url)
}

func post(poster Poster) string  {
	return poster.Post(url, map[string]string{
		"name": "",
		"course": "",
	})
}

/*
接口组合
 */
type RetrieverPoster interface {
	Retriever
	Poster
}

func session(s RetrieverPoster) string  {
	s.Post(url, map[string]string {
		"contents": "another faked imooc.com",
	})
	return s.Get(url)
}

func main()  {
	/*
	接口变量中的内容：
	- 实现者类型
	- 实现者的指针（实现者的值）  --> 实现者

	接口变量里面有什么？
	- 接口变量自带指针
	- 接口变量同样采用值传递，几乎不需要使用接口的指针
	- 指针接收者实现只能以指针方式使用；值接收者都可以
	 */
	var r Retriever

	// mock的Retriever的实现为值接受者，那么该实现可以任意加不加&都可以
	retrieverAndPoster := &mock.Retriever{Context: "this is a fake imooc.com"}
	r = retrieverAndPoster
	fmt.Println(download(r))

	inspect(r)

	// &具体意义是什么  &表示是地址，因为该接口实现的接受者为‘指针接收者’
	r = &real2.Retriever{
		UserAgent: "Mozilla/5.0",
		TimeOut: time.Minute,
	}
	inspect(r)
	//fmt.Println(download(r))

	// Type assertion 类型断言
	if retriever, ok := r.(*mock.Retriever); ok {
		fmt.Println(retriever.Context)
	} else {
		fmt.Println("not a mock Retriever")
	}

	fmt.Println("Try a session")
	fmt.Println(session(retrieverAndPoster))
}

func inspect(r Retriever) {
	// %T 打印类型  %v值
	fmt.Printf("%T %v\n", r, r)
	switch v := r.(type) {
	case *mock.Retriever:
		fmt.Println("Contents: ", v.Context)
	case *real2.Retriever:
		fmt.Println("UserAgent: ", v.UserAgent)
	}
}
