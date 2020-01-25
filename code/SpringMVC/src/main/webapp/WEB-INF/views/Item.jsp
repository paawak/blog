<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Item</title>
		<script type="text/javascript" src="js/item.js"></script>
	</head>
	<body>
		<c:if test="${ not empty errors.allErrors }"> 
		<div style="text-align:center; color:red;">
			<ul>
				<c:forEach var="error" items="${errors.allErrors}">
					<li><spring:message code="${error.code}"  text="${error.defaultMessage}"/></li>
				</c:forEach>
			</ul>
		</div>
		</c:if>
		<form:form modelAttribute="command" method="post" action="checkout.htm">
			<div>
				<table style="text-align: left; width: 100%;" border="0" cellpadding="2"
				cellspacing="2">
					<tbody>
						<tr>
							<th style="vertical-align:top;">Select</th>
							<th style="vertical-align:top;">Item Name</th>
							<th style="vertical-align:top;">Unit Price</th>
							<th style="vertical-align:top;">Quantity</th>
							<th style="vertical-align:top;">Price</th>
						</tr>
						<c:forEach var="item" items="${command.items}" varStatus="count">
						<tr>
							<td style="vertical-align:middle;">
								<form:checkbox id="chkSelect" path="items[${count.index}].selected" 
								onchange="itemSelected(chkSelect[${count.index}], txtItemPrice, txtQuantity, ${count.index}, ${item.price}, txtTotalPrice);"/>
							</td>
							<td style="vertical-align:middle; color:green;">
								<c:out value="${item.itemName}"/>
							</td>
							<td style="vertical-align:middle; color:blue;">
								<input id="txtUnitPrice" type="text" disabled="disabled" value="${item.price}" size="4"/>
							</td>
							<td style="vertical-align:middle;">								
								<form:input id="txtQuantity" path="items[${count.index}].quantity" size="3" disabled="${!item.selected}"
								onblur="updatePrice(txtItemPrice, txtQuantity, ${count.index}, ${item.price}, txtTotalPrice);"/>
							</td>
							<td style="vertical-align:middle; color:blue;">
								<input id="txtItemPrice" type="text" readonly="readonly" value="${item.price * item.quantity}" size="6"/>
							</td>
						</tr>
						</c:forEach>
						<tr>
							<td style="height:30px;">
							</td>
						</tr>
						<tr>
							<td style="vertical-align:middle; text-align:right; font-weight: bold;" colspan="3">
								Total Price:
							</td>
							<td style="vertical-align:middle; text-align:center; color:blue;">
								<form:input id="txtTotalPrice" path="totalPrice" readonly="true" size="6" />
							</td>
						</tr>
						<tr>
							<td style="vertical-align:middle; text-align:right; font-weight: bold;" colspan="3">
								Expected Delivery:
							</td>
							<td style="vertical-align:middle; text-align:center; color:blue;">
								<form:input id="expectedDelivery" path="expectedDelivery" size="12" maxlength="10" />
							</td>
						</tr>
						<tr>
							<td style="vertical-align:middle; text-align:left; font-weight: bold;" colspan="5">
								<input id="submit" type="submit" value="Submit"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form:form>
	
	</body>
</html>