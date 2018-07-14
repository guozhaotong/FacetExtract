str1 = "C:\\Users\\tong\Desktop\新建文件夹\Binary tree.txt"
str2 = "C:\\Users\\tong\Desktop\新建文件夹\Quadtree.txt"
str3 = "C:\\Users\\tong\Desktop\新建文件夹\Bubble sort.txt"
str4 = "C:\\Users\\tong\Desktop\新建文件夹\Merge sort.txt"
str5 = "C:\\Users\\tong\Desktop\新建文件夹\Octree.txt"
str6 = "C:\\Users\\tong\Desktop\新建文件夹\Quick sort.txt"
file_list=[str1,str2,str3,str4,str5,str6]
for file in file_list:
    for file_level_2 in file_list:
        print(file,file_level_2)
