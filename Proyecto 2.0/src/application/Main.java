package application;
	
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import modelo.Juego;


public class Main extends Application {
	
	private static Juego game;
		
	public Main() {
		game=new Juego();
		iniciarJuego();
	}
	
	public void iniciarJuego() {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Registrar usuario");
		dialog.setHeaderText(null);
		dialog.setContentText("Escribe tu nombre:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			game.cargarUsuarios(result.get());
		}		
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root=new BorderPane();
			GameController scene = new GameController(root, Juego.ANCHO_VENTANA+140, Juego.ALTO_VENTANA+50);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			scene.setOnKeyPressed(e->{
				if(e.getCode()==KeyCode.SPACE) {
					if(getGame().getUsuario().getAvatar().isDisparando()==false) {
						getGame().getUsuario().getAvatar().setDisparando(true);
						getGame().getUsuario().getAvatar().getBala().setX(getGame().getUsuario().getAvatar().getPosX());
						scene.disparar();
					}
				}else if(e.getCode()==KeyCode.LEFT) {
					scene.moverI();
				}else if(e.getCode()==KeyCode.RIGHT) {
					scene.moverD();
				}
 			});
			scene.setOnKeyReleased(e->{
				if(e.getCode()==KeyCode.LEFT) {
					scene.detener(scene.getAnimacionI());
				}else if(e.getCode()==KeyCode.RIGHT) {
					scene.detener(scene.getAnimacionD());
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Juego getGame() {
		return game;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
