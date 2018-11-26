package modelo;

import java.io.Serializable;

import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRepetidoException;

/**
 * Clase ABBUsuario
 * @author Victor Mora
 *
 */
@SuppressWarnings("serial")
public class ABBUsuario implements Serializable {
	
	private Usuario raiz;
	
	public ABBUsuario() {
		raiz=null;
	}
	
	/**
	 * Metodo que agrega un nuevo usuario al arbol binario de busqueda si la raiz es null
	 * en caso contrario llama al metodo recursivo de la clase Usuario insertar(Usuario)  
	 * @param actual != null
	 * @param nuevo != null, nuevo metodo a agregar
	 * @throws UsuarioRepetidoException Si en el metodo insertar(Usuario) de la clase Usuario se encuentra
	 * un usuario registrado con el mismo nombre que el de nuevo.getNombre()
	 */
	public void add(Usuario actual, Usuario nuevo) throws UsuarioRepetidoException {
		if(raiz==null) {
			raiz=nuevo;
		}else {
			raiz.insertar(nuevo);
		}
	}

	/**
	 * Metodo recursivo que busca un usuario en el arbol binario de busqueda 
	 * @param actual !=null, usuario actual del recorridodel arbol
	 * @param nombre nombre del usuario que desea buscar
	 * @return El usuario buscado
	 * @throws UsuarioNoExisteException si no se encuentra ningun usuario con el nombre ingresado por parametro
	 */
	public Usuario buscar(Usuario actual, String nombre) throws UsuarioNoExisteException {
		if(actual.getNombre()==nombre) {
			return actual;
		}else {
			if(nombre.compareToIgnoreCase(actual.getNombre())<1) {
				if(actual.getIzquierdo()!=null) {
					return buscar(actual.getIzquierdo(), nombre);
				}else {
					throw new UsuarioNoExisteException(nombre);
				}
			}else {
				if(actual.getDerecho()!=null) {
					return buscar(actual.getDerecho(), nombre);
				}else {
					throw new UsuarioNoExisteException(nombre);
				}
			}
		}
	}
	
	/**
	 * @return the raiz
	 */
	public Usuario getRaiz() {
		return raiz;
	}

	/**
	 * @param raiz the raiz to set
	 */
	public void setRaiz(Usuario raiz) {
		this.raiz = raiz;
	}

	/**
	 * Metodo que llama al metodo recursivo eliminar(String) de la clase usuario
	 * @param nombre != null
	 */
	public void eliminar(String nombre) {
		raiz=raiz.eliminar(nombre);
	}
}
