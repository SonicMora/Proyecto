package modelo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import excepciones.*;

@SuppressWarnings("serial")
public class Juego implements Serializable{

	public static final int ANCHO_VENTANA=360;
	public static final int ALTO_VENTANA=621;
	
	private Usuario usuario;
	private ABBUsuario arbolUsuarios;
	
	private EnemigoBoss eB;
	
	private ListaEnemigo listaEnemigos;
			
	public Juego() {		
		cargarEnemigos();
		cargarBoss();
	}
	
	public void cargarUsuarios(String nombre) throws UsuarioRepetidoException {
		try {
			ObjectInputStream cargarUsuarios=new ObjectInputStream(new FileInputStream("src/usuarios/arbol.dat"));
			arbolUsuarios=(ABBUsuario)cargarUsuarios.readObject();
			cargarUsuarios.close();
		}catch(Exception e) {
			arbolUsuarios=new ABBUsuario();
		}finally {
			usuario=new Usuario(nombre);
			arbolUsuarios.add(arbolUsuarios.getRaiz(), usuario);
		}
	}
	
	public void guardarUsuarios() {
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
	
	public void cargarBoss() {
		try {
			BufferedReader lector=new BufferedReader(new FileReader("src/save/Boss.txt"));
			String linea=lector.readLine();
			String[] str=linea.split(",");
			eB=new EnemigoBoss(Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2]), str[3], Boolean.parseBoolean(str[4]), str[5].charAt(0) );
			lector.close();
		} catch (Exception e) {

		}
	}
	
	public int buscarMayorPuntaje() throws ArbolVacioException {
		int mayor=0;
		ArrayList<Usuario> usuarios=arrayUsuarios();
		if(usuarios.size()==0) {
			throw new ArbolVacioException();
		}else {
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
	}
	
	public ArrayList<Usuario> ordenNombres(){
		ArrayList<Usuario> usuarios=arrayUsuarios();
		ComparadorNombre cN=new ComparadorNombre();
		for(int i=1;i<usuarios.size();i++) {
			for(int j=i;j>0 && cN.compare(usuarios.get(j-1), usuarios.get(j))>0;j--) {
				Usuario temp=usuarios.get(j);
				usuarios.set(j, usuarios.get(j-1));
				usuarios.set(j-1, temp);
			}
		}
		return usuarios;
	}
	
	public ArrayList<Usuario> ordenPuntos() {
		ArrayList<Usuario> usuarios=arrayUsuarios();
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
		return usuarios;
	}
	
	public String arrayToString(ArrayList<Usuario> arreglo) {
		String end="";
		for(int m=0;m<arreglo.size();m++) {
			int j=m+1;
			end+=""+j+")"+arreglo.get(m).toString()+" ";
			end+="\n";
		}
		return end;
	}
	
	public String arrayToString(ArrayList<Usuario> arreglo, int i, int j, String end) {
		if(i<arreglo.size()) {
			j=i+1;
			end+=""+j+")"+arreglo.get(i).toString()+" ";
			end+="\n";
			return arrayToString(arreglo, i++, j, end);
		}else {
			return end;
		}
	}
	
	public String busquedaBinaria(int valor) throws PuntajeNoExiste{
		boolean encontre=false;
		int inicio=0;
		int medio=0;
		ArrayList<Usuario> usuarios=ordenPuntos();
		int fin=usuarios.size()-1;
		while(inicio<=fin && !encontre){
				medio=(inicio+fin)/2;
			if(usuarios.get(medio).getPuntos()==valor){
				encontre=true;
			}else if(usuarios.get(medio).getPuntos()<valor){
				fin=medio-1;
			}else{
				inicio=medio+1;
			}
		}if(encontre==false) {
			throw new PuntajeNoExiste(valor); 
		}else {
			return usuarios.get(medio).toString();
		}
	}
	
	public ArrayList<Usuario> arrayUsuarios(){
		ArrayList<Usuario> nombres = new ArrayList<Usuario>();
		toArray(getArbolUsuarios().getRaiz(), nombres);
		return nombres;
	}

	public void toArray(Usuario actual, ArrayList<Usuario> nombres){
		if(actual!=null ) {
			if(actual.isHoja()) {
				nombres.add(actual);
			}else {
				nombres.add(actual);
				toArray(actual.getIzquierdo(), nombres);
				toArray(actual.getDerecho(), nombres);
			}
		}
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	public EnemigoBoss geteB() {
		return eB;
	}

	public void seteB(EnemigoBoss eB) {
		this.eB = eB;
	}
	
	
}