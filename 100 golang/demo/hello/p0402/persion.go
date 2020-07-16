package p0402

import "fmt"

type Person struct {
	Name string
	Age int8
	Address string
	Gender int8
	Mother, Father *Person
}

func (person *Person) Println(repeat int) {
	for i := 1; i <= repeat; i++ {
		fmt.Printf("name: %s, age: %d, gender: %d, address: %s",
			person.Name,
			person.Age,
			person.Gender,
			person.Address)
		fmt.Println()
	}
}

func (person *Person)SetAddress(address string)  {
	if person == nil {
		fmt.Println("不能为空数据设值")
		return
	}
	person.Address = address
}

func CreatePerson(name string, age int8) *Person  {
	// & 与new关键字类似
	return &Person{Name: name, Age: age}
}

