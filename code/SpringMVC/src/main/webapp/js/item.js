function updatePrice(txtItemPriceArray, txtQuantityArray, arrayIndex, unitPrice, txtTotalPrice) {
	
	txtItemPriceArray[arrayIndex].value = txtQuantityArray[arrayIndex].value * unitPrice;
	
	var totalPrice = 0;
	
	for (var index = 0; index < txtItemPriceArray.length; index++) {
		
		totalPrice += parseFloat(txtItemPriceArray[index].value);
		
	}
	
	txtTotalPrice.value = totalPrice;
	
}

function itemSelected(chkSelect, txtItemPriceArray, txtQuantityArray, arrayIndex, unitPrice, txtTotalPrice) {
	
	txtQuantityArray[arrayIndex].value = 0;
	txtQuantityArray[arrayIndex].disabled = !chkSelect.checked;
	
	updatePrice(txtItemPriceArray, txtQuantityArray, arrayIndex, unitPrice, txtTotalPrice);
	
}

