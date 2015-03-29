package bookTracker.web;

import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import bookTracker.data.NewsItem;
import bookTracker.data.NewsItemDAO;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

public class NewsFeedServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		SyndFeed feed = new SyndFeedImpl(); 
		feed.setFeedType("rss_2.0"); 
		feed.setTitle("Local News Feed"); 
		feed.setLink("http://localhost:8080/BookTracker"); 
		feed.setDescription("This feed was created using ROME"); 
		List<SyndEntry> entries = new ArrayList<SyndEntry>(); 
		
		List<NewsItem> newsItems = new NewsItemDAO().findAll(); 
		Iterator<NewsItem> it = newsItems.iterator(); 
		
		while(it.hasNext()) { 
			NewsItem newsItem = (NewsItem) it.next(); 
			String title = newsItem.getTitle(); 
			String url = newsItem.getURL(); 
			SyndEntry entry = new SyndEntryImpl(); 
			entry.setTitle(title);
			entry.setLink(url);
			entries.add(entry); 
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
