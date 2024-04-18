package org.zhouhao.service;

/**
 * 服务注册功能接口
 */
public interface ServiceProvider {
    <T> void register(T service);
    Object getService(String serviceName);
}
