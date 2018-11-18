package modelo;

import java.io.Serializable;

import excepciones.UsuarioNoExisteException;

@SuppressWarnings("serial")
public class ABBUsuario implements Serializable {
	
private Usuario raiz;
	
	public ABBUsuario() {
		raiz=null;
	}
	
	public void add(Usuario actual, Usuario nuevo) {
		if(raiz==null) {
			raiz=nuevo;
		}else {
			if(nuevo.getNombre().compareToIgnoreCase(actual.getNombre())<1) {
				if(actual.getIzquierdo()==null) {
					actual.setIzquierdo(nuevo);
				}else {
					add(actual.getIzquierdo(), nuevo);
				}
			}else {
				if(actual.getDerecho()==null) {
					actual.setDerecho(nuevo);
				}else {
					add(actual.getDerecho(), nuevo);
				}
			}
		}
	}

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
	
	public Usuario getRaiz() {
		return raiz;
	}

	public void setRaiz(Usuario raiz) {
		this.raiz = raiz;
	}
	
}
