package com.swayam.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class BadPrecedentClientTest {

    @Test
    public void testGetNamesOfActiveItems() {
	// given
	BadPrecedentService badPrecedentService = mock(BadPrecedentService.class);

	doAnswer(new Answer<Object>() {
	    public Object answer(InvocationOnMock invocation) {
		List<Item> items = (List<Item>) invocation.getArguments()[0];
		items.add(new Item("abc", true));
		items.add(new Item("def", false));
		items.add(new Item("ghi", true));
		items.add(new Item("jkl", false));
		items.add(new Item("xyz", true));
		return null;
	    }
	}).when(badPrecedentService).setThePassedInList(any(List.class));

	BadPrecedentClient testClass = new BadPrecedentClient(badPrecedentService);

	// when
	List<String> result = testClass.getNamesOfActiveItems();

	// then
	assertEquals(Arrays.asList("abc", "ghi", "xyz"), result);
    }

}
