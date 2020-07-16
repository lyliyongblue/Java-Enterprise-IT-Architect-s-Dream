package queue

/*
interface{} 代表任意类型，类似Java中的Object
 */
type MultQueue []interface{}

func (q *MultQueue) Push(v interface{}) {
	*q = append(*q, v)
}

func (q *MultQueue) Pop() int  {
	head := (*q)[0]
	*q = (*q)[1:]
	// 通过这个方式可以强转成int
	return head.(int)
}

func (q *MultQueue) IsEmpty() bool  {
	return len(*q) == 0
}

