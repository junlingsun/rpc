import com.junling.rpc.common.map.ServiceProvider;
import com.junling.rpc.registry.loadbalance.impl.RpcLoadBalancerImpl;
import com.junling.rpc.registry.nacos.NacosRegistry;
import com.junling.rpc.sample.api.UserService;
import com.junling.rpc.sample.server.UserServiceImpl;
import com.junling.rpc.server.RpcServer;
import com.junling.rpc.server.netty.NettyServer;

public class ServerTest {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        RpcServer rpcServer = new NettyServer("localhost", 8888, new NacosRegistry(new RpcLoadBalancerImpl()));
        rpcServer.publish(userService, UserService.class);
    }
}
