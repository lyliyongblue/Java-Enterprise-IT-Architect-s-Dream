package main

import (
	"fmt"
	"reflect"
	"runtime"
)

func eval(a, b int, op string) (int, error) {
	switch op {
	case "+":
		return a + b, nil
	case "-":
		return a - b, nil
	case "*":
		return a * b, nil
	case "/":
		return a / b, nil
	default:
		return 0, fmt.Errorf("unsupported operation: %s", op)
	}
}

// 返回两个结果，返回两个数值的商、余数
func div(a, b int) (q, r int) {
	return a / b, a % b
}

func apply(op func(x, y int) int, a, b int) int {
	pointer := reflect.ValueOf(op).Pointer()
	opName := runtime.FuncForPC(pointer).Name()
	fmt.Printf("Calling function %s with args (%d, %d)", opName, a, b)
	return op(a, b)
}

func main() {
	if result, error := eval(10, 1, "$"); error != nil {
		fmt.Println("Error: ", error)
	} else {
		fmt.Println(result)
	}

	fmt.Println(div(10, 3))

	fmt.Println(apply(func(x, y int) int {
		return x - y
	}, 10, 3))

}
