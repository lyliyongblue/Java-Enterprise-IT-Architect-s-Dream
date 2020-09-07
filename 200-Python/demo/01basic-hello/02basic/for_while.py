#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# for循环，只能使用 x in ...
names = ['AA', 'BB', 'CC']
for name in names:
    print("name:", name)

# 循环5次
for index in [0, 1, 2, 3, 4]:
    print("index:", index)

for index in range(5):
    print("range index:", index)

n = 3
while n > 0:
    print("n:", n)
    n = n - 1

# break   continue 类似于java
