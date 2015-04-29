<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="menu.masters.party.title"/></title>
    <meta name="heading" content="<fmt:message key='menu.masters.party.title'/>"/>
</head>

<spring:bind path="party.*">
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

<form:form commandName="party" method="post">
<ul>
    <li>
        <appfuse:label styleClass="desc" key="party.name"/>
        <form:errors path="name" cssClass="fieldError"/>
        <form:input path="name" id="name" cssClass="text large" cssErrorClass="text large error"/>
    </li>
    <li>
        <appfuse:label styleClass="desc" key="party.desc"/>
        <form:errors path="description" cssClass="fieldError"/>
        <form:input path="description" id="description" cssClass="text medium" cssErrorClass="text medium error" />
    </li>
    <li>
            <appfuse:label styleClass="desc" key="party.type" />
            <form:errors path="type" cssClass="fieldError" />
            <form:select path="type"
                id="item" cssClass="text large"
                cssErrorClass="text large error">
                <option value="-1">------Select One------</option>

                <c:forEach var="partyType" items="${partyTypeList}">
                    <option value="${partyType.id}"
                        <c:if test="${partyType.id == party.type.id}">
                                                selected="true"
                                            </c:if>>${partyType.name}</option>
                </c:forEach>
            </form:select>
    </li>
    <li>
            <appfuse:label styleClass="desc" key="party.accountGroup" />
            <form:errors path="accountGroup" cssClass="fieldError" />
            <form:select path="accountGroup"
                id="item" cssClass="text large"
                cssErrorClass="text large error">
                <option value="-1">------Select One------</option>

                <c:forEach var="accountGroup" items="${accountGroupList}">
                    <option value="${accountGroup.id}"
                        <c:if test="${accountGroup.id == party.accountGroup.id}">
                                                selected="true"
                                            </c:if>>${accountGroup.name}</option>
                </c:forEach>
            </form:select>
    </li>
    <li>
        <div>
            <div class="left">
                <appfuse:label styleClass="desc" key="user.email"/>
                <form:errors path="address.email" cssClass="fieldError"/>
                <form:input path="address.email" id="email" cssClass="text medium" cssErrorClass="text medium error"/>
            </div>
            <div>
                <appfuse:label styleClass="desc" key="user.phoneNumber"/>
                <form:errors path="address.phoneNumber" cssClass="fieldError"/>
                <form:input path="address.phoneNumber" id="phoneNumber" cssClass="text medium" cssErrorClass="text medium error"/>
            </div>
        </div>
    </li>
    
    <li>
        <label class="desc"><fmt:message key="user.address.address"/></label>
        <div class="group">
            <div>
                <form:input path="address.address" id="address.address" cssClass="text large" cssErrorClass="text large error"/>
                <form:errors path="address.address" cssClass="fieldError"/>
                <p><appfuse:label key="user.address.address"/></p>
            </div>
            <div class="left">
                <form:input path="address.city" id="address.city" cssClass="text medium" cssErrorClass="text medium error"/>
                <form:errors path="address.city" cssClass="fieldError"/>
                <p><appfuse:label key="user.address.city"/></p>
            </div>
            <div>
                <form:input path="address.province" id="address.province" cssClass="text state" cssErrorClass="text state error"/>
                <form:errors path="address.province" cssClass="fieldError"/>
                <p><appfuse:label key="user.address.province"/></p>
            </div>
            <div class="left">
                <form:input path="address.postalCode" id="address.postalCode" cssClass="text medium" cssErrorClass="text medium error"/>
                <form:errors path="address.postalCode" cssClass="fieldError"/>
                <p><appfuse:label key="user.address.postalCode"/></p>
            </div>
            <div>
                <appfuse:country name="address.country" prompt="" default="${user.address.country}"/>
                <p><appfuse:label key="user.address.country"/></p>
            </div>
        </div>
    </li>
    <li class="buttonBar bottom">
        <input type="submit" class="button" name="save" onclick="bCancel=false" value="<fmt:message key="button.save"/>"/>
        <input type="submit" class="button" name="cancel" onclick="bCancel=true" value="<fmt:message key="button.cancel"/>"/>
    </li>
</ul>
</form:form>




