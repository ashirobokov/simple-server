package ru.ashirobokov.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.net.InetAddress;
import java.util.Date;

public class SimpleServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int cnt=0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("Channel active " + cnt++);
        ctx.write(Unpooled.copiedBuffer("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n", CharsetUtil.UTF_8));
        ctx.write(Unpooled.copiedBuffer("It is " + new Date() + " now.\r\n", CharsetUtil.UTF_8));
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
        System.out.println("\nServer received : " + buf.toString(CharsetUtil.UTF_8) + "\n");
        ChannelFuture f = ctx.writeAndFlush(Unpooled.copiedBuffer("Hi from simple server!", CharsetUtil.UTF_8))
                .addListener(ChannelFutureListener.CLOSE);

/*
        ChannelFuture f = ctx.writeAndFlush(Unpooled.copiedBuffer("Hi from simple server!", CharsetUtil.UTF_8))
                        .addListener(new ChannelFutureListener() {
                            @Override
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                if (channelFuture.isDone()) {
                                    ctx.channel().close();
                                }
                            }
                        });
*/

        System.out.println("Channel READ-0 " + cnt++);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        System.out.println("Channel registered " + cnt++);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        System.out.println("Channel Unregistered " + cnt++);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("Channel inactive " + cnt++);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        System.out.println("Channel read complete " + cnt++);
    }

}