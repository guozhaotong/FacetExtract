import jieba

stopwords = {}.fromkeys([line.rstrip() for line in open('stopword.txt', encoding="utf-8")])

from gensim.models import KeyedVectors

model = KeyedVectors.load_word2vec_format('M:\我是研究生\词表\wiki50.en.text.vector.石磊', binary=False)


def read(file_path):
    with open(file_path) as file:
        string = file.read()
    cut_result = jieba.lcut(string)
    result = []
    for word in cut_result:
        if "" == word.strip():
            continue
        if word.lower() not in stopwords:
            result.append(word.lower())
    return result


root_path = "C:\\Users\\tong\\Desktop\\新建文件夹\\"
str1 = "Binary tree.txt"
str2 = "Quadtree.txt"
str3 = "Bubble sort.txt"
str4 = "Merge sort.txt"
str5 = "Octree.txt"
str6 = "Quick sort.txt"
file_list = [str1, str2, str3, str4, str5, str6]
for file_level_1 in file_list:
    for file_level_2 in file_list:
        try:
            print(model.n_similarity(read(root_path + file_level_1), read(root_path + file_level_1)))
        except Exception as e:
            print(e)
