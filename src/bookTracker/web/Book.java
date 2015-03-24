package bookTracker.web;

public class Book {
	String title;
	Book book; 
	
	public Book(String t){ 
		title = t; 
	}
	
	public Book() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	} 
	
}
