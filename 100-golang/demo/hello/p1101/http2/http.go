package main

import (
	"fmt"
	"net/http"
	"net/http/httputil"
)

/*
http

- 使用http客户端发送请求
- 使用http.Client控制请求头部等
- 使用httputil简化工作
- 使用httputil简化工作

http服务性能测试
- import _ ""
 */
func main() {
	req, err := http.NewRequest(http.MethodGet, "http://www.baidu.com", nil)
	if err != nil {
		panic(err)
	}
	req.Header.Add("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1")
	client := http.Client{
		CheckRedirect: func(req *http.Request, via []*http.Request) error {
			fmt.Println("Redirect: ", req)
			return nil
		},
	}
	resp, err := client.Do(req)
	if err != nil {
		panic(err)
	}
	defer resp.Body.Close()
	body, err := httputil.DumpResponse(resp, false)
	if err != nil {
		panic(err)
	}
	fmt.Printf("%s\n", body)
}
