import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ChannelServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(9999));

        Selector selector = Selector.open();

        ByteBuffer buf = ByteBuffer.allocate(1024);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
//            socketChannel.configureBlocking(false);
//            SelectionKey key = socketChannel.register(selector, SelectionKey.OP_READ);
            
            boolean flag = true;
            while (flag) {
                // 接收从客户端发送过来的数据
                buf.clear();
                int bytesRead = socketChannel.read(buf); // read into
                // buffer.

                if (bytesRead != -1) {

                    buf.flip(); // make buffer ready for read
                    // byte[] b = new byte[buf.remaining()];
                    // buf.get(b, 0, b.length);
                    String str = new String(buf.array(), 0, buf.limit());

                    if (str == null || "".equals(str)) {
                        flag = false;
                    } else {
                        if ("bye".equals(str)) {
                            flag = false;
                        } else {

                            buf.clear();
                            buf.put(("echo:" + str).getBytes());
                            buf.flip();

                            while (buf.hasRemaining()) {
                                socketChannel.write(buf);
                            }

                            System.out.println("receive:" + str); // read 1 byte
                        }
                    }

                }
            }

        }
    }
}