package com.lukaville.server.codec;

import com.lukaville.server.http.HttpRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by nickolay on 21.10.15.
 */
public class HttpRequestDecoder extends MessageToMessageDecoder<String> {
    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        out.add(new HttpRequest("/wikipedia_russiad.html"));
    }
}
