package org.zhouhao.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zhouhao.coder.CommonDecoder;
import org.zhouhao.coder.CommonEncoder;
import org.zhouhao.serializer.JsonSerializer;
import org.zhouhao.RpcServer;
import org.zhouhao.serializer.KyroSerializer;

/**
 * netty服务端（NettyServerHandler为工作线程）:
 * 1.监听端口
 * 2.客户端连接成功，则交给Handler做
 */
public class NettyServer implements RpcServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);


    @Override
    public void start(int port) {
        //创建两个线程组 boosGroup、workerGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务端的启动对象，设置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //设置两个线程组boosGroup和workerGroup
            serverBootstrap.group(bossGroup, workerGroup)
                    //设置服务端通道实现类型
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG, 256)
                    //设置保持活动连接状态
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    //使用匿名内部类的形式初始化通道对象
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //给pipeline管道设置处理器
                            pipeline.addLast(new CommonEncoder(new KyroSerializer()))
                                    .addLast(new CommonDecoder())
                                    .addLast(new NettyServerHandler());

                        }
                    });
            //绑定端口号，启动服务端 channelFuture提供操作完成时一种异步通知的方式
            ChannelFuture future = serverBootstrap.bind(port).sync();
            //对关闭通道进行监听
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("start server error: ", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
