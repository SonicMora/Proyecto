package application;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Optional;

import excepciones.ArbolVacioException;
import excepciones.PuntajeNoExiste;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import modelo.Enemigo;
import modelo.EnemigoBoss;
import modelo.Personaje;

public class GameController extends Scene {
	
	private ImageView fondo;
	private ImageView imgUsuario;
	
	private ImageView laserU;
	private ImageView boss;
	private ImageView laserBoss;

	private ArrayList<ImageView> enemigos;
	
	private Timeline animacionD;
	private Timeline animacionI;
	private Timeline animacionB;
	private Timeline animacionBE;
	private Timeline animationE;
		
	private Pane ajam;
	
	private BorderPane bP;
	
	private Canvas canvas;
	
	private Bounds bounds;
	
	public GameController(BorderPane root, double width, double height) {
		super(root, width, height);
		
		bP=root;
		
		VBox costado=new VBox();
		
		costado.getChildren().add(new Label("Para guardar presione G"));
		costado.getChildren().add(new Label("Para ver puntajes presione P"));
		costado.getChildren().add(new Label("Para disparar presione ESPACIO"));
		costado.getChildren().add(new Label("Para moverse presione <- Ó ->"));
		costado.getChildren().add(new Label("Para Buscar un Usuario presione B"));
		
		canvas=new Canvas();
		canvas.widthProperty().bind(bP.widthProperty());
		canvas.setHeight(50);
		
		primitivas(canvas.getGraphicsContext2D());
		
		ajam=new Pane();
				
		enemigos=new ArrayList<ImageView>();
		laserBoss=new ImageView(new Image("data/laser.png"));
		laserBoss.setRotate(180);
		laserBoss.setVisible(false);
		
		inicializarEnemigos();
		
		laserU=new ImageView(new Image("data/laser.png"));
		laserU.setFitWidth(Main.getGame().getUsuario().getAvatar().getBala().getAncho());
		laserU.setFitHeight(Main.getGame().getUsuario().getAvatar().getBala().getLargo());
				
		imgUsuario=new ImageView();
		imgUsuario.setImage(new Image(Main.getGame().getUsuario().getAvatar().getImagen()));
		imgUsuario.setX(Main.getGame().getUsuario().getAvatar().getPosX());
		imgUsuario.setY(Main.getGame().getUsuario().getAvatar().getPosY());
		imgUsuario.setFitWidth(Main.getGame().getUsuario().getAvatar().getAncho());
		imgUsuario.setFitHeight(Main.getGame().getUsuario().getAvatar().getLargo());
		
		fondo=new ImageView();
		fondo.setImage(new Image("data/background.gif"));
		fondo.setX(0);
		fondo.setY(0);
				
		ajam.getChildren().add(fondo);
		ajam.getChildren().add(imgUsuario);
		ajam.getChildren().add(laserU);
		ajam.getChildren().add(laserBoss);
		
		bP.setCenter(ajam);
		bP.setTop(canvas);
		bP.setRight(costado);
		
		bounds=ajam.getBoundsInLocal();
		
		addEnemigos();
		
		laserU.setVisible(false);
		moverEnemigos();
	}
	
	public void pasarAlBoss() {
		boss=new ImageView(new Image(Main.getGame().geteB().getImagen(), 100, 100, true, true));
		ajam.getChildren().add(boss);
		moverBoss();
	}
	
	public void addEnemigos(){
		for(int i=0;i<getEnemigos().size();i++) {
			ajam.getChildren().add(getEnemigos().get(i));
			getEnemigos().get(i).setVisible(true);
		}
	}
	
	public void moverBoss() {
		Timeline a=new Timeline(new KeyFrame(Duration.millis(30), e->{
			corregirEnemigos(Main.getGame().geteB());
			boss.setX(Main.getGame().geteB().getPosX());
//			disparoEnemigo(Main.getGame().geteB());
		}));
		a.setCycleCount(Timeline.INDEFINITE);
		a.play();
	}
	
	public void moverEnemigos() {
		animationE=new Timeline(new KeyFrame(Duration.millis(100), e->{
			for(int i=0;i<getEnemigos().size();i++) {
				corregirEnemigos(Main.getGame().getListaEnemigos().get(i));
				getEnemigos().get(i).setX(Main.getGame().getListaEnemigos().get(i).getPosX());
			}
		}));
		animationE.setCycleCount(Timeline.INDEFINITE);
		animationE.play();
	}
	
