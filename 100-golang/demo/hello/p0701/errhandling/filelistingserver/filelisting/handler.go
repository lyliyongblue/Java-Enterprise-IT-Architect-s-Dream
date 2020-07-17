package filelisting

import (
	"io/ioutil"
	"net/http"
	"os"
)

func FileList(writer http.ResponseWriter, request *http.Request) {
	path := request.URL.Path[len("/list/"):]
	file, err := os.Open(path)
	if err != nil {
		panic(err)
	}
	defer file.Close()
	all, err := ioutil.ReadAll(file)
	if err != nil {
		panic(err)
	}
	_, err = writer.Write(all)
	if err != nil {
		panic(err)
	}
}