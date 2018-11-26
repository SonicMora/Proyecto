package excepciones;

/**
 * Excepcion ArbolVacioException
 * @author Victor Mora
 *
 */
@SuppressWarnings("serial")
public class ArbolVacioException extends Exception {

	/**
	 * Constructor de la excepcion ArbolVacioException
	 */
	public ArbolVacioException(){
		super("No se puede realizar esta acción pues el arbol está vavío");
	}
	
}