	public void disparoEnemigo(EnemigoBoss este) {
		if(!este.isDisparando()) {
			este.disparoEnemigo();
		}
		animacionBE=new Timeline(new KeyFrame(Duration.millis(3000), e->{
			laserBoss.setVisible(true);
			laserBoss.setX(Main.getGame().geteB().getShot().getX());
			laserBoss.setY(Main.getGame().geteB().getShot().getY());
			este.reloadE();
		}));
		animacionBE.setCycleCount(Timeline.INDEFINITE);
		animacionBE.play();
	}
	
	public void moverI() {
		animacionI=new Timeline(new KeyFrame(Duration.millis(10), e->{
			Main.getGame().getUsuario().getAvatar().setImagen(Personaje.IZQ);
			Main.getGame().getUsuario().getAvatar().moverIzquierda();
			imgUsuario.setImage(new Image(Main.getGame().getUsuario().getAvatar().getImagen()));
			imgUsuario.setX(Main.getGame().getUsuario().getAvatar().getPosX());
			corregirUsuario();
		}));
		animacionI.setCycleCount(Timeline.INDEFINITE);
		animacionI.play();
		corregirUsuario();
	}
	
	public void moverD() {
		animacionD=new Timeline(new KeyFrame(Duration.millis(10), e->{
			Main.getGame().getUsuario().getAvatar().setImagen(Personaje.DER);
			Main.getGame().getUsuario().getAvatar().moverDerecha();
			imgUsuario.setImage(new Image(Main.getGame().getUsuario().getAvatar().getImagen()));
			imgUsuario.setX(Main.getGame().getUsuario().getAvatar().getPosX());
			corregirUsuario();	
		}));
		animacionD.setCycleCount(Timeline.INDEFINITE);
		animacionD.play();
		corregirUsuario();
	}
	
	public void disparar() {
		animacionB=new Timeline(new KeyFrame(Duration.millis(100), e->{
			Main.getGame().getUsuario().getAvatar().disparar();
			laserU.setX(Main.getGame().getUsuario().getAvatar().getBala().getX());
			laserU.setY(Main.getGame().getUsuario().getAvatar().getBala().getY()+60);
			
			for(int i=0;i<getEnemigos().size();i++) {
				if(getEnemigos().get(i).contains(new Point2D(laserU.getX(), laserU.getY()))) {
					Main.getGame().getListaEnemigos().get(i).setVivo(false);
					Main.getGame().getUsuario().setPuntos(Main.getGame().getUsuario().getPuntos()+Main.getGame().getListaEnemigos().get(i).getPuntos());
					getEnemigos().get(i).setVisible(false);
					ajam.getChildren().remove(getEnemigos().get(i));
					pasarLvl();
				}
			}
			laserU.setVisible(true);
			
			Main.getGame().getUsuario().getAvatar().reloadP();
			dejarDeDisparar();
		}));
		animacionB.setCycleCount(Timeline.INDEFINITE);
		animacionB.play();
	}
	
	public void pasarLvl() {
		int muertos=0;
		for(int i=0;i<Main.getGame().getListaEnemigos().size();i++) {
			if(Main.getGame().getListaEnemigos().get(i).isVivo()==false) {
				muertos++;
			}
		}
		if(muertos==Main.getGame().getListaEnemigos().size()) {
			Main.getGame().getUsuario().setNivel(2);
			detener(animationE);
			pasarAlBoss();
		}
	}
		
	public void corregirEnemigos(Enemigo este) {
		if(este.getDireccion()=='D' && este.getPosX()<bounds.getMaxX()) {
			este.moverD();
		}else if(este.getDireccion()=='D' && este.getPosX()>=bounds.getMaxX()) {
			este.setDireccion('I');
			este.moverI();
		}else if(este.getDireccion()=='I' && este.getPosX()>bounds.getMinX()) {
			este.moverI();
		}else if(este.getDireccion()=='I' && este.getPosX()<=bounds.getMinX()) {
			este.setDireccion('D');
			este.moverD();
		}	
	}
	
	public void dejarDeDisparar() {
		if(Main.getGame().getUsuario().getAvatar().isDisparando()==false) {
			detener(getAnimacionB());
			laserU.setVisible(false);
		}
	}
	
