package org.zhouhao;

import org.zhouhao.request.RpcRequest;
import org.zhouhao.serializer.CommonSerializer;

public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
    void setSerializer(CommonSerializer serializer);
}
