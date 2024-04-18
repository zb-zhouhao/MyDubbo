package org.zhouhao;

import org.zhouhao.netty.client.NettyClient;
import org.zhouhao.service.ZhObject;
import org.zhouhao.service.ZhService;

public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient rpcClient = new NettyClient("127.0.0.1", 9999);
        RpcNettyClientDynamicProxy rpcNettyClientDynamicProxy = new RpcNettyClientDynamicProxy(rpcClient);
        ZhService zhService = rpcNettyClientDynamicProxy.getProxy(ZhService.class);

        String res = zhService.hello(new ZhObject(88, "this is netty test"));
        System.out.println(res);
    }
}
