package org.zhouhao.client;

import org.zhouhao.request.RpcRequest;
import org.zhouhao.response.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcClientDynamicProxy implements InvocationHandler {
    private String host;
    private int port;

    public RpcClientDynamicProxy(String host, int port) {
        host = host;
        this.port = port;
    }

    public <T> T getProxy(Class<T> clazz) {
        return  (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .build();
        RpcClient rpcClient = new RpcClient();
        return ((RpcResponse) rpcClient.sendRequest(rpcRequest, host, port)).getData();
    }
}
