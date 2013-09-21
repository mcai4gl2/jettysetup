package weblog.examples.jettysetup.serlvet;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class TestServlet {

    private static Logger log = Logger.getLogger(TestServlet.class);

    @RequestMapping(value="/ping", method = RequestMethod.GET)
    public void ping(HttpServletResponse response) throws IOException {
        log.info("ping page is called");
        IOUtils.write("Embedded Jetty Server is Up and Running", response.getOutputStream());
    }
}
