package excepciones;

@SuppressWarnings("serial")
public class PuntajeNoExiste extends Exception {

	public PuntajeNoExiste(int pt) {
		super("No existe un puntaje: "+pt);
	}
	
}
