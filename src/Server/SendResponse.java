package Server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SendResponse {
    private SocketChannel channel;
    public SendResponse(SocketChannel channel){
        this.channel=channel;
    }

    public void sendResponse(String str) {
        Runnable run=()-> {
                ByteBuffer byteBuffer=ByteBuffer.wrap((str+"\n").getBytes());
                try {
                    channel.write(byteBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        };
            Thread thread=new Thread(run);
            thread.start();
    }
}
