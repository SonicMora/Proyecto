package modelo;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import interfaces.InterfaceDisparar;
import interfaces.InterfaceReloadE;

public class EnemigoA extends Enemigo implements Serializable, InterfaceReloadE, InterfaceDisparar {

	private static final long serialVersionUID = 1L;

	private Timer timer;
	
	private TimerTask tT;
	
	private Disparo shoot;

	private boolean disparando;
	
	private long delay;
	
	private long period;
	
	public EnemigoA(int posX, int posY, int puntos, String imagen, boolean vivo, char direccion) {
		super(posX, posY, puntos, imagen, vivo, direccion);
		
		shoot=new Disparo(0);
		
		this.disparando=false;
		
		delay=2000;
		period=4000;
	}
		
	public boolean isDisparando() {
		return disparando;
	}

	public void setDisparando(boolean disparando) {
		this.disparando = disparando;
	}

	public void setDelay(long delay) {
		this.delay=delay;
	}
	
	public void setPeriod(long period) {
		this.period=period;
	}
	
	public long getDelay() {
		return delay;
	}
	
	public long getPeriod() {
		return period;
	}
	
	public Disparo getShot() {
		return shoot;
	}
	
	public void disparoEnemigo() {
		timer=new Timer();
		tT=new TimerTask() {
			@Override
			public void run() {
				if(disparando==false) {
					disparando=true;
					disparar();
					shoot.setY(getPosY());
					shoot.setX(getPosX());
					reloadE();
				}
			}
		};
		timer.schedule(tT, getDelay(), getPeriod());	
	}
	
	@Override
	public void reloadE() {
		if(getShot().getY()>Juego.ALTO_VENTANA-40) {
			disparando=false;
			shoot.reloadE();
		}
	}
	
	@Override
	public void disparar() {
		if(disparando=true) {
			shoot.moverE();
		}
	}
	
	@Override
	public String toString() {
		return getPosX()+""+ getPosY()+""+ getPuntos()+""+ getImagen()+""+ isVivo()+""+ getDireccion();
	}
}
