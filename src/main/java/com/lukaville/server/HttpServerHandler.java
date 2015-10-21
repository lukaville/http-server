package com.lukaville.server;

import com.lukaville.server.http.ContentTypeDetector;
import com.lukaville.server.http.ErrorGenerator;
import com.lukaville.server.http.HttpHeader;
import com.lukaville.server.http.HttpRequest;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import static com.lukaville.server.http.HttpHeader.*;

public class HttpServerHandler extends SimpleChannelInboundHandler<HttpRequest> {
    private static final String READ_FILE_MODE = "r";
    private HttpServer server;

    public HttpServerHandler(HttpServer server) {
        this.server = server;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpRequest request) throws Exception {
        if (!request.isValid()) {
            sendError(ctx, HttpHeader.BAD_REQUEST);
            return;
        }

        String path = request.getPath();
        if (path == null) {
            sendError(ctx, HttpHeader.FORBIDDEN);
            return;
        }

        File file = new File(server.getDirectory() + path);
        if (file.isHidden() || !file.exists()) {
            sendError(ctx, HttpHeader.NOT_FOUND);
            return;
        }

        if (file.isDirectory()) {
            File indexFile = new File(file.getPath() + "/" + server.getIndexFile());;
            if (indexFile.exists() && !indexFile.isHidden()) {
                file = indexFile;
            } else {
                sendError(ctx, HttpHeader.FORBIDDEN);
                return;
            }
        }

        writeResponse(ctx, file, request);
    }

    private void writeResponse(ChannelHandlerContext ctx, File file, HttpRequest request) throws IOException {
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, READ_FILE_MODE);
        } catch (FileNotFoundException ignore) {
            sendError(ctx, NOT_FOUND);
            return;
        }

        long fileLength = randomAccessFile.length();

        HttpHeader httpHeader = new HttpHeader(OK);
        httpHeader.addHeader(HEADER_CONTENT_LENGTH, String.valueOf(fileLength));

        String extension = FilenameUtils.getExtension(file.getPath());
        httpHeader.addHeader(HEADER_CONTENT_TYPE, ContentTypeDetector.getContentType(extension));

        ChannelFuture future;
        if (request.getMethod() == HttpRequest.Method.HEAD) {
            future = ctx.writeAndFlush(httpHeader);
        } else {
            ctx.write(httpHeader);
            future = ctx.writeAndFlush(new DefaultFileRegion(randomAccessFile.getChannel(), 0, fileLength));
        }
        future.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private static void sendError(ChannelHandlerContext ctx, int error) {
        final String errorBody = ErrorGenerator.generateErrorPage(error, HttpHeader.statusDescriptions.get(error));

        HttpHeader headers = new HttpHeader(error);
        headers.addHeader(HEADER_CONTENT_LENGTH, String.valueOf(errorBody.length()));
        ctx.write(headers);
        ctx.writeAndFlush(Unpooled.wrappedBuffer(errorBody.getBytes())).addListener(ChannelFutureListener.CLOSE);
    }
}