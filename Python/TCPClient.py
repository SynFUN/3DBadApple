# -*- coding = utf-8 -*-
# encoding: utf-8
# @Time : 2021.5.11 12:10
# @Author : Synthesis 杜品赫
# @File : TCPClient.py
# @Software : PyCharm
# https://github.com/SynthesisDu/MC_BadAppleDGDH

import socket
import time

# 创建 socket
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# 建立连接
s.connect(("127.0.0.1", 9999))

# 接收服务器消息
print(s.recv(1024).decode())

for data in [b'Michael', b'Tracy', b'Sarah']:
    # 发送数据
    s.send(data)
    time.sleep(2)
    # 打印接收到的数据
    print(s.recv(1024).decode('utf-8'))
    time.sleep(1)

time.sleep(3)
# 请求退出
s.send(b'exit')
time.sleep(2)
print(s.recv(1024).decode('utf-8'))

# 关闭连接
s.close()

