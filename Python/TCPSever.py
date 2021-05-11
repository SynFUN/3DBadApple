# -*- coding = utf-8 -*-
# encoding: utf-8
# @Time : 2021.5.11 12:09
# @Author : Synthesis 杜品赫
# @File : TCPSever.py
# @Software : PyCharm
# https://github.com/SynthesisDu/MC_BadAppleDGDH

import socketserver

class TCPSever(socketserver.BaseRequestHandler):
    def handle(self):
        while True:
            data = self.request.recv(1024).decode('UTF-8', 'ignore').strip()
            if not data : break
            print(data)
            feedback_data = ("copy\""+data+"\":").encode("utf8")
            print("@")
            self.request.sendall(feedback_data)

host = '127.0.0.1'
data = open("Ports.bin", "r", encoding='utf-8')
port = int(data.readline())
data.close()
server = socketserver.ThreadingTCPServer((host, port), TCPSever)
server.serve_forever()