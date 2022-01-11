import com.junling.rpc.common.map.ServiceProvider;
import com.junling.rpc.sample.api.UserService;
import com.junling.rpc.sample.server.UserServiceImpl;
import com.junling.rpc.server.RpcServer;
import com.junling.rpc.server.netty.NettyServer;

public class ServerTest {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        RpcServer rpcServer = new NettyServer(8888);
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.serviceRegister(userService);
        rpcServer.start();
    }
}
