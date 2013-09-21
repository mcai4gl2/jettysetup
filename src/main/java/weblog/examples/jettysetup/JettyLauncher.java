package weblog.examples.jettysetup;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class JettyLauncher {
    private static Logger log = Logger.getLogger(JettyLauncher.class);

    public static void main(String[] args) throws Exception {
        try {
            DOMConfigurator.configure(Thread.currentThread().getContextClassLoader().getResource("log4j.xml"));

            Server server = new Server();

            SelectChannelConnector connector = new SelectChannelConnector();
            connector.setPort(7411);
            server.setConnectors(new Connector[] {connector});

            DispatcherServlet servlet = new DispatcherServlet();
            servlet.setContextConfigLocation("classpath:servlet-context.xml");

            ServletContextHandler context = new ServletContextHandler();
            context.setContextPath("/");
            context.addServlet(new ServletHolder("baseServlet", servlet), "/");

            HandlerCollection handlers = new HandlerCollection();
            handlers.setHandlers(new Handler[] { context, new DefaultHandler()});
            server.setHandler(handlers);

            XmlWebApplicationContext wctx = new XmlWebApplicationContext();
            wctx.setConfigLocation("");
            wctx.setServletContext(servlet.getServletContext());
            wctx.refresh();

            context.setAttribute(XmlWebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, wctx);
            server.start();

            log.info("Jetty embedded server started");

            log.info("Press any key to stop");
            System.in.read();
            log.info("Stopping Server");
            server.stop();
            log.info("Server stopped");

        } catch (Exception ex) {
            log.error("Failed to run Jetty Server", ex);
            throw ex;
        }
    }
}
