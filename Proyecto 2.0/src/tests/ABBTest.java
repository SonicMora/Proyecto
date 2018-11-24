package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import excepciones.UsuarioRepetidoException;
import modelo.ABBUsuario;
import modelo.Usuario;

class ABBTest {

	private ABBUsuario arbol;
	
	void stage1() {
		arbol=new ABBUsuario();
	}
	
	void stage2() {
		stage1();
		
		try {
			arbol.add(arbol.getRaiz(), new Usuario("Marta"));
			arbol.add(arbol.getRaiz(), new Usuario("Armando"));
			arbol.add(arbol.getRaiz(), new Usuario("Patricio"));
		}catch(Exception e) {
			
		}
		
	}
	
	@Test
	void addTest() {
		stage1();
		try {
			arbol.add(arbol.getRaiz(), new Usuario("Marta"));
			arbol.add(arbol.getRaiz(), new Usuario("Armando"));
			arbol.add(arbol.getRaiz(), new Usuario("Patricio"));
		}catch(Exception e) {
		
		}
		assertEquals("Marta", arbol.getRaiz().getNombre());
		assertEquals("Armando", arbol.getRaiz().getIzquierdo().getNombre());
		assertEquals("Patricio", arbol.getRaiz().getDerecho().getNombre());
	}
	
	@Test
	void addExceptionTest() {
		stage2();

		try {
			arbol.add(arbol.getRaiz(), new Usuario("Marta"));
		}catch(Exception e) {
			assertTrue("Ya existe un usuario con ese nombre, debe lanzarse la excepicon", true);
		}
	}

	@Test
	void eliminarTest() {
		stage2();
		
		arbol.eliminar("Armando");
		
		assertNull(arbol.getRaiz().getIzquierdo());
		
		arbol.eliminar("Marta");
		
		assertEquals("Patricio", arbol.getRaiz().getNombre());
	}
	
	@Test
	void buscarTest() {
		stage2();
		
		try {
			assertEquals(arbol.getRaiz().getIzquierdo(), arbol.buscar(arbol.getRaiz(), "Armando"));
		} catch (UsuarioRepetidoException e) {
			fail("No se encontró el usuario");
		}
	}
	
	@Test
	void buscarExceptionTest() {
		stage2();
		try {
			arbol.buscar(arbol.getRaiz(), "Paco");
		} catch (UsuarioRepetidoException e) {
			assertTrue("El usuario no existe, debía generarse la excepcion", true);
		}
	}
	
}
