package excepciones;

/**
 * Excepcion PuntajeNoExiste
 * @author Victor Mora
 *
 */
@SuppressWarnings("serial")
public class PuntajeNoExiste extends Exception {

	/**
	 * Constructor de la excepcion PuntajeNoExiste
	 * @param pt != null, puntaje inexistente en el ABB de usuarios
	 */
	public PuntajeNoExiste(int pt) {
		super("No existe un puntaje: "+pt);
	}
	
}
