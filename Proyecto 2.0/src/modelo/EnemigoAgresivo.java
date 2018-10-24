package modelo;

import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("serial")
public class EnemigoAgresivo extends Enemigo {

	public Timer timer;
	
	public TimerTask tT;
	
	public Disparo shoot;

	public boolean disparando;
	
	public EnemigoAgresivo(int posX, int posY, int puntos, String imagen, boolean vivo, char direccion) {
		super(posX, posY, puntos, imagen, vivo, direccion);
		
		shoot=new Disparo(0);
		
		this.disparando=false;
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
		timer.schedule(tT, 2000, 4000);	
	}
	
	
	
}
