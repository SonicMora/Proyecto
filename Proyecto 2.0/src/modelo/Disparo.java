package modelo;

import java.io.Serializable;

import interfaces.InterfaceReloadE;
import interfaces.InterfaceReloadP;

/**
 * Clase Disparo
 * @author Victor Mora
 *
 */
@SuppressWarnings("serial")
public class Disparo implements Serializable, InterfaceReloadP, InterfaceReloadE{

	private int largo;
	private int ancho;
	
	private int x;
	private int y;
	
	private int damage;
	
	/**
	 * Constructor de la clase Disparo
	 * @param x != null, posicion x del disparo
	 */
	public Disparo(int x) {
		largo=50;
		ancho=20;
		y=Juego.ALTO_VENTANA-165;
		setDamage(250);
	}
	
	/**
	 * @return the largo
	 */
	public int getLargo() {
		return largo;
	}

	/**
	 * @param largo the largo to set
	 */
	public void setLargo(int largo) {
		this.largo = largo;
	}

	/**
	 * @return the ancho
	 */
	public int getAncho() {
		return ancho;
	}

	/**
	 * @param ancho the ancho to set
	 */
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * @param damage the damage to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * Metodo que setea la posicion y del disparo del enemigo en una posicion por defecto
	 */
	@Override
	public void reloadE() {
		setY(35);
	}
	
	/**
	 * Metodo que setea la posicion y del disparo del personaje en una posicion por defecto
	 */
	@Override
	public void reloadP() {
		setY(Juego.ALTO_VENTANA-165);
	}
	
	/**
	 * Metodo que cambia la posicion del disparo -50 puntos
	 */
	public void moverP() {
		y-=50;
	}

	/**
	 * Metodo que cambia la posicion del disparo +50 pixeles
	 */
	public void moverE() {
		y+=50;
	}
	
}
