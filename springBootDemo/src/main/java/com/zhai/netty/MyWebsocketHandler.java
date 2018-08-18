package com.zhai.netty;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;


/**
 * 接收、处理、响应客户端websocket请求的核心业务处理类
 * @author ZHAIMINGMING
 *
 */
public class MyWebsocketHandler extends SimpleChannelInboundHandler<Object> {

	//定义变量和常量
	private WebSocketServerHandshaker handshaker;
	private static final String WEB_SOCKET_URL="ws://localhost:8888/websocket";
	
	
	
	

	/**
	 * 客户端与服务端创建连接时调用
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		NettyConfig.group.add(ctx.channel());
		System.out.println("客户端与服务端  连接 开启.......");
		
	}

	/**
	 * 客户端与服务端 断开 连接时调用
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		NettyConfig.group.remove(ctx.channel());
		System.out.println("客户端与服务端  断开连接 !!!!!!!!!!!!!!");
	}

	/**
	 * 服务端接收客户端发送过来的数据结束之后调用
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		
		ctx.flush();
		
		System.out.println("服务端接收客户端发送过来的数据结束 !!");
	}

	/**
	 * 工程出现异常时调用
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		System.out.println(" 工程出现异常 !");
		ctx.close();
		
	}
	
	/**
	 * 服务端处理客户端websocket请求的核心方法
	 */
	@Override
	protected void messageReceived(ChannelHandlerContext context, Object msg)
			throws Exception {
		//处理客户端向服务端 发起http 握手请求的业务 
		if(msg instanceof FullHttpRequest){
			
			handHttpRequest(context, (FullHttpRequest)msg);
			
		}else if(msg instanceof WebSocketFrame){//处理websocket连接业务
			
			handWebsocketFrame(context, (WebSocketFrame)msg);
		}
	}
	
	/**
	 * 处理客户端与服务器之前的  websocket业务
	 * @param ctx
	 * @param frame
	 */
	private void handWebsocketFrame(ChannelHandlerContext ctx,WebSocketFrame frame){
		
		//判断是否是 关闭websocket 的指令
		if(frame instanceof CloseWebSocketFrame){
			handshaker.close(ctx.channel(), (CloseWebSocketFrame)frame.retain());
		}
		
		//判断是否是ping 消息
		if(frame instanceof PingWebSocketFrame){
			ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		
		//判断是否为二进制消息， 如果是二进制消息，抛出异常
		if(!(frame instanceof TextWebSocketFrame)){
			System.out.println(" 不支持二进制消息");
			throw new RuntimeException("["+this.getClass().getName()+"]不支持二进制消息");
		}
		
		//返回应答消息
		TextWebSocketFrame textFrame=((TextWebSocketFrame)frame);
		String requestStr = textFrame.text();
		
		System.out.println("服务端收到客户端的消息======>>>>"+requestStr);
		TextWebSocketFrame tws= new TextWebSocketFrame(new Date().toString()
																+ctx.channel().id()
																+"==>>"
																+requestStr);
		
		//群发  服务端向每个连接上来的客户端端群发消息
		NettyConfig.group.writeAndFlush(tws);
	}
	
	
	/**
	 * 处理客户端向服务端发起http 握手请求的业务
	 * @param context
	 * @param req
	 */
	private void handHttpRequest(ChannelHandlerContext context,FullHttpRequest req){
		if(!req.getDecoderResult().isSuccess() || !("websocket".equals(req.headers().get("Upgrade")))){
			sendHttpResponse(context, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
					HttpResponseStatus.BAD_REQUEST));
			
			return;
		}
		
		WebSocketServerHandshakerFactory wsfactory=new WebSocketServerHandshakerFactory(
				WEB_SOCKET_URL, null, false);
		
		handshaker = wsfactory.newHandshaker(req);
		if(handshaker == null){
			WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(context.channel());
		}else{
			handshaker.handshake(context.channel(), req);
		}

	}
	
	/**
	 * 服务端向客户端响应消息
	 * @param context
	 * @param req
	 * @param res
	 */
	private void sendHttpResponse(ChannelHandlerContext ctx,FullHttpRequest req,
			DefaultFullHttpResponse res){
		
		if(res.getStatus().code()!=200){
			ByteBuf buf= Unpooled.copiedBuffer(res.getStatus().toString(),CharsetUtil.UTF_8);
			
			res.content().writeBytes(buf);
			buf.release();
		}
		
		//服务端向客户端发送数据
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		
		if(res.getStatus().code()!=200){
			f.addListener(ChannelFutureListener.CLOSE);
		}
		
	}
	

}
