<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="menu.masters.accountGroup.title"/></title>
    <meta name="heading" content="<fmt:message key='menu.masters.accountGroup.title'/>"/>
</head>

<spring:bind path="accountGroup.*">
    <c:if test="${not empty status.errorMessages}">
    <div class="error">    
        <c:forEach var="error" items="${status.errorMessages}">
            <img src="<c:url value="/images/iconWarning.gif"/>"
                alt="<fmt:message key="icon.warning"/>" class="icon" />
            <c:out value="${error}" escapeXml="false"/><br />
        </c:forEach>
    </div>
    </c:if>
</spring:bind>

<div class="separator"></div>

<form:form commandName="accountGroup" method="post">
<ul>
    <li>
        <appfuse:label styleClass="desc" key="accountGroup.name"/>
        <form:errors path="name" cssClass="fieldError"/>
        <form:input path="name" id="name" cssClass="text large" cssErrorClass="text large error"/>
    </li>
    <li>
        <appfuse:label styleClass="desc" key="accountGroup.desc"/>
        <form:errors path="description" cssClass="fieldError"/>
        <form:input path="description" id="description" cssClass="text medium" cssErrorClass="text medium error" />
    </li>
    <li>
            <appfuse:label styleClass="desc" key="accountGroup.parent" />
            <form:errors path="parent" cssClass="fieldError" />
            <form:select path="parent"
                id="item" cssClass="text large"
                cssErrorClass="text large error">
                <option value="-1">------Select One------</option>

                <c:forEach var="parentGroup" items="${accountGroupList}">
                    <option value="${parentGroup.id}"
                        <c:if test="${parentGroup.id == accountGroup.parent.id}">
                                                selected="true"
                                            </c:if>>${parentGroup.name}</option>
                </c:forEach>
            </form:select>
    </li>
    
    <li class="buttonBar bottom">
        <input type="submit" class="button" name="save" onclick="bCancel=false" value="<fmt:message key="button.save"/>"/>
        <input type="submit" class="button" name="cancel" onclick="bCancel=true" value="<fmt:message key="button.cancel"/>"/>
    </li>
</ul>
</form:form>




