<%@ include file="/common/taglibs.jsp"%>

<c:choose>
	<c:when test="${isPurchaseMode == true}">
	    <c:set var="title" scope="page" value="menu.trx.po.title"/>
	</c:when>
	<c:otherwise>
	    <c:set var="title" scope="page" value="menu.trx.so.title"/>
	</c:otherwise>
</c:choose>

<head>
    <title><fmt:message key="${title}"/></title>
    <meta name="heading" content="<fmt:message key='${title}'/>"/>
    
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

</head>

<p>

    <object id="PurchaseOrder" align="middle"
    codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0" 
    classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000">
    <param name="allowScriptAccess" value="sameDomain" />
    <param name="quality" value="high" />
    <param name="bgcolor" value="#ffffff" />
    <param name="pluginspage" value="http://www.macromedia.com/go/getflashplayer" />
    <embed src="/flash/PurchaseOrderScreen.swf" 
    width="690" height="550" align="middle"
    type="application/x-shockwave-flash"/>
    </object>

</p>






