package org.zhouhao;

import org.zhouhao.request.RpcRequest;

public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
}
