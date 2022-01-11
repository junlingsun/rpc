package com.junling.rpc.client.netty;

import com.junling.rpc.client.RpcClient;
import com.junling.rpc.common.bean.RpcRequest;
import com.junling.rpc.common.bean.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.AttributeKey;
import lombok.Data;

import javax.imageio.spi.ServiceRegistry;
import java.util.concurrent.atomic.AtomicReference;


@Data
public class NettyClient implements RpcClient {

    private static final Bootstrap bootstrap;
//    private static ServiceRegistry serviceRegistry;



    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)))
                                .addLast(new ObjectEncoder())
                                .addLast(new NettyClientHandler());

                    }
                });
    }

    @Override
    public Object send(String host, Integer port, RpcRequest rpcRequest) {

        AtomicReference<Object> result = new AtomicReference<>(null);

        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            //TODO: logger.info("客户端连接到服务器 {}:{}", host, port);
            Channel channel = future.channel();
            if(channel != null) {
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if(future1.isSuccess()) {
                        //TODO: logger.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                    } else {
                        //TODO: logger.error("发送消息时有错误发生: ", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                RpcResponse rpcResponse = channel.attr(key).get();
                return rpcResponse;
            }
        } catch (InterruptedException e) {
            //TODO:logger.error("发送消息时有错误发生: ", e);
        }
        return null;
    }
}
