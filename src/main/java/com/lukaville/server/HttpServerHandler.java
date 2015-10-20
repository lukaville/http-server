package com.lukaville.server;

import com.lukaville.server.http.HttpResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {
    private HttpServer server;

    public HttpServerHandler(HttpServer server) {
        this.server = server;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String body = "<html><h1>Hello, world!</h1></html>";

        HttpResponse response = new HttpResponse(200);
        response.addHeader("Date", (new Date()).toString());
        response.addHeader("Server", HttpServer.SERVER_NAME);
        response.addHeader("Content-Length", String.valueOf(body.length()));
        response.addHeader("Content-Type", "text/html");
        response.addHeader("Connection", "close");
        response.setBody(body);

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}