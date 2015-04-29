<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="menu.masters.itemgroup.title" /></title>
<meta name="heading"
	content="<fmt:message key='menu.masters.itemgroup.title'/>" />
<meta name="menu" content="NewItemGroup" />
</head>

<spring:bind path="itemGroup.*">
    <c:if test="${not empty status.errorMessages}">
    <div class="error">
        <c:forEach var="error" items="${status.errorMessages}">
            <img src="<c:url value="/images/iconWarning.gif"/>"
                alt="<fmt:message key="icon.warning"/>" class="icon"/>
            <c:out value="${error}" escapeXml="false"/><br />
        </c:forEach>
    </div>
    </c:if>
</spring:bind>

<form:form commandName="itemGroup" method="post">
		
		<div align = "center">
		 <!-- #D7E9E5 -->
		<table bgcolor = "#EBF5FC" width = "100%" cellpadding = "2" cellspacing = "2" border = "0" 
				style = "border-collapse:collapse;border-color:blue;">
			<tr><td height = "20"></td></tr>
			<tr><td width = "2%"></td>
				<td align = "left">
	            	<appfuse:label styleClass="desc" key="itemgroup.name"/>
	            </td>
	            <td align = "left">
		            <form:errors path="name" cssClass="fieldError"/>
		            <form:input path="name" id="name" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
        		</td>
        		</tr>
        		
        		<tr><td width = "2%">
        			<td align = "left">
	            		<appfuse:label styleClass="desc" key="generic.description"/>
	             	</td>
	            	<td align = "left">
	            		<form:input path="description" id="description" cssClass="text medium" cssErrorClass="text medium error" maxlength="100"/>
        			</td>
        		</tr>
        		
        		<tr><td width = "2%">
        			<td align = "left">
	            		<appfuse:label styleClass="desc" key="itemgroup.parent"/>
	             	</td>
	            	<td align = "left">
						        <form:select path="parentGroup" id="parentGroup" cssClass="text large" cssErrorClass="text large error">
						        	<option value="-1">------Select One------</option>
						        	
						            <c:forEach var="parent" items="${itemGroupParents}">
						                <option value="${parent.id}"
						                    <c:if test="${parent.id == itemGroup.parentGroup.id}">
						                        selected="true"
						                    </c:if>
						                >${parent.name}</option>
						            </c:forEach>
						        </form:select>
        			</td>
        		</tr>        	
        		        		
	            <tr>
	            	<td colspan = "3">
	            		<table width = "100%" cellpadding = "0" cellspacing = "0" border = "0" style = "border-collapse:collapse">
	            			<tr>
	            				<td>
	            					<img src="<c:url value="/images/botLeft.gif"/>"/>
	            				</td>
	            				<td align = "right" width = "90%" background = "<c:url value="/images/botCenter.gif"/>">
	            					<input type="submit" class="button" name="save" value="<fmt:message key="button.save"/>"/>
        							<input type="submit" class="button" name="cancel" value="<fmt:message key="button.cancel"/>"/>
	            				</td>
	            				<td>
	            					<img src="<c:url value="/images/botRight.gif"/>"/>
	            				</td>
	            			</tr>
	            		</table>
	            	</td>
	            </tr>	            
        	</table>
        	</div>
</form:form>
