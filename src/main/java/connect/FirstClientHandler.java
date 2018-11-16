package connect;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author tenshawfeng
 * @create 2018/11/16下午4:23
 * @since 1.0.0
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg = new Date()+"   client send msg，有中文哟";
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(msg.getBytes()));//object

        ByteBuf bytebuf = ctx.alloc().buffer();//bytebuf
        bytebuf.writeBytes(msg.getBytes());
      //  ctx.channel().writeAndFlush(bytebuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String result = (String) msg;
        System.out.println("client received from server: "+result);

        /*ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("client received from server: "+byteBuf.toString(Charset.forName("utf-8")));*/
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
