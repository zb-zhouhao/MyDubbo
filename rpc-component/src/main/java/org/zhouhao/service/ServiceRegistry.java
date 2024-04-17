package org.zhouhao.service;

/**
 * 服务注册功能接口
 */
public interface ServiceRegistry {
    <T> void register(T service);
    Object getService(String serviceName);
}
