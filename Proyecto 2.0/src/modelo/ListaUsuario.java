package modelo;

public class ListaUsuario {

	private Usuario cabeza;
	
	private int size;
	
	public ListaUsuario() {
		cabeza=null;
		size=0;
	}

	public void add(String nombre) {
		Usuario nuevo=new Usuario(nombre);
		if(cabeza==null) {
			cabeza=nuevo;
		}else {
			Usuario temp=cabeza;
			while(temp.getSiguiente()!=null) {
				temp=temp.getSiguiente();
			}
			temp.setSiguiente(nuevo);
		}
		setSize(getSize() + 1);
	}
	
	public Usuario getCabeza() {
		return cabeza;
	}

	public void setCabeza(Usuario cabeza) {
		this.cabeza = cabeza;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
	
}
