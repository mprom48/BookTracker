package bookTracker.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookListServlet extends HttpServlet {

	private RequestDispatcher booklistJsp; 
	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		booklistJsp = context.getRequestDispatcher("/WEB-INF/jsp/booklist.jsp");
		
		try{ 
			Class.forName("com.mysql.jdbc.Driver"); 
		}
		catch (ClassNotFoundException e){
			throw new ServletException(e); 
		}
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Book> books = new ArrayList<Book>(); 
		
		try{ 
			Connection conn = DriverManager.getConnection(
					"jdbc.mysql://localhost/bookTracker", "root", "");
			Statement stmt = conn.createStatement(); 
			ResultSet rsBooks = stmt.executeQuery("SELECT title FROM books"); 
			while(rsBooks.next()) { 
				String title = rsBooks.getString("title"); 
				Book book = new Book(); 
				book.setTitle(title); 
				books.add(book);
				PrintWriter out = resp.getWriter();
				out.printf("%s", title);
			}
			conn.close(); 
		} catch(SQLException e) { 
			throw new ServletException(e); 
		}	
	}

}
