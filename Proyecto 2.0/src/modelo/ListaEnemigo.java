package modelo;

import java.io.Serializable;

/**
 * Clase ListaEnemigo 
 * @author Victor Mora
 *
 */
@SuppressWarnings("serial")
public class ListaEnemigo implements Serializable{

	private Enemigo cabeza;
	
	private int size;
	
	/**
	 * Constructor de la clase ListaEnemigo
	 */
	public ListaEnemigo() {
		cabeza=null;
		size=0;
	}
	
	/**
	 * @return the cabeza
	 */
	public Enemigo getCabeza() {
		return cabeza;
	}

	/**
	 * @param cabeza the cabeza to set
	 */
	public void setCabeza(Enemigo cabeza) {
		this.cabeza = cabeza;
	}

	/**
	 * @return the size
	 */
	public int size() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Metodo que agrega un nuevo enemigo a la lista de enemigos 
	 * @param nuevo != null
	 */
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
		setSize(size() + 1);
	}
	
	/**
	 * Metodo que limpia la lista de enemigos, haciendo que la cabeza oierda las referencias de sus siguentes
	 * y luego hace el valor de la cabeza=null
	 */
	public void clear() {
		cabeza.setSiguiente(null);
		cabeza=null;
		setSize(0);
	}
	
	/**
	 * Metodo que devuelve un Enemigo dado por el parametro index
	 * @param index != null
	 * @return El enemigo en la posicion del parametro index
	 */
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
	
}
