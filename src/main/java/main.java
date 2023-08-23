import handler.requestHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  @Author：xsr
 * @name：main
 * @Date：2023/8/23 16:26
 * @Filename：main
 */
public class main {
    public static void main(String[] args) throws IOException {
        //1 创建ServletSocket 在8080端口监听
        ServerSocket serverSocket = new ServerSocket(8080);
        // 如果没有关闭就一直在等待监听
        while (!serverSocket.isClosed()){
//            接收消息，阻塞起来
            Socket socket = serverSocket.accept();
            Thread thread1 = new Thread(new requestHandler(socket));
            thread1.start();
        }
    }
}
