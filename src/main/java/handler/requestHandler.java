package handler;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @Author：xsr
 * @name：requestHandler
 * @Date：2023/8/23 16:52
 * @Filename：requestHandler
 * 用线程封装接收和响应的操作，每次请求时都会创建一个线程，并不是多路复用的 改一下
 */
public class requestHandler implements Runnable{
    Socket socket=null;

    public requestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //先接收浏览器发送的信息
        //inputStream是字节流 为了读取方便转成一个BuffreadReader字符流。可以整行整行的读取
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            //读取消息
            String msg=null;
            System.out.println("==================接收到浏览器发送的数据=================");
            //循环的读取
            while ((msg=bufferedReader.readLine())!=null){
                //判断mes的长度是否为0
                if(msg.length()==0){
                    break;
                }
                System.out.println(msg);
            }
            //socket对消息进行响应，返回http的格式：响应头+响应内容
            OutputStream outputStream = socket.getOutputStream();
            //响应头
            String respHeader="HTTP/1.1 200\r\n" +
                    "Content-Type: text/html;charset=utf-8\r\n\r\n";
            //响应内容
            String resp=respHeader+"来自xsrTomcat消息";
//            流传输
            outputStream.write(resp.getBytes());
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            //最后一定确保socket要关闭
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
