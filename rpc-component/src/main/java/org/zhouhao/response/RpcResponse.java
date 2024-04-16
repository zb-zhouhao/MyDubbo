package org.zhouhao.response;

import lombok.Data;
import org.zhouhao.response.statuscode.ResponseCode;

import javax.xml.ws.Response;
import java.io.Serializable;

/**
 * 服务端调用完方法后，给客户端返回的信息
 */
@Data
public class RpcResponse<T> implements Serializable {
    private Integer statusCode;
    private String message;
    private T data;

    public static <T> RpcResponse<T> success(T data) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    public static <T> RpcResponse<T> fail(ResponseCode code) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }
}
