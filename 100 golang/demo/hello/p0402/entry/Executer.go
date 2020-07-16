package main

import (
	"fmt"
	"hello/p0402"
)

/*
go get 获取第三发库
go get golang.org/x/tools/cmd/goimports

go get 命令
go get github.com/gpmgo/gopm
go get -v github.com/gpmgo/gopm
gopm get -g -v golang.org/x/tools/cmd/goimports
编译
go build golang.org/x/tools/cmd/goimports
编译安装
go install golang.org/x/tools/cmd/goimports
使用gopm获取不能获取的包
一个目录下面只能有一个main函数
 */
func main()  {
	person := p0402.Person{Name: "yong", Age: 18, Address: "四川成都丽景路1080号", Gender: 1};
	fmt.Println(person)
	person.Println(3)

	person.Println(2)
	person.SetAddress("丽景路")
	person.Println(1)
}

