/**
 * @Time : 2021.2.5 21:03
 * @Author : Synthesis 杜品赫
 * @Proj : TCPSeverDemo01
 * @Software : IntelliJ IDEA
 * https://github.com/ddzbxh
 */

import java.io.*;
import java.net.*;

//服务端
public class TCPSever {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            //1.生成一个地址
            serverSocket = new ServerSocket(9999);
            while (true){
                //2.等待客户端连接
                socket = serverSocket.accept();
                //3.读取客户端的消息
                is = socket.getInputStream();
                //管道流
                baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len=is.read(buffer))!=-1){
                    baos.write(buffer, 0, len);
                }
                System.out.println(baos.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (baos != null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
