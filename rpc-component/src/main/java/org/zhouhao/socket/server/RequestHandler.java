package org.zhouhao.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zhouhao.request.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public Object handle(RpcRequest rpcRequest, Object service) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object result = null;
        Method method = service.getClass().getMethod(rpcRequest.getMethodName(),
                rpcRequest.getParamTypes());
        result = method.invoke(service, rpcRequest.getParameters());
        return result;
    }
}
