package connect;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

/**
 * 〈一句话功能简述〉<br>
 * 〈nettyserver端〉
 *
 * @author tenshawfeng
 * @create 2018/11/16下午3:14
 * @since 1.0.0
 */
public class NettyServer {
    private static final int PORT = 9090;

    public static void main(String[] args) {
        NioEventLoopGroup boos = new NioEventLoopGroup();
        NioEventLoopGroup workers = new NioEventLoopGroup();
        AttributeKey<Object> clientKey = AttributeKey.newInstance("clientKey");
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boos, workers)
                .channel(NioServerSocketChannel.class)
                .attr(AttributeKey.newInstance("ServerName"), "nettyServer")
                .childAttr(clientKey, "clientValue")
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("建立了连接");
                        super.channelActive(ctx);
                    }
                }).childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                System.out.println(ch.attr(clientKey).get());
            }
        });

        run(serverBootstrap, PORT);
    }

    private static void run(ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
            }
        });
    }
}
