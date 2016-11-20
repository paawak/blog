<%-- 
    Document   : index
    Created on : Aug 21, 2010, 8:01:16 PM
    Author     : paawak
--%>

<%@page import="java.util.Optional"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="com.swayam.eardemo.shared.api.MySessionBeanRemote"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.LocalDate"%>
<%@page import="com.swayam.eardemo.shared.model.Person"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
</head>
<body>
<%
	String firstName = request.getParameter("firstName");
	String lastName = request.getParameter("lastName");
	Optional<Integer> personId = Optional.empty();
    if ((firstName != null) && (lastName != null)) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        // FIXME: get it from servlet
        person.setDateOfBirth(LocalDate.of(1955, 6, 18));
        
        // String contextName = "java:global/swayam-ear/swayam-ejb/MySessionBean";
        String contextName = "java:app/swayam-ejb/MySessionBean";
        
        MySessionBeanRemote remoteBean = InitialContext.doLookup(contextName);

        personId = Optional.of(remoteBean.savePerson(person));
    }	
%>
	<h1>This illustrates a JSP invoking of an EJB</h1>
<%
	if (personId.isPresent()){
%>
	<h2>Saved FirstName: <%=firstName%> and LastName: <%=lastName%>, got back: <%=personId.get()%></h2>
<%	    	    
	}
%>	
	<form method='post'>
		<div>
			<div>
				FirstName: <input type='text' name='firstName' />
			</div>
			<div>
				LastName: <input type='text' name='lastName' />
			</div>
			<div>
				<input type='submit' name='submit' value='Submit' />
			</div>
		</div>
	</form>
</body>
</html>
