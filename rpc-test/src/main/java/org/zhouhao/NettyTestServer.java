package org.zhouhao;

import org.zhouhao.netty.server.NettyServer;
import org.zhouhao.service.DefaultServiceRegistry;
import org.zhouhao.service.ServiceRegistry;
import org.zhouhao.service.ZhService;
import org.zhouhao.service.ZhServiceImpl;

public class NettyTestServer {


    public static void main(String[] args) {
        ZhService zhService = new ZhServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(zhService);
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(9999);
    }
}
