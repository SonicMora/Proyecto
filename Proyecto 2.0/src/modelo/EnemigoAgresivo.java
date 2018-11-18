package modelo;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Timer;
import java.util.TimerTask;

import interfaces.InterfaceDisparar;
import interfaces.InterfaceReloadE;

@SuppressWarnings("serial")
public class EnemigoAgresivo extends Enemigo implements Serializable, InterfaceReloadE, InterfaceDisparar {

	private Timer timer;
	
	private TimerTask tT;
	
	private Disparo shoot;

	private boolean disparando;
	
	private long delay;
	
	private long period;
	
	public EnemigoAgresivo(int posX, int posY, int puntos, String imagen, boolean vivo, char direccion) {
		super(posX, posY, puntos, imagen, vivo, direccion);
		
		shoot=new Disparo(0);
		
		this.disparando=false;
		
		delay=2000;
		period=4000;
		
		disparoEnemigo();
	}
		
	public boolean isDisparando() {
		return disparando;
	}

	public void setDisparando(boolean disparando) {
		this.disparando = disparando;
	}

	public void setDelay() {
		SecureRandom ran=new SecureRandom();
		long d=3000+ran.nextInt(5000);
		delay=d;
	}
	
	public void setPeriod() {
		SecureRandom ran=new SecureRandom();
		long p=3000+ran.nextInt(4000);
		period=p;
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
					shoot.setY(getPosY());
					shoot.setX(getPosX());
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
	
}
