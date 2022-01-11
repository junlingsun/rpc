package com.junling.rpc.server.netty;

import com.junling.rpc.common.bean.RpcRequest;
import com.junling.rpc.common.bean.RpcResponse;
import com.junling.rpc.common.map.ServiceProvider;
import com.junling.rpc.server.RequestHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import lombok.Data;

@Data
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private RequestHandler requestHandler = new RequestHandler();
    private ServiceProvider serviceProvider = new ServiceProvider();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest rpcRequest) throws Exception {
        try {
            String interfaceName = rpcRequest.getInterfaceName();
            Object service = serviceProvider.getService(interfaceName);
            requestHandler.setRpcRequest(rpcRequest);
            requestHandler.setService(service);
            Object result = requestHandler.handle();
            RpcResponse<Object> success = RpcResponse.success(result);
            System.out.println("result " + success);
            ChannelFuture future = ctx.writeAndFlush(success);
            future.addListener(ChannelFutureListener.CLOSE);
        } finally {
            ReferenceCountUtil.release(rpcRequest);
        }

    }


}
