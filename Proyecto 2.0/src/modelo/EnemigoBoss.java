package modelo;

import java.io.Serializable;

public class EnemigoBoss extends EnemigoA implements Serializable{

	private static final long serialVersionUID = 1L;

	private int vida;

	public EnemigoBoss(int posX, int posY, int puntos, String imagen, boolean vivo, char direccion) {
		super(posX, posY, puntos, imagen, vivo, direccion);
		vida=5000;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}
	
	
}
