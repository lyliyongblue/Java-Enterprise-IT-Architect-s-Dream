package obj

import "fmt"

type Person struct {
	Name   string
	Age    int8
	Height int8
}

func (person *Person)SayHi(msg string)  {
	fmt.Println("name: " + person.Name + " say: " + msg)
}

