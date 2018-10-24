package modelo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Juego implements Serializable{

	public static final int ANCHO_VENTANA=360;
	public static final int ALTO_VENTANA=621;
	
	private Usuario usuario;
	
	private String rutaEnemigos;
	
	private ArrayList<Usuario> usuarios;
	private ArrayList<Enemigo> enemigos;
	
	public Juego() {
		rutaEnemigos="Boss/KingCristian.png";
		
		usuario=new Usuario("Mora", "epa");
		enemigos=new ArrayList<Enemigo>();
		
		inicializarEnemigos();
	}
	
	public void inicializarEnemigos() {
		SecureRandom ran=new SecureRandom();
		int cont=10;
		int x=50;
		while(cont>0) {
			enemigos.add(new Enemigo(x, 20+ran.nextInt(50), 100, rutaEnemigos, true, 'D'));
			cont--;
			x+=40;
		}
	}
	
	public void inicializarEnemigosAgresivos() {
		enemigos.removeAll(enemigos);
		System.out.println(enemigos.size());
		SecureRandom ran=new SecureRandom();
		int cont=10;
		int x=50;
		while(cont>0) {
			enemigos.add(new EnemigoAgresivo(x, 20+ran.nextInt(50), 100, rutaEnemigos, true, 'D'));
			cont--;
			x+=40;
		}
		System.out.println(enemigos.size());
	}
	
	@SuppressWarnings("unchecked")
	public void agregarUsuario(String nombre, String contra) {
		try {
			ObjectInputStream mostrar=new ObjectInputStream(new FileInputStream("src/partidas/Usuarios.dat"));
			usuarios=(ArrayList<Usuario>) mostrar.readObject();
			mostrar.close();
		}catch(Exception e) {
			usuarios=new ArrayList<Usuario>();
		}finally {
			usuario=new Usuario(nombre, contra);
		}
	}

	public void guardar() {
		try {
			ObjectOutputStream escribir=new ObjectOutputStream(new FileOutputStream("src/partidas/Usuarios.dat"));
			escribir.writeObject(usuarios);
			escribir.close();
		}catch(IOException e) {
			System.out.println("Ha ocurrido un error");
		}
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

	public ArrayList<Enemigo> getEnemigos() {
		return enemigos;
	}

	public void setEnemigos(ArrayList<Enemigo> enemigos) {
		this.enemigos = enemigos;
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
	
	public void moverDerecha() {
		usuario.moverDerecha();
	}
	
	public void moverIzquierda() {
		usuario.moverIzquierda();
	}
	
	public void setRutaEnemigos(String rutaEnemigos) {
		this.rutaEnemigos=rutaEnemigos;
	}
	
	public String getRutaEnemigos() {
		return rutaEnemigos;
	}
}
