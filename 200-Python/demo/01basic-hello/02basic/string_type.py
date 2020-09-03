#!/usr/bin/env python3
# _*_ coding: utf-8 _*_
print('中文 English')

# ord()函数获取字符的整数表示
print(ord('A'))
print(ord('中'))

# chr()函数把编码转换为对应的字符
print(chr(66))
print(chr(25991))

# 使用16进制表示汉字
print('16进制', '\u4e2d\u6587')
# 16进制 中文

# 字符串的 encode
print('bytes', b'ABC')
# bytes b'ABC'
print('str encode to bytes:', 'ABC'.encode('ascii'))
# str encode to bytes: b'ABC'
print('中文 encode to utf-8:', '中文'.encode('utf-8'))
# 中文 encode to utf-8: b'\xe4\xb8\xad\xe6\x96\x87'

# 字符串的 decode
print('bytes to ascii:', b'ABC'.decode('ascii'))
# bytes to ascii: ABC
print('bytes to ascii:', b'ABC'.decode('utf-8'))
# bytes to ascii: ABC
print('中文bytes to utf-8:', b'\xe4\xb8\xad\xe6\x96\x87'.decode('utf-8'))
# 中文bytes to utf-8: 中文
print('错误的中文bytes to utf-8:', b'\xe4\xb8\xad\xe6\x87'.decode('utf-8', errors='ignore'))
# 错误的中文bytes to utf-8: 中

# str len 计算str包含多少个字符，用len()函数
print(len('ABC'))
# 3
print(len('中文'))
# 2

# bytes，len()函数就计算字节数
print(len(b'ABC'))
# 3
print(len(b'\xe4\xb8\xad\xe6\x96\x87'))
# 6
print(len('中文'.encode('utf-8')))
# 6

