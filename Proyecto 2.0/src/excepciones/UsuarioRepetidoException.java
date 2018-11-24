package excepciones;

@SuppressWarnings("serial")
public class UsuarioRepetidoException extends Exception {

	public UsuarioRepetidoException(String nombre) {
		super("Ya existe un usuario con el nombre especificado: "+nombre);
	}
	
}
