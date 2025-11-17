package IKT222.Assignment4;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppServer {

    public static void main(String[] args) throws Exception {

        // HTTPS configuration
        HttpConfiguration httpsConfig = new HttpConfiguration();
        httpsConfig.addCustomizer(new SecureRequestCustomizer());

        // SSL context
        SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
        sslContextFactory.setKeyStorePath("keystore.jks");
        sslContextFactory.setKeyStorePassword("password");
        sslContextFactory.setKeyManagerPassword("password");

        // HTTPS connector
        Server httpsServer = new Server();
        ServerConnector httpsConnector = new ServerConnector(
                httpsServer,
                new SslConnectionFactory(sslContextFactory, "http/1.1"),
                new HttpConnectionFactory(httpsConfig)
        );
        httpsConnector.setPort(8443);
        httpsServer.addConnector(httpsConnector);

        // Web app handler
        ServletContextHandler context = new ServletContextHandler();
        context.addServlet(new ServletHolder(new AppServlet()), "/*");
        httpsServer.setHandler(context);

// HTTP server for redirect
Server httpServer = new Server(8080);
httpServer.setHandler(new org.eclipse.jetty.server.handler.AbstractHandler() {
    @Override
    public void handle(String target,
                       org.eclipse.jetty.server.Request baseRequest,
                       javax.servlet.http.HttpServletRequest request,
                       javax.servlet.http.HttpServletResponse response)
            throws java.io.IOException, javax.servlet.ServletException {

        String redirectURL = "https://localhost:8443" + target;
        response.setStatus(javax.servlet.http.HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", redirectURL);
        baseRequest.setHandled(true);
    }
});


        // Start both
        httpServer.start();
        httpsServer.start();

        System.out.println("HTTP redirector running on http://localhost:8080");
        System.out.println("Secure server running on https://localhost:8443");

        httpsServer.join();
        httpServer.join();
    }
}
