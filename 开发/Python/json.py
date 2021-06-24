# -*- coding = utf-8 -*-
# @Time : 2021/6/25 0:56
# @Author : Synthesis 杜品赫
# @File : json.py
# @Software : PyCharm
# https://github.com/ddzbxh
import os

if __name__ == '__main__':
    a = ''
    data = open("hmcl.json", "r")
    for i in data.readlines():
        a = a + i
    data.close()
    aaa = a.partition("\"commonpath\": \"")
    a3 = aaa[2]
    a3a3a3 = a3.partition("\"")
    out = aaa[0] + aaa[1] + os.getcwd()[:-6].replace('\\', '\\\\') + a3a3a3[1] + a3a3a3[2]
    bbb = out.partition("\"defaultJavaPath\": \"")
    b3 = bbb[2]
    b3b3b3 = b3.partition("\"")
    out = bbb[0] + bbb[1] + (os.getcwd()[:-6] + "\\.java\\jdk1.8.0_101\\jre\\bin\\java.exe").replace('\\', '\\\\') + b3b3b3[1] + b3b3b3[2]
    data = open("hmcl.json", "w")
    data.write("")
    data.close()
    data = open("hmcl.json", "w")
    data.write(out)
    data.close()
