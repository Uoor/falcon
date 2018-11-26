package com.falcon.util.analysis.jetty;

import com.falcon.server.jetty.ApiDocJettyServlet;
import com.falcon.server.jetty.ApiInvokerJettyServlet;
import com.falcon.server.netty.Server;
import com.google.common.collect.Lists;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.DispatcherType;
import java.net.UnknownHostException;
import java.util.EnumSet;

/**
 * Created by fanshuai on 18/9/27.
 */
public class JettyServer implements Server {
    private org.eclipse.jetty.server.Server server;
    private ServletContextHandler context;
    private Object serverStartingLock = new Object();
    public JettyServer(int port, String servletContext){
        server = new org.eclipse.jetty.server.Server(port);
        context = new ServletContextHandler(server, servletContext);
        server.setHandler(context);
    }
    @Override
    public boolean isStarted() {
        if (server==null){
            return false;
        }
        return server.isStarted();
    }

    @Override
    public void start() throws UnknownHostException {
        if (isStarted()) {
            return;
        }
        synchronized (serverStartingLock) {
            if (isStarted()) {
                return;
            }
            try {
                server.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            while (!server.isStarted()) {
                try {
                    Thread.sleep(100l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        context.addServlet(ApiInvokerHttpServlet.class, "/api/invoke");
        context.addServlet(ApiDocJettyServlet.class, "/api/doc");
        context.addFilter(JettyDefaultFilter.class, "/*", EnumSet.copyOf(Lists.newArrayList(DispatcherType.ASYNC)));
    }

    @Override
    public Object doRequest(Object param) {
        return null;
    }
}