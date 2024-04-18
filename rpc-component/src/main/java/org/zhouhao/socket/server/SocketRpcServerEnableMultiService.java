package org.zhouhao.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zhouhao.RpcServer;
import org.zhouhao.service.ServiceRegistry;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * 服务端允许注册多个服务
 */
public class SocketRpcServerEnableMultiService implements RpcServer {
    private final ExecutorService threadPool;
    private static final Logger logger = LoggerFactory.getLogger(SocketRpcServer.class);
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;
    private final ServiceRegistry serviceRegistry;
    private RequestHandler requestHandler = new RequestHandler();


    public SocketRpcServerEnableMultiService(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        BlockingDeque<Runnable> workingQueue = new LinkedBlockingDeque<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS, workingQueue, threadFactory);
    }

    @Override
    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("server init");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("client connect! ip addr is : " + socket.getInetAddress());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
