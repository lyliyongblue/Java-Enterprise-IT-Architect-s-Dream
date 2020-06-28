package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func convertToBin(n int) string {
	result := ""
	if n == 0 {
		return "0"
	}
	if n < 0 {
		panic(fmt.Sprintf("Wrong num: %d", n))
	}
	for ; n > 0; n /= 2 {
		lsb := n % 2
		result = strconv.Itoa(lsb) + result
	}
	return result
}

func printFile(filename string)  {
	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		fmt.Println(scanner.Text())
	}
}

func forever()  {
	for {
		fmt.Println("abc")
	}
}

func main() {
	fmt.Println(convertToBin(10), convertToBin(54), convertToBin(8955621), convertToBin(0))
	printFile("main/abc.txt")
	//forever()
}
