from gensim.models import KeyedVectors

model = KeyedVectors.load_word2vec_format('M:\我是研究生\词表\wiki50.en.text.vector.石磊', binary=False)
# model = KeyedVectors.load_word2vec_format('GoogleNews-vectors-negative300.bin', binary=True)

print("# 获取词向量")
print(model['computer'])

print("# 计算一个词的最近似的词，倒排序")
print(model.most_similar(['girl']))

print("# 计算两词之间的余弦相似度")
print(model.similarity('girl', 'man'))
print(model.similarity('girl', 'body'))
print(model.similarity('girl', 'woman'))

print("# 计算两个集合之间的余弦似度")
list1 = ['my', 'country', 'is', 'best']
list2 = ['your', 'city', 'bad']
list3 = ['he', 'love', 'you']
print(model.n_similarity(list2, list1))
print(model.n_similarity(list2, list3))


"""
Compute the Word Mover's Distance between two documents. When using this
        code, please consider citing the following papers:
        .. Ofir Pele and Michael Werman, "A linear time histogram metric for improved SIFT matching".
        .. Ofir Pele and Michael Werman, "Fast and robust earth mover's distances".
        .. Matt Kusner et al. "From Word Embeddings To Document Distances".
"""
print("wmdistance")
print(model.wmdistance(list1, list2))
print(model.wmdistance(list3, list2))
