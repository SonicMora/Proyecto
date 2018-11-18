package modelo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.SecureRandom;
//import java.security.SecureRandom;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Juego implements Serializable{

	public static final int ANCHO_VENTANA=360;
	public static final int ALTO_VENTANA=621;
	
	private Usuario usuario;
	private ABBUsuario arbolUsuarios;
	
	private ListaEnemigo listaEnemigos;
	
//	private ListaEnemigo lista;
//	private ListaEnemigo lista2;
	
	private ArrayList<Usuario> usuarios;
	
	//Lissa Fernanda Solano Chavez 
	
	public Juego() {				
		usuario=new Usuario("Mora");
		
//		lista=new ListaEnemigo();
//		lista2=new ListaEnemigo();
		cargarEnemigos();
		
//		crearLista2();
//		crearLista22();
	}
	
	public void cargarUsuarios(String nombre) {
		try {
			ObjectInputStream cargarUsuarios=new ObjectInputStream(new FileInputStream("src/usuarios/arbol.dat"));
			arbolUsuarios=(ABBUsuario)cargarUsuarios.readObject();
			cargarUsuarios.close();
		}catch(Exception e) {
			arbolUsuarios=new ABBUsuario();
		}finally {
			usuario=new Usuario(nombre);
		}
	}
	
	public void guardarUsuarios() {
		arbolUsuarios.add(arbolUsuarios.getRaiz(), usuario);
		try {
			ObjectOutputStream salvar=new ObjectOutputStream(new FileOutputStream("src/usuarios/arbol.dat"));
			salvar.writeObject(arbolUsuarios);
			salvar.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cargarEnemigos() {
		try {
			ObjectInputStream cargar=new ObjectInputStream(new FileInputStream("src/save/NormalEnemies.dat"));
			listaEnemigos=(ListaEnemigo) cargar.readObject();
			cargar.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cargarEnemigosAgresivos() {
		try {
			ObjectInputStream cargar=new ObjectInputStream(new FileInputStream("src/save/AtackingEnemies.dat"));
			listaEnemigos=(ListaEnemigo)cargar.readObject();
			cargar.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int buscarUsuarioMayorPuntaje() {
		int mayor=0;
		for(int i=1;i<usuarios.size();i++) {
			Usuario porInsertar=(Usuario)usuarios.get(i);
			boolean termino=false;
			for(int j=i;j>0 && !termino;j--) {
				Usuario actual=(Usuario)usuarios.get(j-1);
				if(actual.compareTo(porInsertar)>0) {
					usuarios.set(j, actual);
					usuarios.set(j-1, porInsertar);
				}else {
					termino=true;
				}
			}
		}
		mayor=usuarios.get(0).getPuntos();
		return mayor;
	}
	
	public String ordenPuntos() {
		String end="";
		for(int i=1;i<usuarios.size();i++) {
			Usuario porInsertar=(Usuario)usuarios.get(i);
			boolean termino=false;
			for(int j=i;j>0 && !termino;j--) {
				Usuario actual=(Usuario)usuarios.get(j-1);
				if(actual.compareTo(porInsertar)>0) {
					usuarios.set(j, actual);
					usuarios.set(j-1, porInsertar);
				}else {
					termino=true;
				}
			}
		}
		for(int m=0;m<usuarios.size();m++) {
			int j=m+1;
			end+=""+j+")"+usuarios.get(m)+" ";
			end+="\n";
		}
		return end;
	}
	
	public String busquedaBinaria(int valor) {
		boolean encontre=false;
		int inicio=0;
		int medio=0;
		int fin=usuarios.size()-1;
		while(inicio<=fin && !encontre){
				medio=(inicio+fin)/2;
			if(usuarios.get(medio).getPuntos()==valor){
				encontre=true;
			}else if(usuarios.get(medio).getPuntos()>valor){
				fin=medio-1;
			}else{
				inicio=medio+1;
			}
		}
		return usuarios.get(medio).toString();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public void moverDerecha() {
		usuario.moverDerecha();
	}
	
	public void moverIzquierda() {
		usuario.moverIzquierda();
	}
	
	public ListaEnemigo getListaEnemigos() {
		return listaEnemigos;
	}

	public void setListaEnemigos(ListaEnemigo listaEnemigos) {
		this.listaEnemigos = listaEnemigos;
	}

	public ABBUsuario getArbolUsuarios() {
		return arbolUsuarios;
	}

	public void setArbolUsuarios(ABBUsuario arbolUsuarios) {
		this.arbolUsuarios = arbolUsuarios;
	}

	
	
	
	
//	public void crearLista2() {
//		SecureRandom ran=new SecureRandom();
//		int cont=10;
//		int x=50;
//		while(cont>0) {
//			int n=1+ran.nextInt(6);
//			String ruta="";
//			switch(n) {
//			case 1:
//				ruta="Boss/GodCuartas.png";
//				break;
//			case 2: 
//				ruta="Boss/KingCristian.png";
//				break;
//			case 3: 
//				ruta="Boss/LordBarrios.png";
//				break;
//			case 4: 
//				ruta="Boss/PapiBusta.png";
//				break;
//			case 5: 
//				ruta="Boss/QueenNorah.png";
//				break;
//			case 6: 
//				ruta="Boss/YakeBoss.png";
//				break;
//			}
//			lista2.add(new Enemigo(x, 20+ran.nextInt(50), 100, ruta, true, 'D'));
//			cont--;
//			x+=40;
//		}
//		try {
//			ObjectOutputStream salvar=new ObjectOutputStream(new FileOutputStream("src/save/NormalEnemies.dat"));
//			salvar.writeObject(lista2);
//			salvar.close();
//		}catch(Exception e) {
//			
//		}
//	}
//	
//	public void crearLista22() {	
//		SecureRandom ran=new SecureRandom();
//		int cont=10;
//		int x=50;
//		while(cont>0) {
//			int n=1+ran.nextInt(6);
//			String ruta="";
//			switch(n) {
//			case 1:
//				ruta="Boss/GodCuartas.png";
//				break;
//			case 2: 
//				ruta="Boss/KingCristian.png";
//				break;
//			case 3: 
//				ruta="Boss/LordBarrios.png";
//				break;
//			case 4: 
//				ruta="Boss/PapiBusta.png";
//				break;
//			case 5: 
//				ruta="Boss/QueenNorah.png";
//				break;
//			case 6: 
//				ruta="Boss/YakeBoss.png";
//				break;
//			}
//			lista.add(new Enemigo(x, 20+ran.nextInt(50), 100, ruta, true, 'D'));
//			cont--;
//			x+=40;
//		}
//		try {
//			ObjectOutputStream salvar=new ObjectOutputStream(new FileOutputStream("src/save/AtackingEnemies.dat"));
//			salvar.writeObject(lista);
//			salvar.close();
//		}catch(Exception e) {
//			
//		}
//	}
	
}