package org.zhouhao;

import org.zhouhao.netty.server.NettyServer;
import org.zhouhao.service.DefaultServiceProvider;
import org.zhouhao.service.ServiceProvider;
import org.zhouhao.service.ZhService;
import org.zhouhao.service.ZhServiceImpl;
import org.zhouhao.serializer.KyroSerializer;

public class NettyTestServer {


    public static void main(String[] args) {
        ZhService zhService = new ZhServiceImpl();
        NettyServer server = new NettyServer("127.0.0.1", 9999);
        server.setSerializer(new KyroSerializer());
        server.publishService(zhService, ZhService.class);
    }
}
