package org.zhouhao.exception;

import org.zhouhao.exception.statuscode.RpcError;

/**
 * rpc调用异常
 */
public class RpcException extends RuntimeException{
    public RpcException(RpcError rpcError, String detail) {
        super(rpcError.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());
    }
}
