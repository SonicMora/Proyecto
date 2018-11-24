package tests;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
		lista.add(new Enemigo(50, 50, 20, "Ñero", true, 'D'));
		lista.add(new Enemigo(50, 50, 20, "Ñera", true, 'D'));
		lista.add(new Enemigo(50, 50, 20, "Ñeru", true, 'D'));
	}
	
	@Test
	void clearTest() {
		stage2();
		lista.clear();
		assertNull(lista.getCabeza());
	}
	
	@Test
	void getTest() {
		
	}
	
//	@Test
//	public void getTest() {
//		stage2();
//		assertEquals("Ñera", lista.get(2, 1, lista.getCabeza()).getImagen());
//	}

	
	@Test
	void test() {
		
	}
}
