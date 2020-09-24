// interface 和 type 相类似，但是并不完全一致
interface Person {
    name: string;
    readonly gender?: string;
    age?: number; // 属性名加?表示该接口可有可无
    [propName: string]: any; // 其他任意属性是string类型
    say(): string;
}

// 接口可以继承
interface Teacher extends Person {
    teach(): string
}

// interface定义一个函数
interface SayHi {
    (word: string): string
}

const getPersonName1 = (person: {name: string}) => {
    console.log(person.name)
}

const setPersionName1 = (person: {name: string}, name: string): void => {
    person.name = name
}

const getPersonName = (person: Person): string => {
    console.log(person.name)
    return person.name
}

const setPersionName = (person: Person, name: string): void => {
    person.name = name
}

const person = {
    name: 'Yong',
    gender: 'male',
    age: 10,
    say() {
        return 'I\'m ' + name
    }
}

getPersonName(person)

const getPersonName2 = (person: Teacher) => {
    console.log(person.name)
}