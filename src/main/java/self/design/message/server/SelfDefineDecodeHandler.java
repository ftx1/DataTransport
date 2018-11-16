package self.design.message.server;



import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class SelfDefineDecodeHandler extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes()<4){
            return;
        }
        int beginIndex = byteBuf.readerIndex();
        System.out.println("beginIndex:"+beginIndex);
        int length = byteBuf.readIntLE();
        System.out.println("length:"+length);
        if(byteBuf.readableBytes()<length){
            //byteBuf.readerIndex(beginIndex);
            return;
        }
        beginIndex = byteBuf.readerIndex();
        ByteBuf otherByteBufRef = byteBuf.slice(beginIndex, 4 + length);
        otherByteBufRef.retain();
        System.out.println("send out!");
        list.add(otherByteBufRef);

    }

}
