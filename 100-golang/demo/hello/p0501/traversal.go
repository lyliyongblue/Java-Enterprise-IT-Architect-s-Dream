package p0501

/*
duck typing
- 长的像鸭子，那就是鸭子
- 描述事务的外部行为而非内部结构
- 严格说go属于结构化类型系统，类似duck typing ，go语言是编译时就绑定的，样说它不是duck typing

<R extends Retriever> String download(R r) {
	return r.get("www.baidu.com");
}

go语言的duck typing
- 同事需要Readable, Appendable怎么办？ （apache polygene）
- 同事具有python, c++ 的duck typing的灵活性
- 又具有java的类型检查

接口由使用者定义
 */
