package self.design.message.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

public class BusinessServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        int length = byteBuf.readInt();
        assert length ==8;

        byte[] head = new byte[4];
        byteBuf.readBytes(head);
        String headString = new String(head);
        System.out.println(headString);

        byte[] body = new byte[4];
        byteBuf.readBytes(body);
        String bodyString = new String(body);
        System.out.println(bodyString);
    }
}
