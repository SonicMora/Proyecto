package modelo;

import java.io.Serializable;

import excepciones.UsuarioRepetidoException;

@SuppressWarnings("serial")
public class ABBUsuario implements Serializable {
	
	private Usuario raiz;
	
	public ABBUsuario() {
		raiz=null;
	}
	
	public void add(Usuario actual, Usuario nuevo) throws UsuarioRepetidoException {
		if(raiz==null) {
			raiz=nuevo;
		}else {
			raiz.insertar(nuevo);
		}
	}

	public Usuario buscar(Usuario actual, String nombre) throws UsuarioRepetidoException {
		if(actual.getNombre()==nombre) {
			return actual;
		}else {
			if(nombre.compareToIgnoreCase(actual.getNombre())<1) {
				if(actual.getIzquierdo()!=null) {
					return buscar(actual.getIzquierdo(), nombre);
				}else {
					throw new UsuarioRepetidoException(nombre);
				}
			}else {
				if(actual.getDerecho()!=null) {
					return buscar(actual.getDerecho(), nombre);
				}else {
					throw new UsuarioRepetidoException(nombre);
				}
			}
		}
	}
	
	public Usuario getRaiz() {
		return raiz;
	}

	public void setRaiz(Usuario raiz) {
		this.raiz = raiz;
	}
	
	public void eliminar(String nombre) {
		raiz=raiz.eliminar(nombre);
	}
}
