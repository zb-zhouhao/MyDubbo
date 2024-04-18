package org.zhouhao;

import org.zhouhao.netty.client.NettyClient;
import org.zhouhao.serializer.KyroSerializer;
import org.zhouhao.service.ZhObject;
import org.zhouhao.service.ZhService;

public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient();
        client.setSerializer(new KyroSerializer());
        RpcNettyClientDynamicProxy rpcNettyClientDynamicProxy = new RpcNettyClientDynamicProxy(client);
        ZhService zhService = rpcNettyClientDynamicProxy.getProxy(ZhService.class);
        ZhObject object = new ZhObject(12, "This is a message");
        String res = zhService.hello(object);
        System.out.println(res);
    }
}
