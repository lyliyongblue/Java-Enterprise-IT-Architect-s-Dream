package main

import "fmt"

/*
1、
 */
func main()  {
	m := map[string]string {
		"name": "yong li",
		"address": "嘻嘻嘻",
		"firstName": "yong",
		"lastName": "li",
	}

	// m2 == empty map
	m2 := make(map[string]int)

	// m3 == nil
	var m3 map[string]int
	fmt.Println(m, m2, m3)

	for k, v := range m {
		fmt.Println(k, v)
	}

	name, ok := m["name"]
	fmt.Println("name: ", name, ok)

	name, ok = m["name2"]
	fmt.Println("name2: ", name, ok)

	name, ok = m["name"]
	fmt.Println("name: ", name, ok)

	delete(m, "name")
	name, ok = m["name"]
	fmt.Println("name: ", name, ok)
}