<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="menu.masters.item.title" /></title>
<meta name="heading"
	content="<fmt:message key='menu.masters.item.title'/>" />
<meta name="menu" content="ListItems" />
</head>

<c:set var="buttons">
    <input type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/masters/new_item.html"/>'"
        value="<fmt:message key="button.add"/>"/>

    <input type="button" onclick="location.href='<c:url value="/mainMenu.html"/>'"
        value="<fmt:message key="button.done"/>"/>
</c:set>

<c:out value="${buttons}" escapeXml="false" />

<display:table name="itemList" cellspacing="0" cellpadding="0" requestURI="" 
    defaultsort="1" id="items" pagesize="25" class="table" export="true">
    <display:column property="name" escapeXml="true" sortable="true" titleKey="item.name" style="width: 25%"
        url="/masters/new_item.html" paramId="id" paramProperty="id"/>
    <display:column property="code" escapeXml="true" sortable="true" titleKey="item.code" style="width: 34%"/>
    <display:column property="itemGroup.name" sortable="true" titleKey="item.itemGroup" style="width: 25%" autolink="true" media="html"/>

    <display:setProperty name="paging.banner.item_name" value="item"/>
    <display:setProperty name="paging.banner.items_name" value="items"/>

    <display:setProperty name="export.excel.filename" value="ItemList.xls"/>
    <display:setProperty name="export.csv.filename" value="ItemList.csv"/>
    <display:setProperty name="export.pdf.filename" value="ItemList.pdf"/>
</display:table>

<c:out value="${buttons}" escapeXml="false" />

<script type="text/javascript">
    highlightTableRows("users");
</script>


