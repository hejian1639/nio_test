import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ChannelClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(true);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        ByteBuffer buf = ByteBuffer.allocate(1024);

        boolean flag = true;

        while (flag) {
            System.out.print("输入信息：");
            String str = input.readLine();

            // 发送数据到服务端
            buf.clear();
            buf.put(str.getBytes());
            buf.flip();

            while (buf.hasRemaining()) {
                socketChannel.write(buf);
            }

//            if ("bye".equals(str)) {
//                flag = false;
//            } else {
//                buf.clear();
//                int bytesRead = socketChannel.read(buf); // read into
//                // buffer.
//                if (bytesRead != -1) {
//
//                    buf.flip(); // make buffer ready for read
//                    str = new String(buf.array(), 0, buf.limit());
//
//                    System.out.println(str); // read 1 byte
//
//                }
//
//            }
        }
        input.close();

        socketChannel.close();

    }
}