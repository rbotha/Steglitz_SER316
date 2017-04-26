package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import java.util.Date;

import nu.xom.Element;
import nu.xom.Attribute;

import org.junit.Test;

import net.sf.memoranda.TaskImpl;
import net.sf.memoranda.util.Util;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskImplTest.
 */
public class TaskImplTest {
	
	/**
	 * Test Getters and Setters for added task attributes.
	 */
	@Test
	public void testGettersSetters() {
		TaskImpl task;
		TaskImpl emptyTask;
		Element content;
		
		Element el = new Element("task");
        el.addAttribute(new Attribute("startDate", new Date().toString()));
        el.addAttribute(new Attribute("endDate", ""));
		String id = Util.generateId();
        el.addAttribute(new Attribute("id", id));
        el.addAttribute(new Attribute("progress", "0"));
        el.addAttribute(new Attribute("effort", "1"));
        el.addAttribute(new Attribute("actualEffort", "2"));
		el.addAttribute(new Attribute("timestamp", "-1")); // -1 implies that there is no timestamp
        el.addAttribute(new Attribute("priority", "2")); // PRIORITY_NORMAL
        el.addAttribute(new Attribute("errorsAdded", "3"));
        el.addAttribute(new Attribute("errorsFixed", "4"));
        el.addAttribute(new Attribute("estloc", "5"));
        el.addAttribute(new Attribute("actloc", "6"));
        Element txt = new Element("text");
        txt.appendChild("Test text");
        el.appendChild(txt);
        Element desc = new Element("description");
        desc.appendChild("Test description");
        el.appendChild(desc); 
        
		task = new TaskImpl(el, null);
		emptyTask = new TaskImpl(new Element("task"), null);
		
		content = task.getContent();
		
		assertEquals((long)1.0, task.getEffort() );
		task.setEffort((long)10);
		assertEquals((long)10, task.getEffort() );		
		content.addAttribute(new Attribute("effort", "not a long"));
		assertEquals((long)0, task.getEffort() );// Check for non-long attr
		assertEquals((long)0, emptyTask.getEffort());// Check for missing attr
		
		assertEquals((long)2.0, task.getActualEffort());
		task.setActualEffort((long)20);
		assertEquals((long)20, task.getActualEffort());
		content.addAttribute(new Attribute("actualEffort", "not a long"));
		assertEquals((long)0, task.getActualEffort() );
		assertEquals((long)0, emptyTask.getActualEffort());
		
		assertEquals((long)-1, task.getTimestamp());
		task.setTimestamp((long)-10);
		assertEquals((long)-10, task.getTimestamp());
		content.addAttribute(new Attribute("timestamp", "not a long"));
		assertEquals((long)-1, task.getTimestamp() );
		assertEquals((long)-1, emptyTask.getTimestamp());
		
		assertEquals(3, task.getErrorsAdded());
		task.setErrorsAdded(30);
		assertEquals(30, task.getErrorsAdded());
		content.addAttribute(new Attribute("errorsAdded", "not an int"));
		assertEquals((int)0, task.getErrorsAdded() );
		assertEquals((int)0, emptyTask.getErrorsAdded());
		
		assertEquals(4, task.getErrorsFixed());
		task.setErrorsFixed(40);
		assertEquals(40, task.getErrorsFixed());
		content.addAttribute(new Attribute("errorsFixed", "not an int"));
		assertEquals((int)0, task.getErrorsFixed() );
		assertEquals((int)0, emptyTask.getErrorsFixed());
		
		assertEquals(5, task.getEstLOC());
		task.setEstLOC(50);
		assertEquals(50, task.getEstLOC());
		content.addAttribute(new Attribute("estloc", "not an int"));
		assertEquals((int)0, task.getEstLOC() );
		assertEquals((int)0, emptyTask.getEstLOC());
		
		assertEquals(6, task.getActLOC());
		task.setActLOC(60);
		assertEquals(60, task.getActLOC());	
		content.addAttribute(new Attribute("actloc", "not an int"));
		assertEquals((int)0, task.getActLOC() );
		assertEquals((int)0, emptyTask.getActLOC());
	}

}
