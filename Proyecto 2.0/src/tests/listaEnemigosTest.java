package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
//import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import modelo.Enemigo;
import modelo.ListaEnemigo;

class listaEnemigosTest {

	private ListaEnemigo lista;
	
	public void stage1() {
		lista=new ListaEnemigo();
	}
	
	public void stage2() {
		lista=new ListaEnemigo();
		lista.add(new Enemigo(50, 50, 20, "Arnold", true, 'D'));
		lista.add(new Enemigo(50, 50, 20, "Claro", true, 'D'));
		lista.add(new Enemigo(50, 50, 20, "Helio", true, 'D'));
	}
	
	@Test
	void clearTest() {
		stage2();
		lista.clear();
		assertNull(lista.getCabeza());
	}
	
	@Test
	void getTest() {
		stage2();
		assertEquals(lista.getCabeza(), lista.get(0));
	}
	
	@Test
	void addTest() {
		stage1();
		Enemigo uno=new Enemigo(50, 50, 20, "Arnold", true, 'D');
		Enemigo dos=new Enemigo(50, 50, 20, "Crill", true, 'D');
		Enemigo tres=new Enemigo(50, 50, 20, "Quirrel", true, 'D');
		
		lista.add(uno);
		lista.add(dos);
		lista.add(tres);
		
		assertEquals(uno, lista.getCabeza());
		assertEquals(dos, lista.get(1));
		assertEquals(tres, lista.get(2));
		assertEquals(3, lista.size());
	}
}