	public void detener(Timeline animacion) {
		animacion.pause();
	}

	public void corregirUsuario() {
		if(imgUsuario.getX()>=bounds.getMaxX()-40) {
			detener(animacionD);
		}else if(imgUsuario.getX()<bounds.getMinX()+40){
			detener(animacionI);
		}
	}

	public void inicializarEnemigos() {
		int cont=0;
		while(cont<Main.getGame().getListaEnemigos().size()) {
			getEnemigos().add(new ImageView(new Image(Main.getGame().getListaEnemigos().get(cont).getImagen(), 50, 50, true, true)));
			cont++;
		}
		posicionarEnemigos();
	}
	
	public void posicionarEnemigos() {
		for(int i=0;i<getEnemigos().size();i++) {
			getEnemigos().get(i).setX(Main.getGame().getListaEnemigos().get(i).getPosX());
			getEnemigos().get(i).setY(Main.getGame().getListaEnemigos().get(i).getPosY());
		}
	}
	
	public void primitivas(GraphicsContext g) {
		try {
			final Font f=Font.loadFont(new FileInputStream(new File("src/data/ARCADECLASSIC.TTF")), 20);
			g.setFill(Color.BLACK);
			g.setFont(f);
			g.fillText("PUNTAJE", 5, 20);
			g.fillText("PUNTAJE MAXIMO", 100, 20);
			try{
				g.fillText(Integer.toString(Main.getGame().buscarMayorPuntaje()), 130, 35);
			}catch(ArbolVacioException e) {
				g.fillText("3000", 130, 35);
			}
			
			g.drawImage(new Image("data/usuarioD.png"), 270, 10, 30, 30);
			g.drawImage(new Image("data/usuarioD.png"), 300, 10, 30, 30);
			g.drawImage(new Image("data/usuarioD.png"), 330, 10, 30, 30);
			pintarPuntaje(g);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void pintarPuntaje(GraphicsContext g) {
		Timeline animacionPuntaje=new Timeline(new KeyFrame(Duration.millis(1), e->{
			g.setFont(new Font("ARCADECLASSIC", 20));
			g.setFill(Color.BLACK);
			g.fillText(Integer.toString(Main.getGame().getUsuario().getPuntos()), 5, 40);
		}));
		animacionPuntaje.setCycleCount(Timeline.INDEFINITE);
		animacionPuntaje.play();
	}
	
	public void verPuntajes() {
		Alert puntajes=new Alert(AlertType.NONE);
		
		ButtonType aceptar=new ButtonType("Aceptar");
		
		puntajes.getButtonTypes().add(aceptar);
		
		puntajes.setHeaderText("PUNTAJES DE LA PIPOL QUE HA JUGADO");
		puntajes.setContentText(Main.getGame().arrayToString(Main.getGame().ordenPuntos()));
		puntajes.setTitle("This game");
		Optional<ButtonType> result=puntajes.showAndWait();
		if(result.get()==aceptar) {
			puntajes.close();
		}
	}
	
	public void guardar() {
		Main.getGame().guardarUsuarios();
	}
	
	public void buscar() {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Buscar usuario");
		dialog.setHeaderText(null);
		dialog.setContentText("Escribe el puntaje del usuario que quieres buscar:");

		Alert puntajes=new Alert(AlertType.NONE);
		puntajes.setHeaderText("PUNTAJES DE LA PIPOL QUE HA JUGADO");
		puntajes.setTitle("This game");
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			try {
				puntajes.setContentText(Main.getGame().busquedaBinaria(Integer.parseInt(result.get())));
				puntajes.showAndWait();
			} catch (NumberFormatException | PuntajeNoExiste e) {
				Main.mostrar(e.getMessage());
			}
		}	
	}
	
	public Timeline getAnimacionD() {
		return animacionD;
	}

	public Timeline getAnimacionI() {
		return animacionI;
	}

	public Timeline getAnimacionB() {
		return animacionB;
	}
	
	 public Pane getAjam() {
		return ajam;
	}

	public void setAjam(Pane ajam) {
		this.ajam = ajam;
	}

	public ArrayList<ImageView> getEnemigos() {
		return enemigos;
	}

	public void setEnemigos(ArrayList<ImageView> enemigos) {
		this.enemigos = enemigos;
	}
	
}