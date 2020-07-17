package fib

// 斐波拉契数列的实现
func Fibonacci() func() int {
	a, b := 0, 1
	return func() int {
		a, b = b, a + b
		return b
	}
}


