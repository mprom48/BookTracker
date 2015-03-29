package bookTracker.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class NewsItemDAO extends DataAccessObject {

	public NewsItem find(Long id) { 
		ResultSet rs = null; 
		PreparedStatement stmt = null; 
		Connection conn = null; 
		
		try { 
			conn = getConnection(); 
			String sql = "SELECT * FROM news_item WHERE id=?"; 
			stmt = conn.prepareStatement(sql); 
			stmt.setLong(1, id.longValue()); 
			rs = stmt.executeQuery(); 
			if(!rs.next()) { 
				return null; 
			}
			return read(rs); 
		}
		catch (SQLException e) {
			throw new RuntimeException(e); 
		}
		finally { 
			close(rs, stmt, conn); 
		}
	}
	
	private NewsItem read(ResultSet rs) throws SQLException { 
		Long id = new Long(rs.getLong("id")); 
		String title = rs.getString("title"); 
		String url = rs.getString("url"); 
		NewsItem newsItem = new NewsItem(); 
		newsItem.setId(id); 
		newsItem.setTitle(title); 
		newsItem.setURL(url);
		return newsItem; 
	}
	
	public List<NewsItem> findAll() { 
		LinkedList<NewsItem> newsItems = new LinkedList<NewsItem>(); 
		ResultSet rs = null; 
		PreparedStatement stmt = null; 
		Connection conn = null; 
		
		try { 
			conn = getConnection(); 
			String sql = "SELECT * FROM news_item ORDER BY id"; 
			stmt = conn.prepareStatement(sql); 
			rs = stmt.executeQuery(); 
			while(rs.next()) { 
				NewsItem newsItem = read(rs); 
				newsItems.add(newsItem); 
			}
			return newsItems; 
		}
		catch (SQLException e) { 
			throw new RuntimeException(e); 
		}
		finally { 
			close(rs, stmt, conn); 
		}
	}
}
