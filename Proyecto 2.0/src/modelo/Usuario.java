package modelo;

import java.io.Serializable;

import excepciones.UsuarioRepetidoException;

/**
 * Clase Usuario 
 * @author Victor Mora
 *
 */
@SuppressWarnings("serial")
public class Usuario implements Serializable, Comparable<Usuario>{
	
	private Personaje avatar;
	
	private int vidas;
	private int puntos;
	private int nivel;
	
	private String nombre;	

	private Usuario derecho;
	private Usuario izquierdo;
	
	/**
	 * Constructor de la clase Usuario
	 * @param nombre != null
	 */
	public Usuario(String nombre) {
		avatar=new Personaje(Personaje.IZQ);
		this.vidas = 3;
		this.puntos = 0;
		this.nivel = 1;
		this.nombre = nombre;
		
		derecho=null;
		izquierdo=null;
	}
	
	/**
	 * Metodo recursivo que inserta un nuevo usuario al arbol binario de busqueda 
	 * @param nuevo != null
	 * @throws UsuarioRepetidoException si encuentra un usuario con el mismo nombre que nuevo.getNombre()
	 */
	public void insertar(Usuario nuevo) throws UsuarioRepetidoException {
    	if(this.nombre.compareTo(nuevo.nombre)>0) {
    		if(this.izquierdo==null) {
    			this.izquierdo=nuevo;
    		}else {
    			this.izquierdo.insertar(nuevo);
    		}
    	}else if(this.nombre.compareTo(nuevo.nombre)<0) {
    		if(this.derecho==null) {
    			this.derecho=nuevo;
    		}else {
    			this.derecho.insertar(nuevo);
    		}
    	}else if(this.nombre.compareTo(nuevo.nombre)==0) {
    		throw new UsuarioRepetidoException(nuevo.nombre);
    	}
	}
	
	/**
	 * @return the avatar
	 */
	public Personaje getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(Personaje avatar) {
		this.avatar = avatar;
	}

	/**
	 * @return the vidas
	 */
	public int getVidas() {
		return vidas;
	}

	/**
	 * @param vidas the vidas to set
	 */
	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	/**
	 * @return the puntos
	 */
	public int getPuntos() {
		return puntos;
	}

	/**
	 * @param puntos the puntos to set
	 */
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	/**
	 * @return the nivel
	 */
	public int getNivel() {
		return nivel;
	}

	/**
	 * @param nivel the nivel to set
	 */
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo que hace perder una vida al usuario
	 * y acto seguido lo hace reaparecer en una posicion por defecto
	 */
	public void perderVida() {
		this.setVidas(this.getVidas()-1);
		avatar.reaparecer();
	}
	
	/**
	 * @param o != null 
	 * @return 1 si el usuario actul tiene mas puntos que el parametro
	 * @return -1 si el usuario actual tiene menos puntos que el parametro 
	 * @return 0 si el usuario actual y el parametro tienen los mismos puntos 
	 */
	@Override
	public int compareTo(Usuario o) {
		if(this.getPuntos()<o.getPuntos()) {
			return 1;
		}else if(this.getPuntos()>o.getPuntos()){
			return -1;
		}else {
			return 0;	
		}
	}

	/**
	 * Acciona el metodo moverDerecha() del avatar
	 */
	public void moverDerecha() {
		avatar.moverDerecha();
	}
	
	/**
	 * Acciona el metodo moverIzquierda() del avatar
	 */
	public void moverIzquierda() {
		avatar.moverIzquierda();
	}
	
	/**
	 * @return the derecho
	 */
	public Usuario getDerecho() {
		return derecho;
	}

	/**
	 * @param derecho the derecho to set
	 */
	public void setDerecho(Usuario derecho) {
		this.derecho = derecho;
	}

	/**
	 * @return the izquierdo
	 */
	public Usuario getIzquierdo() {
		return izquierdo;
	}

	/**
	 * @param izquierdo the izquierdo to set
	 */
	public void setIzquierdo(Usuario izquierdo) {
		this.izquierdo = izquierdo;
	}

	/**
	 * Metodo que verifica si un Usuario es hoja
	 * @return true si el Usuario no tiene hijos, false si el usuario tiene al menos un hijo
	 */
	public boolean isHoja() {
		return (getDerecho()==null && getIzquierdo()==null);
	}
	
	/**
	 * Metodo para eliminr a un usuario del arbol binario de busqueda
	 * @param unNombre !=null
	 * @return Usuario nuevo en la raiz
	 */
	public Usuario eliminar(String unNombre) {
		if(isHoja()) {
			return null;
		}
		if(unNombre.equalsIgnoreCase(nombre)) {
			if(izquierdo==null)
				return derecho;
			if(derecho==null)
				return izquierdo;
			Usuario suce=derecho.darMenor();
			derecho = derecho.eliminar(suce.nombre);
			suce.izquierdo=izquierdo;
			suce.derecho=derecho;
			return suce;
		}
		else if(nombre.compareToIgnoreCase(unNombre)>0) {
			izquierdo=izquierdo.eliminar(unNombre);
		}else {
			if(derecho!=null)
				derecho=derecho.eliminar(unNombre);
		}
		return this;
	}
	
	/**
	 * Metodo que retorna el Usuario mas a la izquierda de un Usuario dado
	 * @return Usuario mas a la izquierda de un Usuario dado
	 */
	public Usuario darMenor() {
        return (izquierdo==null) ? this : izquierdo.darMenor();
    }
	
	@Override
	public String toString() {
		return getNombre()+" "+getPuntos();
	}
}
