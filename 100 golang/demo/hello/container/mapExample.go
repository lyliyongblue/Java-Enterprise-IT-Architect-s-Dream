package main

import (
	"fmt"
	"strings"
	"unicode/utf8"
)

/*
2、寻找最长不包含重复字符的字串最大长度
如： aaabccccce  -> abc  3
 */
func main()  {
	var content = "aaabccccce"
	var count = execute(content)
	fmt.Printf("content: %s, count: %d", content, count)
}

func execute(context string) int {
	datas := strings.Split(context, "")
	maps := make(map[string]int)
	fmt.Println(maps, datas)
	fmt.Println(utf8.RuneCountInString("aa啊啊"))
	return 0
}