package modelo;

import java.io.Serializable;

import interfaces.InterfaceDisparar;
import interfaces.InterfaceReloadP;

/**
 * Clase Personaje
 * @author Victor Mora
 *
 */
@SuppressWarnings("serial")
public class Personaje implements Serializable, InterfaceReloadP, InterfaceDisparar {

	public static final String DER="data/usuarioD.png";
	public static final String IZQ="data/usuarioI.png";
	
	private String imagen;
		
	private int largo;
	private int ancho;
	
	private int posX;
	private int posY;
	
	private boolean disparando;
	
	private Disparo bala;
	
	/**
	 * Constructor de la clase Presonaje
	 * @param imagen != null
	 */
	public Personaje(String imagen) {
		this.imagen=imagen;
		
		posX=(Juego.ANCHO_VENTANA/2)-getAncho();
		posY=(Juego.ALTO_VENTANA-100);
		
		largo=90;
		ancho=60;
		
		this.disparando = false;
		bala=new Disparo(0);
		reloadP();
	}
	
	public void reloadP() {
		if(bala.getY()<5) {
			disparando=false;
			bala.reloadP();
		}
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
	 * @return the disparando
	 */
	public boolean isDisparando() {
		return disparando;
	}

	/**
	 * @param disparando the disparando to set
	 */
	public void setDisparando(boolean disparando) {
		this.disparando = disparando;
	}

	/**
	 * @return the der
	 */
	public static String getDer() {
		return DER;
	}

	/**
	 * @return the izq
	 */
	public static String getIzq() {
		return IZQ;
	}
	
	/**
	 * @return the bala
	 */
	public Disparo getBala() {
		return bala;
	}

	/**
	 * @param bala the bala to set
	 */
	public void setBala(Disparo bala) {
		this.bala = bala;
	}

	/**
	 * Metodo que hace reaparecer al avatar en una posicion por defecto
	 */
	public void reaparecer() {
		this.setPosX(Juego.ANCHO_VENTANA/2);
	}
	
	/**
	 * Metodo que aumenta la posicion x del avatar 5 puntos
	 */
	public void moverDerecha() {
		setImagen(DER);
		if(getPosX()<Juego.ANCHO_VENTANA) {
			posX+=5;
		}
	}
	
	/**
	 * Metodo que disminuye la posicion x del avatar 5 puntos
	 */
	public void moverIzquierda() {
		setImagen(IZQ);
		if(getPosX()<=Juego.ANCHO_VENTANA) {
			posX-=5;
		}
	}
	
	/**
	 * Metodo que hace disparar al personaje
	 */
	@Override
	public void disparar() {
		if(isDisparando()==true) {
			bala.moverP();
		}
	}
	
	
}
