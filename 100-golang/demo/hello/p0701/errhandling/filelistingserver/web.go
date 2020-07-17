package main

import (
	"hello/p0701/errhandling/filelistingserver/filelisting"
	"net/http"
)

/*
通过go提供的http组建，直接就可以构建一个http服务
发生panic，http服务器不会挂掉
 */
func main()  {
	http.HandleFunc("/list/", filelisting.FileList)
	err := http.ListenAndServe(":8888", nil)
	if err != nil {
		panic(err)
	}
}
