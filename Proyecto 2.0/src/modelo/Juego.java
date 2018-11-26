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

/**
 * Clase Juego
 * @author Victor Mora
 *
 */
@SuppressWarnings("serial")
public class Juego implements Serializable{

	public static final int ANCHO_VENTANA=360;
	public static final int ALTO_VENTANA=621;
	
	private Usuario usuario;
	private ABBUsuario arbolUsuarios;
	
	private EnemigoBoss eB;
	
	private ListaEnemigo listaEnemigos;
	
	/**
	 * Constructor de la clase Juego 
	 */
	public Juego() {		
		cargarEnemigos();
		cargarBoss();
	}
	
	/**
	 * Metodo que carga los usuarios de un archivo serializado 
	 * @param nombre != null
	 * @throws UsuarioRepetidoException si al intentar agregar el usuario encuentra un usuario existente con el mismo nombre
	 */
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
	
	/**
	 * Metodo que serializa el arbol binario de buqueda de los usuarios
	 */
	public void guardarUsuarios() {
		try {
			ObjectOutputStream salvar=new ObjectOutputStream(new FileOutputStream("src/usuarios/arbol.dat"));
			salvar.writeObject(arbolUsuarios);
			salvar.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que carga los enemigos desde un archivo serializado
	 */
	public void cargarEnemigos() {
		try {
			ObjectInputStream cargar=new ObjectInputStream(new FileInputStream("src/save/NormalEnemies.dat"));
			listaEnemigos=(ListaEnemigo) cargar.readObject();
			cargar.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que lee un archivo de texto e instancia un nuevo EnemigoBoss con los
	 * valores obtenidos del archivo de texto
	 */
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
	
	/**
	 * Metodo que busca el mayor puntaje de todos los usuario
	 * @return El mayor puntaje de entre todos los usuarios
	 * @throws ArbolVacioException Si se intenta llamar este metodo cuando el arbol esta vacio
	 */
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
	
	/**
	 * Metodo que ordena un arreglo conforme a los nombres de los objetos en el 
	 * @return ArrayList ordenado conforme a los nombres de los usuarios
	 */
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
	
	/**
	 * Metodo que ordena un arreglo conforme a los puntos de los objetos en el 
	 * @return ArrayList ordenado conforme a los puntos de los usuarios
	 */
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
	
	/**
	 * Metodo que toma un arreglo y lo convierte a un solo objeto de tipo String 
	 * @param arreglo != null
	 * @return String que contiene todos los toString() de los objetos en el parametro arreglo
	 */
	public String arrayToString(ArrayList<Usuario> arreglo) {
		String end="";
		for(int m=0;m<arreglo.size();m++) {
			int j=m+1;
			end+=""+j+")"+arreglo.get(m).toString()+" ";
			end+="\n";
		}
		return end;
	}
	
	/**
	 * Metodo recursivo que toma un arreglo y lo convierte en un solo objeto de tipo String
	 * @param arreglo != null, El arreglo a convertir 
	 * @param i != null, posicion del arreglo a tomar
	 * @param j != null, contador
	 * @param end != null, acomulador de String 
	 * @return objeto de tipo String con todos los toString() de los objetos del arreglo separados por "\n" 
	 */
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
	
	/**
	 * Metodo que busca en un arreglo ordenado un usuario con un puntaje igual al parametro valor
	 * @param valor != null, Parametro con el puntje a buscar 
	 * @return el toString() del usuario encontrado con el puntaje especificado
	 * @throws PuntajeNoExiste si en el arreglo no hay ningun usuario con u puntaje igul el parametro especificad
	 */
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
	
	/**
	 * Metodo que convierte un arbol binario de busqueda a un ArrayList de tipo usuario
	 * @return Arraylist de tipo usuario con el contenido del arbol binario de busqueda 
	 */
	public ArrayList<Usuario> arrayUsuarios(){
		ArrayList<Usuario> nombres = new ArrayList<Usuario>();
		toArray(getArbolUsuarios().getRaiz(), nombres);
		return nombres;
	}
	
	/**
	 * Metodo recursivo que recorre el arbol binario de busqueda y agrega cada elemento a un ArrayList de tipo Usuario 
	 * @param actual Usuario actual en el que va el recorrido del arbol binario de busqueda
	 * @param nombres != null 
	 */
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

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the arbolUsuarios
	 */
	public ABBUsuario getArbolUsuarios() {
		return arbolUsuarios;
	}

	/**
	 * @param arbolUsuarios the arbolUsuarios to set
	 */
	public void setArbolUsuarios(ABBUsuario arbolUsuarios) {
		this.arbolUsuarios = arbolUsuarios;
	}

	/**
	 * @return the eB
	 */
	public EnemigoBoss geteB() {
		return eB;
	}

	/**
	 * @param eB the eB to set
	 */
	public void seteB(EnemigoBoss eB) {
		this.eB = eB;
	}

	/**
	 * @return the listaEnemigos
	 */
	public ListaEnemigo getListaEnemigos() {
		return listaEnemigos;
	}

	/**
	 * @param listaEnemigos the listaEnemigos to set
	 */
	public void setListaEnemigos(ListaEnemigo listaEnemigos) {
		this.listaEnemigos = listaEnemigos;
	}
	
	
	
	
}