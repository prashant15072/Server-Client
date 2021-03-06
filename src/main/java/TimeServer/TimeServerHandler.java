package TimeServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by PRASHANT on 4/13/2017.
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        final ByteBuf time=ctx.alloc().buffer();
        time.writeInt((int) (System.currentTimeMillis()/1000L + 2208988800L));
        final ChannelFuture f=ctx.writeAndFlush(time);
        f.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                assert f==channelFuture;
                ctx.close();
            }
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
