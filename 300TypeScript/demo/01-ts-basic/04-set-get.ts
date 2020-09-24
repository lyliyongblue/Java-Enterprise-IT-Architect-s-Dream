class Person2 {
    constructor(private _name: string) {}

    get name() {
        return this._name + ' li'
    }

    set name(name: string) {
        this._name = name
    }
}

// 单例
class Demo {
    private static instance: Demo = new Demo()

    private constructor() {}

    static getInstantce() {
        return this.instance
    }
}

const demo1 = Demo.getInstantce()
const demo2 = Demo.getInstantce()

console.log(demo1)
console.log(demo2)
