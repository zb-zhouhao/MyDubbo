package org.zhouhao.netty.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zhouhao.request.RpcRequest;
import org.zhouhao.response.RpcResponse;
import org.zhouhao.socket.server.RequestHandler;
import org.zhouhao.service.DefaultServiceProvider;
import org.zhouhao.service.ServiceProvider;
import org.zhouhao.thread.ThreadPoolFactory;

import java.util.concurrent.ExecutorService;

/**
 * 1.接收request
 * 2.从注册中心查找服务
 * 3.反射调用
 * 4.生成response返回
 *
 *  自定义的Handler需要继承Netty规定好的HandlerAdapter
 *  才能被Netty框架所关联，有点类似SpringMVC的适配器模式
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private static RequestHandler requestHandler;
//    private static ServiceProvider serviceProvider;

    private static final String THREAD_NAME_PREFIX = "netty-server-handler";

    private static final ExecutorService threadPool;

    static {
        requestHandler = new RequestHandler();
//        serviceProvider = new DefaultServiceProvider();
        threadPool = ThreadPoolFactory.createDefaultThreadPool(THREAD_NAME_PREFIX);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        try {
            //获取客户端发送过来的消息
            threadPool.execute(() -> {
                logger.info("server receive request: {}", rpcRequest);
//                String interfaceName = rpcRequest.getInterfaceName();
//            Object service = serviceProvider.getService(interfaceName);
                Object result = requestHandler.handle(rpcRequest);
                //发送消息给客户端
                ChannelFuture future =
                        channelHandlerContext.writeAndFlush(RpcResponse.success(result,
                                rpcRequest.getRequestId()));
                future.addListener(ChannelFutureListener.CLOSE);
            });

        } finally {
            ReferenceCountUtil.release(rpcRequest);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //发生异常，关闭通道
        logger.error("处理过程调用时有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }
}
