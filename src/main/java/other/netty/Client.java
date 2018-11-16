package other.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br>
 * 〈netty客户端〉
 *
 * @author tenshawfeng
 * @create 2018/11/1下午5:04
 * @since 1.0.0
 */
public class Client {
    private static class SingletonHolder {
        static final Client instance = new Client();
    }

    public static Client getInstance(){
        return SingletonHolder.instance;
    }

    private EventLoopGroup group;
    private Bootstrap b;
    private ChannelFuture cf ;

    private Client(){
        this.group = new NioEventLoopGroup();
        //1
        this.b = new Bootstrap();
        //2
        b.group(group)
                //3
                .channel(NioSocketChannel.class)
                //4
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        //1
                        ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
                        //2
                        sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buf));
                        //3
                        sc.pipeline().addLast(new StringDecoder());

                        //在这里配置具体数据接收方法的处理
                        sc.pipeline().addLast(new ReadTimeoutHandler(5));
                        sc.pipeline().addLast(new ClientHandler());
                    }
                });
    }

    public void connect(){
        try {
            this.cf = this.b.connect("127.0.0.1", 8765).sync();
            System.out.println("远程服务器已经连接, 可以进行数据交换..");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public ChannelFuture getChannelFuture(){
        //如果没有连接先链接
        if(this.cf == null){
            this.connect();
        }
        //this.cf.channel().isActive() 这里得到的是链接状态 在重新连接时候使用的 返回true/false
        if(!this.cf.channel().isActive()){
            this.connect();
        }
        return this.cf;
    }

    public static void main(String[] args) throws Exception{

        final Client c = Client.getInstance();
        //c.connect();
        ChannelFuture cf = c.getChannelFuture();
        //7
        for(int i = 1; i <= 3; i++ ){

            cf.channel().writeAndFlush(Unpooled.copiedBuffer(("message"+i+"$_").getBytes()));
            TimeUnit.SECONDS.sleep(4);
        }
        //8
        cf.channel().closeFuture().sync();

        //9 重连
        new Thread(new Runnable() {

            public void run() {
                try {
                    System.out.println("进入子线程...");
                    //重新调用连接
                    ChannelFuture cf = c.getChannelFuture();
                    System.out.println(cf.channel().isActive());
                    System.out.println(cf.channel().isOpen());

                    //再次发送数据
                    cf.channel().writeAndFlush(Unpooled.copiedBuffer("子线程数据$_".getBytes()));
                    cf.channel().closeFuture().sync();
                    System.out.println("子线程结束.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        System.out.println("断开连接,主线程结束..");




    }
}
