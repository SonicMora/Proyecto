package modelo;

@SuppressWarnings("serial")
public class EnemigoBoss extends EnemigoAgresivo {

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
