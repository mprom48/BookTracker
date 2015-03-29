package bookTracker.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class DataAccessObject {
	private static DataSource dataSource; 
	private static Object idLock = new Object(); 
	
	public static void setDataSource(DataSource dataSource) {
		DataAccessObject.dataSource = dataSource; 
	}
		
	protected static Connection getConnection() { 
		try { 
			return dataSource.getConnection(); 
		}
		catch (SQLException e) { 
			throw new RuntimeException(e); 
		}
	}
	
	protected static void close(Statement stmt, Connection conn) { 
		close(null, stmt, conn); 
	}

	protected static void close(ResultSet rs, Statement stmt, Connection conn) {
		try { 
			if (rs != null)
				rs.close(); 
			if(stmt != null) 
				stmt.close(); 
			if(conn != null)
				conn.close(); 
		}
		catch (SQLException e){ 
			throw new RuntimeException(e); 
		}
	}
	
	protected static Long getID() { 
		ResultSet rs = null; 
		PreparedStatement stmt = null; 
		Connection conn = null; 
		
		try { 
			conn = getConnection(); 
			synchronized(idLock){ 
				stmt = conn.prepareStatement("SELECT NEXT VALUE FROM sequence"); 
				rs = stmt.executeQuery(); 
				rs.first(); 
				long id = rs.getLong(1); 
				stmt.close();
				
				stmt = conn.prepareStatement("UPDATE sequence SET NEXT_VALUE = ?"); 
				stmt.setLong(1, id + 1);
				stmt.executeUpdate(); 
				stmt.close(); 
				return new Long(id); 
			}
		}
		catch (SQLException e) { 
			throw new RuntimeException(e); 
		}
		finally { 
			close(rs, stmt, conn); 
		}
	}
}