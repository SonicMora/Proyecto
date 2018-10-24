package application;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import modelo.Enemigo;
import modelo.Personaje;

public class GameController extends Scene{
		
	@FXML private ImageView fondo;
	@FXML private ImageView imgUsuario;

	@FXML private ImageView laser;
	
	@FXML private ArrayList<ImageView> enemigos;
	
	@FXML private Timeline animacionD;
	@FXML private Timeline animacionI;
	@FXML private Timeline animacionB;
		
	@FXML private Pane ajam;
	
	@FXML private BorderPane bP;
	
	@FXML private Canvas canvas;
	
	@FXML private Bounds bounds;
	
	public GameController(BorderPane root, double width, double height) {
		super(root, width, height);
		
		bP=root;
		
		canvas=new Canvas();
		canvas.widthProperty().bind(bP.widthProperty());
		canvas.setHeight(50);
		
		primitivas(canvas.getGraphicsContext2D());
		
		ajam=new Pane();
		
		setEnemigos(new ArrayList<ImageView>());
		inicializarEnemigos();
		
		laser=new ImageView(new Image("data/laser.png"));
		laser.setFitWidth(Main.getGame().getUsuario().getAvatar().getBala().getAncho());
		laser.setFitHeight(Main.getGame().getUsuario().getAvatar().getBala().getLargo());
				
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
		ajam.getChildren().add(laser);
		
		bP.setCenter(ajam);
		bP.setTop(canvas);
		
		bounds=ajam.getBoundsInLocal();
		
		for(int i=0;i<getEnemigos().size();i++) {
			ajam.getChildren().add(getEnemigos().get(i));
		}
		
		laser.setVisible(false);
		moverEnemigos();
	}
	
	public void moverEnemigos() {
		Timeline animationE=new Timeline(new KeyFrame(Duration.millis(100), e->{
			for(int i=0;i<getEnemigos().size();i++) {
				corregirEnemigos(Main.getGame().getEnemigos().get(i));
				getEnemigos().get(i).setX(Main.getGame().getEnemigos().get(i).getPosX());
			}
		}));
		animationE.setCycleCount(Timeline.INDEFINITE);
		animationE.play();
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
					
			laser.setX(Main.getGame().getUsuario().getAvatar().getBala().getX());
			laser.setY(Main.getGame().getUsuario().getAvatar().getBala().getY()+60);
			
			for(int i=0;i<getEnemigos().size();i++) {
				if(getEnemigos().get(i).contains(new Point2D(laser.getX(), laser.getY()))) {
					Main.getGame().getEnemigos().get(i).setVivo(false);
					getEnemigos().get(i).setVisible(false);
					ajam.getChildren().remove(getEnemigos().get(i));
					pasarLvl();
				}
			}
			laser.setVisible(true);
			
			Main.getGame().getUsuario().getAvatar().reload();
			dejarDeDisparar();
		}));
		animacionB.setCycleCount(Timeline.INDEFINITE);
		animacionB.play();
	}
	
	/**
	 * 
	 * HAY QUE HACER UN METOTO clear(); CUANDO IMPLEMENTE LAS LISTAS ENLAZADAS 
	 * 
	 */
	
	public void pasarLvl() {
		int muertos=0;
		for(int i=0;i<Main.getGame().getEnemigos().size();i++) {
			if(Main.getGame().getEnemigos().get(i).isVivo()==false) {
				muertos++;
			}
		}
		if(muertos==Main.getGame().getEnemigos().size()) {
			Main.getGame().inicializarEnemigosAgresivos();
			for(int i=0;i<enemigos.size();i++) {
				ajam.getChildren().add(enemigos.get(i));
			}
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
			laser.setVisible(false);
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
		while(cont<Main.getGame().getEnemigos().size()) {
			getEnemigos().add(new ImageView(new Image(Main.getGame().getEnemigos().get(cont).getImagen(), 50, 50, true, true)));
			cont++;
		}
		posicionarEnemigos();
	}
	
	public void posicionarEnemigos() {
		for(int i=0;i<getEnemigos().size();i++) {
			getEnemigos().get(i).setX(Main.getGame().getEnemigos().get(i).getPosX());
			getEnemigos().get(i).setY(Main.getGame().getEnemigos().get(i).getPosY());
		}
	}
	
	public void primitivas(GraphicsContext g) {
		g.setFill(Color.BLACK);
		g.setFont(new Font("ARCADECLASSIC", 20));
		g.fillText("PUNTAJE", 5, 20);
		g.fillText("PUNTAJE MAXIMO", 100, 20);
		
		g.drawImage(new Image("data/usuarioD.png"), 270, 10, 30, 30);
		g.drawImage(new Image("data/usuarioD.png"), 300, 10, 30, 30);
		g.drawImage(new Image("data/usuarioD.png"), 330, 10, 30, 30);
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