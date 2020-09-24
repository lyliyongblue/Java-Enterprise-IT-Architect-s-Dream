// 抽象类
abstract class Geom {
    abstract getArea(): number

    getType() {
        return "Geom"
    }
}

class Circle extends Geom {
    getArea(): number {
        throw new Error("Method not implemented.");
    }
}

class Square extends Geom {
    getArea(): number {
        throw new Error("Method not implemented.");
    }

}