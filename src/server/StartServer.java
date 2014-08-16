package server;


import db.ConnectionFactory;
import db.DBManager;
import db.PGConnectionFactory;
import db.entity.UserRecord;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;



public class StartServer {




    public static void main(String args[]) throws UnknownHostException {

        ExecutorService bossExec = new OrderedMemoryAwareThreadPoolExecutor(1,
                400000000, 2000000000, 60, TimeUnit.SECONDS);
        ExecutorService ioExec = new OrderedMemoryAwareThreadPoolExecutor(4,
                400000000, 2000000000, 60, TimeUnit.SECONDS);

        ChannelFactory factory = new NioServerSocketChannelFactory(bossExec,ioExec,4);

        ServerBootstrap networkServer = new ServerBootstrap(factory);

        networkServer.setOption("backlog", 500);
        networkServer.setOption("connectTimeoutMillis", 10000);
        networkServer.setOption("child.tcpNoDelay", true);
        networkServer.setOption("child.keepAlive", true);
        networkServer.setOption("readWriteFair", true);
        networkServer.setPipelineFactory(new ServerPipelineFactory());
        Channel channel = networkServer.bind(new InetSocketAddress("xx.xx.xxx.xx", 29999));


        System.err.println("Server started : " + InetAddress.getLocalHost().toString());
    }


}
