package mock

type Retriever struct {
	Context string
}

func (r Retriever) Get(url string) string  {
	return r.Context + " -> " + url
}
