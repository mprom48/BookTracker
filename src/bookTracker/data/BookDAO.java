package bookTracker.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BookDAO extends DataAccessObject {
	
	public Book find(Long id) { 
		ResultSet rs = null; 
		PreparedStatement stmt = null; 
		Connection conn = null; 
		
		try { 
			conn = getConnection(); 
			String sql = "SELECT * FROM books WHERE id=?"; 
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
	
	private Book read(ResultSet rs) throws SQLException { 
		Long id = new Long(rs.getLong("BookID"));
		String title = rs.getString("title"); 
		Book book = new Book(); 
		book.setId(id);
		book.setTitle(title);
		return book; 
	}
	
	public List<Book> findAll() { 
		LinkedList<Book> books = new LinkedList<Book>(); 
		ResultSet rs = null; 
		PreparedStatement stmt = null; 
		Connection conn = null; 
		
		try { 
			conn = getConnection(); 
			String sql = "SELECT * FROM books ORDER by bookID"; 
			stmt = conn.prepareStatement(sql); 
			rs = stmt.executeQuery(); 
			while(rs.next()) { 
				Book book = read(rs);
				books.add(book); 
			}
			return books; 
		}
		catch(SQLException e) { 
			throw new RuntimeException(e); 
		}
		finally { 
			close(rs, stmt, conn); 
		}
	}
}
