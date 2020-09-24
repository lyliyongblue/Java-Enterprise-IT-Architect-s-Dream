#!/usr/bin/env python3
# -*- coding: utf-8 -*-
names = ['Michael', 'Bob', 'Tracy']
print(names)
# ['Michael', 'Bob', 'Tracy']
# 使用len获取list长度
print("names length: %s" % len(names))
# names length: 3
print('index %s: %s' % (1, names[0]))
# index 1: Michael
print('index %s: %s' % (2, names[1]))
# index 2: Bob
print('index %s: %s' % (3, names[2]))
# index 3: Tracy

# 还可以用-1做索引，直接获取最后一个元素
print('index %s: %s' % (len(names), names[-1]))
# index 3: Tracy

names.append('Li.yong')
print(names)
# ['Michael', 'Bob', 'Tracy', 'Li.yong']

names.insert(0, 'Tong')
print(names)
# ['Tong', 'Michael', 'Bob', 'Tracy', 'Li.yong']

name = names.pop()
print("name:", name)
# name: Li.yong
print(names)
# ['Tong', 'Michael', 'Bob', 'Tracy']
name = names.pop(2)
print("name:", name)
# name: Bob
print(names)
# ['Tong', 'Michael', 'Tracy']

# 要把某个元素替换成别的元素，可以直接赋值给对应的索引位置
names[1] = 'Li.yong'
print(names)
# ['Tong', 'Li.yong', 'Tracy']

# list里面的元素的数据类型也可以不同
names.append(True)
print(names)
# ['Tong', 'Li.yong', 'Tracy', True]


# tuple
# 另一种有序列表叫元组：tuple。tuple和list非常类似，但是tuple一旦初始化就不能修改
names = ('Michael', 'Bob', 'Tracy')
print(names)
# ('Michael', 'Bob', 'Tracy')
print(names[0], names[1])
# Michael Bob

# 不可变的tuple有什么意义？因为tuple不可变，所以代码更安全。如果可能，能用tuple代替list就尽量用tuple。

# 定义的不是tuple，是1这个数！这是因为括号()既可以表示tuple，又可以表示数学公式中的小括号，这就产生了歧义，因此，Python规定，这种情况下，按小括号进行计算，计算结果自然是1。
#
# 所以，只有1个元素的tuple定义时必须加一个逗号,，来消除歧义：
names = (10,)
print(names)
# (10,)
