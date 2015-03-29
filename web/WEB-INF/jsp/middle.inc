	</div>
	<div class="container">
	<h3><%=syndFeed.getTitle()%></h3>
	<ul>
		<%
		Iterator it = syndFeed.getEntries().iterator(); 
		while(it.hasNext())
		{ 
			SyndEntry entry = (SyndEntry) it.next(); 
		%>
		<li>
			<a href="<%=entry.getLink()%>" target="blank">
			<%=entry.getTitle() %></a>
		</li>
		<% } %>
	</ul>