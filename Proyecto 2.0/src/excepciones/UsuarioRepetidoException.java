package excepciones;

/**
 * Excepcion UsuarioRepetidoException
 * @author Victor Mora
 *
 */
@SuppressWarnings("serial")
public class UsuarioRepetidoException extends Exception {

	/**
	 * Constructor de la Excepcion UsuarioRepetidoException
	 * @param nombre != null, nombre del usuario existente
	 */
	public UsuarioRepetidoException(String nombre) {
		super("Ya existe un usuario con el nombre especificado: "+nombre);
	}
	
}
