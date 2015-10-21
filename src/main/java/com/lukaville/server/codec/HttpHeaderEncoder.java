package com.lukaville.server.codec;

import com.lukaville.server.http.HttpHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by nickolay on 20.10.15.
 */
public class HttpHeaderEncoder extends MessageToByteEncoder<HttpHeader> {
    @Override
    protected void encode(ChannelHandlerContext ctx, HttpHeader msg, ByteBuf out) throws Exception {
        out.writeBytes(msg.toString().getBytes());
    }
}
