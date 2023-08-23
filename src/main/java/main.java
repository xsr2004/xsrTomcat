import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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
            //先接收浏览器发送的信息
            //inputStream是字节流 为了读取方便转成一个BuffreadReader字符流。可以整行整行的读取
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
        }
    }
}
