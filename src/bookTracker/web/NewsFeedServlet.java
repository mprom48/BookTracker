package bookTracker.web;

import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

public class NewsFeedServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		try{ 
			Class.forName("com.mysql.jdbc.Driver"); 
		}
		catch(ClassNotFoundException e){
			throw new ServletException(e); 
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		SyndFeed feed = new SyndFeedImpl(); 
		feed.setFeedType("rss_2.0"); 
		feed.setTitle("Local News Feed"); 
		feed.setLink("http://localhost:8080/BookTracker"); 
		feed.setDescription("This feed was created using ROME"); 
		List<SyndEntry> entries = new ArrayList<SyndEntry>(); 
		
		try{ 
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/publisher", "root", ""); 
			Statement stmt = conn.createStatement(); 
			ResultSet rsEntries = stmt.executeQuery("SELECT * FROM news_item"); 
			while(rsEntries.next()) { 
				String title = rsEntries.getString("title"); 
				String url = rsEntries.getString("url"); 
				SyndEntry entry = new SyndEntryImpl(); 
				entry.setTitle(title); 
				entry.setLink(url);
				entries.add(entry); 
			}
		}
		catch (SQLException e) { 
			throw new ServletException(e); 
		}
		
		resp.setContentType("text/xml"); 
		feed.setEntries(entries); 
		Writer writer = resp.getWriter(); 
		SyndFeedOutput out = new SyndFeedOutput(); 
		try { 
			out.output(feed, writer); 
		}
		catch (FeedException e) { 
			e.printStackTrace(); 
		}
		
	}
	

	
}
