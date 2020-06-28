package main

import "fmt"

/*
1 struct
Go仅支持封装，不支持继承和多态

 */

type treeNode struct {
	value int32
	left, right *treeNode
}

func (node treeNode) print() {
	fmt.Println(node)
}

// 只有指针才能修改结构内容
// nil对象也可以调用方法
func (node *treeNode) setValue(value int32) {
	node.value = value
}

/*
创建构造工厂
 */
func createNode(value int32) *treeNode  {
	// 不需要知道该对象是分配在堆山的？还是栈上？
	return &treeNode{value: value}
}

func main()  {
	root := treeNode{value: 3}

	root.left = &treeNode{}
	root.right = &treeNode{5, nil, nil}
	root.right.left = new(treeNode)


	nodes := []treeNode {
		{value: 3},
		{},
		{6, nil, &root},
	}

	fmt.Println(nodes)

}
