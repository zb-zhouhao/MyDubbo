import org.zhouhao.client.RpcClientDynamicProxy;
import org.zhouhao.service.ZhObject;
import org.zhouhao.service.ZhService;

public class TestClient {
    public static void main(String[] args) {
        RpcClientDynamicProxy proxy = new RpcClientDynamicProxy("127..0.0.1", 0227);
        ZhService zhService =  proxy.getProxy(ZhService.class);
        ZhObject zhObject = new ZhObject(16, "This is a message");
        String res = zhService.hello(zhObject);
        System.out.printf(res);
    }

}