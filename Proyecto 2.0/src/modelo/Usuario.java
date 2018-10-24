package modelo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Usuario implements Serializable, Comparable<Usuario>{
	
	private Personaje avatar;
	
	private int vidas;
	private int puntos;
	private int nivel;
	
	private String nombre;	

	private Usuario siguiente;
	
	public Usuario(String nombre) {
		avatar=new Personaje(Personaje.IZQ);
		this.vidas = 3;
		this.puntos = 0;
		this.nivel = 0;
		this.nombre = nombre;
		
		siguiente=null;
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

	public Usuario getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(Usuario siguiente) {
		this.siguiente = siguiente;
	}
}
