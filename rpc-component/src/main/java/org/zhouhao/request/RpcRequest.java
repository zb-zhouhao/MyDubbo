package org.zhouhao.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 服务端定位一个服务（方法）需要知道的信息
 */
@Data
@Builder
public class RpcRequest implements Serializable {
    /**
     * 接口名称
     */
    private String interfaceName;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 参数列表
     */
    private Object[] parameters;
    /**
     * 参数类型
     */
    private Class<?>[] paramTypes;
}
