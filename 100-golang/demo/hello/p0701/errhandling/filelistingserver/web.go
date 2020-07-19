package main

import (
	"log"
	"net/http"
	"os"
	"yong.com/hello/p0701/errhandling/filelistingserver/filelisting"
)

type handlerFunc func(writer http.ResponseWriter, request *http.Request) error

// go语言定义接口，和接口实现，可以相互不可见，只需要各自都有相同的约定格式即可
type userError interface {
	error
	Message() string
}

func errHandler(handler handlerFunc) func(writer http.ResponseWriter, request *http.Request) {
	return func(writer http.ResponseWriter, request *http.Request) {
		defer func() {
			r := recover()
			if r == nil {
				return
			}
			log.Printf("err handler, recover error: %v\n", r)
			code := http.StatusInternalServerError
			http.Error(writer, http.StatusText(code), code)
		}()

		err := handler(writer, request)
		if err == nil {
			return
		}
		log.Printf("Error: %s\n", err.Error())

		if userErr, ok := err.(userError); ok {
			http.Error(writer, userErr.Message(), http.StatusBadRequest)
			return
		}
		code := http.StatusOK
		switch {
		case os.IsNotExist(err):
			code = http.StatusNotFound
		case os.IsPermission(err):
			code = http.StatusForbidden
		default:
			code = http.StatusInternalServerError
		}
		http.Error(writer, http.StatusText(code), code)
	}
}

/*
通过go提供的http组建，直接就可以构建一个http服务
发生panic，http服务器不会挂掉
 */
func main()  {
	http.HandleFunc("/", errHandler(filelisting.FileList))
	err := http.ListenAndServe(":8888", nil)
	if err != nil {
		panic(err)
	}
}
