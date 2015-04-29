<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Checkout</title>
	</head>
	<body>
	
		<form:form commandName="command" method="post" action="checkout.htm">
			<div>
				<table style="text-align: left; width: 100%;" border="0" cellpadding="2"
				cellspacing="2">
					<tbody>
						<tr>
							<th style="vertical-align:top;">Sl.</th>
							<th style="vertical-align:top;">Item Name</th>
							<th style="vertical-align:top;">Unit Price</th>
							<th style="vertical-align:top;">Quantity</th>
							<th style="vertical-align:top;">Price</th>
						</tr>
						<c:set var="loopCount" value="1"/>
						<c:forEach var="item" items="${command.items}">
						<c:if test="${item.quantity > 0}">
						<tr>
							<td style="vertical-align:middle;">
								<c:out value="${loopCount}"/>.&gt;
							</td>
							<td style="vertical-align:middle; color:green;">
								<c:out value="${item.itemName}"/>
							</td>
							<td style="vertical-align:middle; color:blue;">
								<c:out value="${item.price}"/>
							</td>
							<td style="vertical-align:middle;">
								<c:out value="${item.quantity}"/>
							</td>
							<td style="vertical-align:middle; color:blue;">
								<c:out value="${item.price * item.quantity}"/>
							</td>
						</tr>
						<c:set var="loopCount" value="${loopCount + 1}"/>
						</c:if>
						</c:forEach>
						<tr>
							<td style="height:30px;">
							</td>
						</tr>
						<tr>
							<td style="vertical-align:middle; text-align:right; font-weight: bold;" colspan="3">
								Total Price:
							</td>
							<td style="vertical-align:middle; text-align:center; font-weight: bold; color:red;">
								<c:out value="${command.totalPrice}"/>
							</td>
						</tr>
						<tr>
							<td style="vertical-align:middle; text-align:right; font-weight: bold;" colspan="3">
								Expected Delivery:
							</td>
							<td style="vertical-align:middle; text-align:center; color:blue;">
								<c:out value="${command.expectedDelivery}"/>
							</td>
						</tr>
						<tr>
							<td style="vertical-align:middle; text-align:left; font-weight: bold;" colspan="5">
								<a href="#">Proceed to payment</a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form:form>
	
	</body>
</html>