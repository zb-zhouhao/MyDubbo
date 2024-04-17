import org.zhouhao.server.RpcServer;
import org.zhouhao.server.RpcServerEnableMultiService;
import org.zhouhao.service.DefaultServiceRegistry;
import org.zhouhao.service.ServiceRegistry;
import org.zhouhao.service.ZhService;
import org.zhouhao.service.ZhServiceImpl;

public class TestServer {
    public static void main(String[] args) {
//        ZhService zhService = new ZhServiceImpl();
//        RpcServer rpcServer = new RpcServer();
//        rpcServer.register(zhService, 0227);

        ZhService zhService = new ZhServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(zhService);
        RpcServerEnableMultiService rpcServerEnableMultiService = new RpcServerEnableMultiService(serviceRegistry);
        rpcServerEnableMultiService.start(0227);
    }
}
