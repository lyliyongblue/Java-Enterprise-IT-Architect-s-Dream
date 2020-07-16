package mock

type Retriever struct {
	Context string
}

/*
类似于Java重写toString，对于go只需要实现go的Stringer接口
 */
func (r *Retriever) String() string {
	return ">>>>>>>>>" + r.Context
}

/*
该Post中需要修改Retriever，并且需要在Get中使用，那么只能使用×指针接收者
 */
func (r *Retriever) Post(url string, form map[string]string) string {
	r.Context = form["contents"]
	return "ok: " + url
}

func (r *Retriever) Get(url string) string  {
	return r.Context + " -> " + url
}
