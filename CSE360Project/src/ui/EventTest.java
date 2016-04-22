package ui;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class EventTest {
	
	@Test
	public void testGetEventInstance() throws IOException {
		ArrayList<Event> eventContainer = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			eventContainer.add(Event.getEventInstance());
		}
		while(eventContainer.remove(null)); //remove all nulls
		assertEquals(Event.getMAX_ALLOWED_INSTANCES(), eventContainer.size()); //check if we have exactly the limited amount, we always go over the amount in the for loop
	}

}
