package org.zhouhao.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zhouhao.request.RpcRequest;
import org.zhouhao.response.RpcResponse;
import org.zhouhao.service.ServiceRegistry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

public class RequestHandlerThread implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerThread.class);
    private final Socket socket;
    private final ServiceRegistry serviceRegistry;

    private RequestHandler requestHandler;


    public RequestHandlerThread(Socket socket, RequestHandler requestHandler,
                                ServiceRegistry serviceRegistry) {
        this.socket = socket;
        this.serviceRegistry = serviceRegistry;
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())){
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
//            System.out.printf(service.getClass().toString());
            String interfaceName = rpcRequest.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName);
            Object result = requestHandler.handle(rpcRequest, service);
            //反射调用
//            Method method = service.getClass().getMethod(rpcRequest.getMethodName(),
//                    rpcRequest.getParamTypes());
//            Object returnObject = method.invoke(service, rpcRequest.getParameters());
            objectOutputStream.writeObject(RpcResponse.success(result));
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            logger.error("call error: ", e);
        }
    }
}
