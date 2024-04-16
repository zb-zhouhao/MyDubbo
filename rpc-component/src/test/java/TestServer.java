import org.zhouhao.server.RpcServer;
import org.zhouhao.service.ZhService;
import org.zhouhao.service.ZhServiceImpl;

public class TestServer {
    public static void main(String[] args) {
        ZhService zhService = new ZhServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(zhService, 0227);
    }
}
