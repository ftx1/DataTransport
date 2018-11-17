import imdemo.msg.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Assert;
import org.junit.Test;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author tenshawfeng
 * @create 2018/11/16下午8:26
 * @since 1.0.0
 */
public class PacketCodeCTest {
    @Test
    public void encode() {
        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId("fdsaf");
        loginRequestPacket.setUsername("ftx");
        loginRequestPacket.setPassword("dream");
        PacketCodeC packetCodeC = PacketCodeC.INSTANCE;
        ByteBuf byteBuf = packetCodeC.encode(ByteBufAllocator.DEFAULT,loginRequestPacket);
        Packet decodedPacket = packetCodeC.decode(byteBuf);
        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));
    }
}
