# _*_ coding: utf-8 _*_
# 整数
age = 100_000
print('age:', age)

# 浮点数
salary = 1000.02
print('salary:', salary)
salary = 1.00002e3
print('salary:', salary)

# 字符串
print("It's OK")
print('It\'s OK')
# 通过r表名引号中的所有字段都直接输出
print(r'It\\\'s OK')
# 通过三个引号，可以输入多行字符串
print('''line1
line2
line3
''')

# 布尔值
is_good = True
is_happy = True
# 用and or not运算
print(is_good and is_happy)
print(is_good or is_happy)
print(not is_good)

# 空值
address = None
print('Address:', address)


# 除法，计算结果是浮点数，即使是两个整数恰好整除
print('除法：', 10 / 3)
print('除法：', 9 / 3)

# 地板除 使用 //
print('地板除法：', 10 // 3)
print('地板除法：', 9 // 3)

# 求余数 %
print('求余数 10 % 3 =', 10 % 3)
print('求余数 9 % 3 =', 9 % 3)

