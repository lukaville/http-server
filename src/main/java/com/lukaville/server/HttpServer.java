package com.lukaville.server;

import com.lukaville.server.codec.HttpHeaderEncoder;
import com.lukaville.server.codec.HttpRequestDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class HttpServer {
    public static final String SERVER_NAME = "LukavilleHttpServer";

    private String host;
    private int port;
    private String directory;
    private String indexFile;
    private int cpuNumber;

    public HttpServer(String host, int port, String directory, String indexFile, int cpuNumber) {
        this.host = host;
        this.port = port;
        this.directory = directory;
        this.indexFile = indexFile;
        this.cpuNumber = cpuNumber;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast(new StringDecoder());
                     ch.pipeline().addLast(new HttpRequestDecoder());
                     ch.pipeline().addLast(new HttpHeaderEncoder());
                     ch.pipeline().addLast(new HttpServerHandler(HttpServer.this));
                 }
             });

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDirectory() {
        return directory;
    }

    public int getCpuNumber() {
        return cpuNumber;
    }

    public String getIndexFile() {
        return indexFile;
    }
}