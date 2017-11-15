package beauj.workshop05.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppInitializer implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		byte[] secret = null;

		try {
			secret = "opensasamee".getBytes("utf-8");
			sce.getServletContext().setAttribute("secret", secret);
		} catch (UnsupportedEncodingException ex) { }
	}


	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
