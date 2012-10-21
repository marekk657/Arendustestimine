package test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import ise1.Calculator;

import org.junit.Test;

public class TestCalculator {

	@Test
	public void Calculator_NewInstance() {
		Calculator c = new Calculator(); 
		assertThat(c.getScore(), is(0));
	}

	@Test
	public void Calculator_20Shots20Points() {
		Calculator c = new Calculator(); 
		for (int i = 0; i < 20; i++) {
			c.hit(1);
		}
		assertThat(c.getScore(), is(20));
	}
	
	@Test
	public void Calculator_20Shots40Points() {
		Calculator c = new Calculator(); 
		for (int i = 0; i < 20; i++) { 
			c.hit(2);
		}
		assertThat(c.getScore(), is(40));
	}
	
	@Test
	public void Calculator_20Shots150Points() {
		Calculator c = new Calculator(); 
		for (int i = 0; i < 21; i++) { 
			c.hit(5);
		}
		assertThat(c.getScore(), is(150));
	}
	
	@Test
	public void Calculator_AllStrikes() {
		Calculator c = new Calculator(); 
		for (int i = 0; i < 21; i++) { // 
			c.hit(10);
		}
		assertThat(c.getScore(), is(290));
	}
	
	@Test
	public void Calculator_TestGame() {
		Calculator c = new Calculator(); 
		
		// first frame
		c.hit(3);
		c.hit(5);
		
		// second frame
		c.hit(5);
		c.hit(5);
		
		c.hit(2);
		c.hit(5);
		
		c.hit(10);
		
		c.hit(2);
		c.hit(8);
		
		c.hit(10);
		
		c.hit(5);
		c.hit(2);
		
		c.hit(7);
		c.hit(1);
		
		c.hit(3);
		c.hit(7);
		
		c.hit(10);
		c.hit(7);
		c.hit(3);
		
		assertThat(c.getScore(), is(139));
	}
}
