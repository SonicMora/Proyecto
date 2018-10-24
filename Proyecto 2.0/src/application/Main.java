package application;
	
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import modelo.Juego;


public class Main extends Application {
	
	private static Juego game;
		
	public Main() {
		game=new Juego();
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root=new BorderPane();
			GameController scene = new GameController(root, Juego.ANCHO_VENTANA, Juego.ALTO_VENTANA+50);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			scene.setOnKeyPressed(e->{
				if(e.getCode()==KeyCode.SPACE) {
//					getGame().getUsuario().setPuntos(Main.getGame().getUsuario().getPuntos()+1);
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
