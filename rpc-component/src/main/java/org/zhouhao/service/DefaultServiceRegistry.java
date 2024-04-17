package org.zhouhao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zhouhao.exception.RpcException;
import org.zhouhao.exception.statuscode.RpcError;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 服务注册中心
 */
public class DefaultServiceRegistry implements ServiceRegistry {
     private static final Logger logger = LoggerFactory.getLogger(DefaultServiceRegistry.class);

     private static Map<String, Object> serviceMap = new ConcurrentHashMap<>();
     private static Set<String> registeredService = ConcurrentHashMap.newKeySet();

    /**
     * 注册服务
     * k, v : 实现的接口，注册的服务类
     * @param service
     * @param <T>
     */
    @Override
    public <T> void register(T service) {
        String serviceName = service.getClass().getCanonicalName();
        if (registeredService.contains(serviceName))
            return;
        registeredService.add(serviceName);
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0) {
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for (Class<?> i : interfaces) {
            serviceMap.put(i.getCanonicalName(), service);
        }
        logger.info("interfaceName:{} serviceName:{}", interfaces, serviceName);
    }

    @Override
    public Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (service == null) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
