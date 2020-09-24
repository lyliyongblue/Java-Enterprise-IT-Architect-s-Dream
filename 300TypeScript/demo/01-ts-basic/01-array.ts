// 数组
const arr: (number | string)[] = [1, '2', 3]
const stringArr: string[] = ['a', 'b', 'c']
const undefinedArr: undefined[] = [undefined]

// type alias 类型别名
type User = { name: string; age: number }
const userArr: User[] = [{ name: 'yong', age: 10 }]

class Teacher {
    name: string;
    age: number
}

const ojbectArr: Teacher[] = [
    new Teacher(),
    {
        name: 'li.yong',
        age: 15
    }
]


// 元组 tuple
const teacherInfo : [string, string, number] = ['Yong', 'male', 16]
// 元组数组
const teacherList: [string, string, number][] = [
    ['Yong', 'male', 10],
    ['Li', 'female', 16]
]
