package modelo;

import java.io.Serializable;

import excepciones.UsuarioRepetidoException;

@SuppressWarnings("serial")
public class Usuario implements Serializable, Comparable<Usuario>{
	
	private Personaje avatar;
	
	private int vidas;
	private int puntos;
	private int nivel;
	
	private String nombre;	

	private Usuario derecho;
	private Usuario izquierdo;
	
	public Usuario(String nombre) {
		avatar=new Personaje(Personaje.IZQ);
		this.vidas = 3;
		this.puntos = 0;
		this.nivel = 1;
		this.nombre = nombre;
		
		derecho=null;
		izquierdo=null;
	}
	
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
	
	public Personaje getAvatar() {
		return avatar;
	}

	public void setAvatar(Personaje avatar) {
		this.avatar = avatar;
	}

	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void perderVida() {
		this.setVidas(this.getVidas()-1);
		avatar.reaparecer();
	}
	
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

	public void moverDerecha() {
		avatar.moverDerecha();
	}
	
	public void moverIzquierda() {
		avatar.moverIzquierda();
	}

	public Usuario getDerecho() {
		return derecho;
	}

	public void setDerecho(Usuario siguiente) {
		this.derecho = siguiente;
	}

	public Usuario getIzquierdo() {
		return izquierdo;
	}

	public void setIzquierdo(Usuario izquierdo) {
		this.izquierdo = izquierdo;
	}
	
	public boolean isHoja() {
		return (getDerecho()==null && getIzquierdo()==null);
	}
	
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
	
	public Usuario darMenor() {
        return (izquierdo==null) ? this : izquierdo.darMenor();
    }
	
	@Override
	public String toString() {
		return getNombre()+" "+getPuntos();
	}
}
