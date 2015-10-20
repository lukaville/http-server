package com.lukaville.server.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by nickolay on 20.10.15.
 */
public class HttpResponseEncoder extends MessageToByteEncoder<HttpResponse> {
    @Override
    protected void encode(ChannelHandlerContext ctx, HttpResponse msg, ByteBuf out) throws Exception {
        out.writeBytes(msg.toString().getBytes());
    }
}
