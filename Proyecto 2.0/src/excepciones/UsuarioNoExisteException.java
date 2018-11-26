package excepciones;

/**
 * Excepcion UsuarioNoExiste
 * @author Victor Mora
 *
 */
@SuppressWarnings("serial")
public class UsuarioNoExisteException extends Exception {

	/**
	 * Constructor de la exception UsuarioNoExisteException
	 * @param nombre != null, nombre del usuario que no logro encontrarse en el ABB de usuarios
	 */
	public UsuarioNoExisteException(String nombre) {
		super("No pudimos encontrar ningun usuario con el nombre especificado: "+nombre);
	}
	
}
