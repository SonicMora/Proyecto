package modelo;

public class ListaEnemigo {

	private Enemigo cabeza;
	
	private int size;
	
	public ListaEnemigo() {
		cabeza=null;
		size=0;
	}

	public Enemigo getCabeza() {
		return cabeza;
	}

	public void setCabeza(Enemigo cabeza) {
		this.cabeza = cabeza;
	}
	
	public void add(Enemigo nuevo) {
		if(cabeza==null) {
			cabeza=nuevo;
		}else {
			Enemigo temp=cabeza;
			while(temp.getSiguiente()!=null) {
				temp=temp.getSiguiente();
			}
			temp.setSiguiente(nuevo);
		}
		setSize(getSize() + 1);
	}
	
	public void clear() {
		cabeza.setSiguiente(null);
		cabeza=null;
		setSize(0);
	}
	
	public Enemigo get(int index) {
		int c=0;
		Enemigo temp=cabeza;
		while(c<index) {
			if(temp.getSiguiente()!=null) {
				temp=temp.getSiguiente();
			}
			c++;
		}
		return temp;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
}
