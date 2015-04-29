<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="menu.masters.item.title" /></title>
<meta name="heading"
	content="<fmt:message key='menu.masters.item.title'/>" />
<meta name="menu" content="NewItem" />
</head>

<spring:bind path="item.*">
	<c:if test="${not empty status.errorMessages}">
		<div class="error"><c:forEach var="error"
			items="${status.errorMessages}">
			<img src="<c:url value="/images/iconWarning.gif"/>"
				alt="<fmt:message key="icon.warning"/>" class="icon" />
			<c:out value="${error}" escapeXml="false" />
			<br />
		</c:forEach></div>
	</c:if>
</spring:bind>

<form:form commandName="item" method="post">

	<ul>
	   <li>
			<appfuse:label styleClass="desc" key="item.code" />
			<form:input path="code" id="code" cssClass="text medium" cssErrorClass="text medium error" maxlength="50" />
		</li>

        <li>
            <appfuse:label styleClass="desc" key="item.name" />
            <form:errors path="name" cssClass="fieldError" />
            <form:input path="name" id="name" cssClass="text medium" cssErrorClass="text medium error" maxlength="50" />
        </li>

		<li>
			<appfuse:label styleClass="desc" key="item.description" />
			<form:input path="description" id="description" cssClass="text medium" cssErrorClass="text medium error" maxlength="100" />
		</li>

		<li>
			<appfuse:label styleClass="desc" key="item.itemGroup" />
			<form:errors path="itemGroup" cssClass="fieldError" />
			<form:select path="itemGroup"
				id="itemGroup" cssClass="text large"
				cssErrorClass="text large error">
				<option value="-1">------Select One------</option>

				<c:forEach var="itemGroup" items="${itemGroupList}">
					<option value="${itemGroup.id}"
						<c:if test="${itemGroup.id == item.itemGroup.id}">
						                        selected="true"
						                    </c:if>>${itemGroup.name}</option>
				</c:forEach>
			</form:select>
		</li>

        <li>
            <appfuse:label styleClass="desc" key="item.currency" />
            <form:errors path="currency" cssClass="fieldError" />
            <form:select path="currency"
                id="currency" cssClass="text large"
                cssErrorClass="text large error">
                <option value="-1">------Select One------</option>

                <c:forEach var="currency" items="${currencyList}">
                    <option value="${currency.id}"
                        <c:if test="${currency.id == item.currency.id}">
                                                selected="true"
                                            </c:if>>${currency.name}</option>
                </c:forEach>
            </form:select>
        </li>

        <li>
            <appfuse:label styleClass="desc" key="item.unit" />
            <form:errors path="unit" cssClass="fieldError" />
            <form:select path="unit"
                id="unit" cssClass="text large"
                cssErrorClass="text large error">
                <option value="-1">------Select One------</option>

                <c:forEach var="unit" items="${unitList}">
                    <option value="${unit.id}"
                        <c:if test="${unit.id == item.unit.id}">
                                                selected="true"
                                            </c:if>>${unit.name}</option>
                </c:forEach>
            </form:select>
        </li>
        
        <li>
            <appfuse:label styleClass="desc" key="item.sellingPrice" />
            <form:errors path="sellingPrice" cssClass="fieldError" />
            <form:input path="sellingPrice" id="sellingPrice" cssClass="text medium" cssErrorClass="text medium error" maxlength="50"/>
        </li>

		<li>
			<div>
			<table width="100%" cellpadding="0" cellspacing="0" border="0"
				style="border-collapse: collapse">
				<tr>
					<td><img src="<c:url value="/images/botLeft.gif"/>" /></td>
					<td align="right" width="90%"
						background="<c:url value="/images/botCenter.gif"/>"><input
						type="submit" class="button" name="save"
						value="<fmt:message key="button.save"/>" /> <input type="submit"
						class="button" name="cancel"
						value="<fmt:message key="button.cancel"/>" /></td>
					<td><img src="<c:url value="/images/botRight.gif"/>" /></td>
				</tr>
			</table>
			</div>
		</li>
	</ul>
</form:form>
