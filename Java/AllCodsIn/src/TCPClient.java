/**
 * @Time : 2021.2.5 21:02
 * @Author : Synthesis 杜品赫
 * @Proj : TCPClientDemo01
 * @Software : IntelliJ IDEA
 * https://github.com/ddzbxh
 */

import java.io.*;
import java.net.*;
import java.net.Socket.*;

// 客户端
public class TCPClient {
    private int toPython;
    public TCPClient() {
        toPython = 9007;
    }
    public TCPClient(int portNum) {
        toPython = portNum;
        String fileName = Jython.getPathOfPythonFile() + "\\Ports.bin";
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(String.valueOf(portNum));
            out.close();
        } catch (IOException e) { }
    }
    public boolean sendString(String s) {
        Socket socket = null;
        OutputStream os = null;
        InputStream is = null;
        try {
            //1.获取服务器的地址+端口号
            InetAddress serverIP = InetAddress.getByName("127.0.0.1");
            int port = toPython;
            //2.创建一个socket
            socket = new Socket(serverIP, port);
            //3.发送消息 IO流
            os = socket.getOutputStream();
            os.write(s.getBytes());
            //4.读取客户端的消息
            is = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int len = is.read(buffer);
            String str = new String(buffer,0,len);
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    return true;
    }

    public static void main(String[] args) throws IOException{
        TCPClient a = new TCPClient(9007);
        Jython py = new Jython();
        py.pyServer();
        a.sendString("Hello world!");
    }
}
