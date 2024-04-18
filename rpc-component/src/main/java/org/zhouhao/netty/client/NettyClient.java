package org.zhouhao.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;

import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zhouhao.RpcClient;
import org.zhouhao.coder.CommonDecoder;
import org.zhouhao.coder.CommonEncoder;
import org.zhouhao.request.RpcRequest;
import org.zhouhao.response.RpcResponse;
import org.zhouhao.serializer.JsonSerializer;
import org.zhouhao.netty.server.NettyServerHandler;

/**
 * Netty客户端
 * 1.序列化request
 * 2.send request
 * 3.返回response
 */
public class NettyClient implements RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private String host;
    private int port;
    private static final Bootstrap bootstrap;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    static {
        EventLoopGroup group = new NioEventLoopGroup();
        //创建bootstrap对象，配置参数
        bootstrap = new Bootstrap();
        //设置线程组
        bootstrap.group(group)
                //设置客户端的通道实现类型
                .channel(NioSocketChannel.class)
                //使用匿名内部类初始化通道
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //添加客户端通道的处理器
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new CommonDecoder())
                                .addLast(new CommonEncoder(new JsonSerializer()))
                                .addLast(new NettyClientHandler());
                    }
                });

    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try {
            //连接服务端
            ChannelFuture future = bootstrap.connect(host, port).sync();
            logger.info("client connect server {}:{}", host, port);
            Channel channel = future.channel();
            if (channel != null) {
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if (future1.isSuccess()) {
                        logger.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                    } else {
                        logger.error("发送消息时有错误发生: ", future1.cause());
                    }
                });
                //对通道关闭进行监听
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                RpcResponse rpcResponse = channel.attr(key).get();
                System.out.printf(rpcResponse.toString());
                return rpcResponse.getData();
            }
        } catch (InterruptedException e) {
            logger.error("send messg error: ", e);
        }
        return null;
    }
}
