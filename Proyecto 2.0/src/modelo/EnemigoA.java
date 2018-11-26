package modelo;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import interfaces.InterfaceDisparar;
import interfaces.InterfaceReloadE;

/**
 * Clase Enemio 
 * @author Victor Mora
 *
 */
public class EnemigoA extends Enemigo implements Serializable, InterfaceReloadE, InterfaceDisparar {

	private static final long serialVersionUID = 1L;

	private Timer timer;
	
	private TimerTask tT;
	
	private Disparo shoot;

	private boolean disparando;
	
	private long delay;
	
	private long period;
	
	/**
	 * Constructor de la clase EnemigoA 
	 * @param posX != null, posX posicion x del EnemigoA
	 * @param posY != null, posY posicion y del EnemigoA
	 * @param puntos != null, puntos puntos que da el EnemigoA cuando es golpeado
	 * @param imagen != null, imagen ruta de la imagen que tendrá en la interfaz el EnemigoA
	 * @param vivo != null, vivo booleano de confirmacion para saber si el EnemigoA aun esta vivo o no
	 * @param direccion != null, direccion char que indica la direccion de movimiento del EnemigoA
	 */
	public EnemigoA(int posX, int posY, int puntos, String imagen, boolean vivo, char direccion) {
		super(posX, posY, puntos, imagen, vivo, direccion);
		
		shoot=new Disparo(0);
		
		this.disparando=false;
		
		delay=3000;
		period=5000;
		
		disparoEnemigo();
	}
	
	/**
	 * @return the timer
	 */
	public Timer getTimer() {
		return timer;
	}

	/**
	 * @param timer the timer to set
	 */
	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	/**
	 * @return the tT
	 */
	public TimerTask gettT() {
		return tT;
	}

	/**
	 * @param tT the tT to set
	 */
	public void settT(TimerTask tT) {
		this.tT = tT;
	}

	/**
	 * @return the shoot
	 */
	public Disparo getShoot() {
		return shoot;
	}

	/**
	 * @param shoot the shoot to set
	 */
	public void setShoot(Disparo shoot) {
		this.shoot = shoot;
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
	 * @return the delay
	 */
	public long getDelay() {
		return delay;
	}

	/**
	 * @param delay the delay to set
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}

	/**
	 * @return the period
	 */
	public long getPeriod() {
		return period;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(long period) {
		this.period = period;
	}

	/**
	 * Metodo que hace al EnemigoA disparar 
	 */
	public void disparoEnemigo() {
		timer=new Timer();
		tT=new TimerTask() {
			@Override
			public void run() {
				if(disparando==false) {
					disparando=true;
					disparar();
				}
			}
		};
		timer.schedule(tT, getDelay(), getPeriod());	
	}
	
	/**
	 * Metodo que calcula si es momento de recargar la bala del EnemigoA
	 */
	@Override
	public void reloadE() {
		if(getShoot().getY()>Juego.ALTO_VENTANA-40) {
			disparando=false;
			shoot.reloadE();
		}
	}
	
	/**
	 * Metodo que actualiza la posicion de la bala del EnemigoA
	 */
	@Override
	public void disparar() {
		if(disparando=true) {
			shoot.moverE();			
			shoot.setY(getPosY());
			shoot.setX(getPosX());
			reloadE();
		}
	}
	
	/**
	 * Metodo toString() del EnemigoA
	 */
	@Override
	public String toString() {
		return getPosX()+""+ getPosY()+""+ getPuntos()+""+ getImagen()+""+ isVivo()+""+ getDireccion();
	}
}
