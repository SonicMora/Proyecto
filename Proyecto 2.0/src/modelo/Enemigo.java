package modelo;

import java.io.Serializable;

/**
 * Clase Enemigo
 * @author Victor Mora
 *
 */
@SuppressWarnings("serial")
public class Enemigo implements Serializable{

	private int posX;
	private int posY;
	
	private int puntos;
	
	private char direccion;
	
	private String imagen;

	private boolean vivo;
	
	private Enemigo siguiente;
	
	/**
	 * Constructor de la clase Enemigo
	 * @param posX != null, posX posicion x del Enemigo
	 * @param posY != null, posY posicion y del Enemigo
	 * @param puntos != null, puntos puntos que da el Enemigo cuando es golpeado
	 * @param imagen != null, imagen ruta de la imagen que tendrá en la interfaz el Enemigo
	 * @param vivo != null, vivo booleano de confirmacion para saber si el Enemigo aun esta vivo o no
	 * @param direccion != null, direccion char que indica la direccion de movimiento del Enemigo
	 */
	public Enemigo(int posX, int posY, int puntos, String imagen, boolean vivo, char direccion) {
		this.posX = posX;
		this.posY = posY;
		this.puntos = puntos;
		this.imagen = imagen;
		this.vivo = vivo;
		this.direccion=direccion;
	}

	/**
	 * @return the posX
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * @param posX the posX to set
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * @return the posY
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * @param posY the posY to set
	 */
	public void setPosY(int posY) {
		this.posY = posY;
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
	 * @return the direccion
	 */
	public char getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(char direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the imagen
	 */
	public String getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	/**
	 * @return the vivo
	 */
	public boolean isVivo() {
		return vivo;
	}

	/**
	 * @param vivo the vivo to set
	 */
	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}

	/**
	 * @return the siguiente
	 */
	public Enemigo getSiguiente() {
		return siguiente;
	}

	/**
	 * @param siguiente the siguiente to set
	 */
	public void setSiguiente(Enemigo siguiente) {
		this.siguiente = siguiente;
	}

	public void moverD() {
		posX+=5;
	}
	
	public void moverI() {
		posX-=5;
	}
	
}
