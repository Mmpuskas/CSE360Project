package ui;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class EventTest {
	
	@Test
	public void testGetEventInstance() throws IOException {
		Event.resetInstances();
		ArrayList<Event> eventContainer = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			eventContainer.add(Event.getEventInstance());
		}
		while(eventContainer.remove(null)); //remove all nulls
		assertEquals(Event.getMAX_ALLOWED_INSTANCES(), eventContainer.size()); //check if we have exactly the limited amount of instances, we always go over the amount in the for loop
		//Event.getMAX_ALLOWED_INSTANCES() currently returns 5 
	}
	
	@Test
	public void testEquals1() throws IOException{
		Event.resetInstances();
		Event origEvent = Event.getEventInstance();
		Event dupEvent = new Event(origEvent);
		assertTrue(origEvent.equals(dupEvent.dummyEvent));
		
	}
	
	@Test
	public void testEquals2() throws IOException{
		Event.resetInstances();
		Event origEvent = Event.getEventInstance();
		Event dupEvent = new Event(origEvent);
		origEvent.forceEqualsFail(); //changes the value of one of the instance variables i use to compare
		assertFalse(origEvent.equals(dupEvent.dummyEvent));
		
	}
	
	@Test
	public void testGetAftermath() throws IOException{
		Event.resetInstances();
		Event test = Event.getEventInstance();
		
	}

}
