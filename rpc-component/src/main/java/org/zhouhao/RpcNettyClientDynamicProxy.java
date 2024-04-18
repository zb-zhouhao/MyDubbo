package org.zhouhao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zhouhao.request.RpcRequest;
import org.zhouhao.response.RpcResponse;
import org.zhouhao.socket.client.SocketRpcClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcNettyClientDynamicProxy implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(RpcNettyClientDynamicProxy.class);
    private final RpcClient rpcClient;

    public RpcNettyClientDynamicProxy(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
    }

    public <T> T getProxy(Class<T> clazz) {
        return  (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        RpcRequest rpcRequest = RpcRequest.builder()
//                .interfaceName(method.getDeclaringClass().getName())
//                .methodName(method.getName())
//                .parameters(args)
//                .paramTypes(method.getParameterTypes())
//                .build();
//        SocketRpcClient rpcClient = new SocketRpcClient();
//        return ((RpcResponse) rpcClient.sendRequest(rpcRequest, host, port)).getData();
        logger.info("call method {}#{}", method.getDeclaringClass().getName(), method.getName());
        RpcRequest rpcRequest = new RpcRequest(method.getDeclaringClass().getName(),
                method.getName(), args, method.getParameterTypes());
        return rpcClient.sendRequest(rpcRequest);
    }
}
