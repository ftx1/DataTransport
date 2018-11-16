package protobuf.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;
import protobuf.dataformat.StreamDataProto;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class ProtoBufClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("================start==================");
        StreamDataProto.SteamData.Builder builder = StreamDataProto.SteamData.newBuilder();
        builder.setType("tyep1");
        builder.setData("1234556788");
        int length = builder.build().toByteArray().length;
        UnpooledByteBufAllocator allocator = new UnpooledByteBufAllocator(false);
        ByteBuf buffer = allocator.buffer(length+4);
        buffer.writeIntLE(length);
        buffer.writeBytes(builder.build().toByteArray());
        System.out.println(length+"--"+new String(builder.build().toByteArray()));
        System.out.println(buffer.getByte(0));
        System.out.println(buffer.getByte(1));
        System.out.println(buffer.getByte(2));
        ctx.writeAndFlush(buffer);


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
