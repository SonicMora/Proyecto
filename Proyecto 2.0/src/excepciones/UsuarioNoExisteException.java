package excepciones;

@SuppressWarnings("serial")
public class UsuarioNoExisteException extends Exception {

	public UsuarioNoExisteException(String nombre) {
		super("No pudimos hallar el usuario especificado: "+nombre);
	}
	
}
