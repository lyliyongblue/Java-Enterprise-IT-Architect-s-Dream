package obj

import "fmt"

type Man struct {
	person *Person
	drink bool
}

func (man *Man)toDrink(mount int8) {
	man.person.SayHi("Man")
	fmt.Println("drink: ", mount)
}
