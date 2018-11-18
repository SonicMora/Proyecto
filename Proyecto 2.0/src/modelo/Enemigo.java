package modelo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Enemigo implements Serializable{

	private int posX;
	private int posY;
	
	private int puntos;
	
	private char direccion;
	
	private String imagen;

	private boolean vivo;
	
	private Enemigo siguiente;
	
	public Enemigo(int posX, int posY, int puntos, String imagen, boolean vivo, char direccion) {
		this.posX = posX;
		this.posY = posY;
		this.puntos = puntos;
		this.imagen = imagen;
		this.vivo = vivo;
		this.direccion=direccion;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	public boolean isVivo() {
		return vivo;
	}

	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}

	public void moverD() {
		posX+=5;
	}
	
	public void moverI() {
		posX-=5;
	}

	public char getDireccion() {
		return direccion;
	}

	public void setDireccion(char direccion) {
		this.direccion = direccion;
	}

	public Enemigo getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(Enemigo siguiente) {
		this.siguiente = siguiente;
	}
}
