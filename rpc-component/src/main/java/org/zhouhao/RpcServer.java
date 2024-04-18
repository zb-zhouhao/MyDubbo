package org.zhouhao;

import org.zhouhao.serializer.CommonSerializer;

public interface RpcServer {
    void start();

    <T> void publishService(Object service, Class<T> serviceClass);

    void setSerializer(CommonSerializer serializer);
}
