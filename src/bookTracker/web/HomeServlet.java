package bookTracker.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class HomeServlet extends HttpServlet {
	private RequestDispatcher homeJsp; 
	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		homeJsp = context.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		URL url = new URL("http://feeds2.feedburner.com/thechildrensbookreview"); 
		SyndFeedInput syndFeedInput = new SyndFeedInput(); 
		SyndFeed syndFeed = null; 
		XmlReader xmlReader = new XmlReader(url); 
		try {
			syndFeed = syndFeedInput.build(xmlReader);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FeedException e) {
			e.printStackTrace();
		} 
		req.setAttribute("syndFeed", syndFeed); 
		homeJsp.forward(req, resp);
		
	}
	
}
