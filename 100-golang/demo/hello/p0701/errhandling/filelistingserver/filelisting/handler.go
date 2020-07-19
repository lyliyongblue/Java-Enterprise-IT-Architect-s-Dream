package filelisting

import (
	"fmt"
	"io/ioutil"
	"net/http"
	"os"
	"strings"
)

const pref string = "/list/"

// type可以定义个结构，也可以直接定义成一个string
type userError string

// 并且能对定义一个提供接口实现
func (u userError) Error() string {
	return u.Message()
}

func (u userError) Message() string {
	return string(u)
}

func FileList(writer http.ResponseWriter, request *http.Request) error {
	if strings.Index(request.URL.Path, pref) < 0 {
		// fmt.Sprintf 可以构建一个字符串返回
		// return errors.New(fmt.Sprintf("Path must start with %s", pref))
		return userError(fmt.Sprintf("Path must start with %s", pref))
	}
	path := request.URL.Path[len(pref):]
	file, err := os.Open(path)
	if err != nil {
		return err
	}
	defer file.Close()
	all, err := ioutil.ReadAll(file)
	if err != nil {
		return err
	}
	_, err = writer.Write(all)
	if err != nil {
		return err
	}
	return nil
}