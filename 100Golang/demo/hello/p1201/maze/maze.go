package main

import (
	"fmt"
	"os"
)

func readMaze(filename string) [][] int  {
	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}
	var row, col int
	_, _ = fmt.Fscanf(file, "%d %d", &row, &col)

	maze := make([][]int, row)
	for i := range maze {
		maze[i] = make([]int, col)
		for j := range maze[i]{
			_, _ = fmt.Fscanf(file, "%d", &maze[i][j])
		}
	}
	return maze
}

type point struct {
	i, j int
}

var dirs = [4]point {{-1, 0}, {0, -1}, {1, 0}, {0, 1}}

func (p point)add(r point) point  {
	return point{
		p.i + r.i,
		p.j + r.j,
	}
}

// 获取当前点所在的坐标系的值，判断point是否还出于坐标系范围内
func (p point)at(grid [][]int) (int, bool)  {
	if p.i < 0 || p.i >= len(grid) {
		return 0, false
	}
	if p.j < 0 || p.j >= len(grid[0]) {
		return 0, false
	}
	return grid[p.i][p.j], true
}

func walk(maze [][]int, start, end point) [][]int {
	steps := make([][]int, len(maze))
	for i := range steps {
		steps[i] = make([]int, len(maze[i]))
	}
	queue := []point{start}

	for len(queue) > 0 {
		current := queue[0]
		queue = queue[1:]
		if current == end {
			break
		}
		for _, dir := range dirs{
			next := current.add(dir)
			// maze的坐标next不是墙，即值为0
			// grid的坐标next未走过，即值为0
			// next 不是 start
			value, ok := next.at(maze)
			if !ok || value != 0 {
				continue
			}
			value, ok = next.at(steps)
			if !ok || value != 0 {
				continue
			}
			if next == start {
				continue
			}
			currentStep, _ := current.at(steps)
			steps[next.i][next.j] = currentStep + 1
			queue = append(queue, next)
		}
	}
	return steps
}

// 传说中的迷宫算法
/*
广度优先搜索走迷宫
- 用循环创建二位slice
- 使用slice来实现队列
- 用Fscanf读取文件（给C++较像）
- 对Point的抽象
 */
func main() {
	maze := readMaze("p1201/maze/maze.in")
	for i := range maze {
		for j := range maze[i] {
			fmt.Printf("%d ", maze[i][j])
		}
		fmt.Println()
	}
	fmt.Println()
	steps := walk(maze, point{0, 0}, point{len(maze) - 1, len(maze[0]) - 1})
	for i := range steps {
		for j := range steps[i] {
			fmt.Printf("%3d", steps[i][j])
		}
		fmt.Println()
	}
}