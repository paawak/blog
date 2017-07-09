package com.swayam.demo.reactive.reactor3.react.xml;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import com.swayam.demo.reactive.reactor3.model.LineItemRow;
import com.swayam.demo.reactive.reactor3.xml.JaxbUnmarshaller;

public class JaxbUnmarshallerTest {

    @Test
    public void testUnmarshall() {

	// given
	JaxbUnmarshaller jaxbUnmarshaller = new JaxbUnmarshaller();

	// when
	LineItemRow lineItem = jaxbUnmarshaller.unmarshall(JaxbUnmarshallerTest.class.getResourceAsStream("/datasets/xml/www.cs.washington.edu/single-lineitem-row-sample.xml"), LineItemRow.class);

	// then
	assertEquals(1, lineItem.getOrderKey());
	assertEquals(1552, lineItem.getPartKey());
	assertEquals(93, lineItem.getSupplierKey());
	assertEquals(1, lineItem.getLineNumber());
	assertEquals(17, lineItem.getQuantity());

	assertEquals(24710.35F, lineItem.getExtendedPrice(), 0);
	assertEquals(0.04F, lineItem.getDiscount(), 0);
	assertEquals(0.02F, lineItem.getTax(), 0);

	assertEquals("N", lineItem.getReturnFlag());
	assertEquals("O", lineItem.getLineStatus());

	assertEquals(LocalDate.of(1996, 3, 13), lineItem.getShippingDate());
	assertEquals(LocalDate.of(1996, 2, 12), lineItem.getCommitDate());
	assertEquals(LocalDate.of(1996, 3, 22), lineItem.getReceiptDate());

	assertEquals("DELIVER IN PERSON", lineItem.getShippingInstructions());
	assertEquals("TRUCK", lineItem.getShipMode());
	assertEquals("blithely regular ideas caj", lineItem.getComment());

    }

}
