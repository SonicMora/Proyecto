package application;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Optional;

import excepciones.ArbolVacioException;
import excepciones.PuntajeNoExiste;
import excepciones.UsuarioNoExisteException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
	private Timeline animationMB;
		
	private ProgressBar vidaBoss;
	
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
		vidaBoss=new ProgressBar(0);
		ajam.getChildren().add(boss);
		ajam.getChildren().add(vidaBoss);
		moverBoss();
	}
	
	public void addEnemigos(){
		for(int i=0;i<getEnemigos().size();i++) {
			ajam.getChildren().add(getEnemigos().get(i));
			getEnemigos().get(i).setVisible(true);
		}
	}
	
	public void moverBoss() {
		animationMB=new Timeline(new KeyFrame(Duration.millis(30), e->{
			corregirEnemigos(Main.getGame().geteB());
			boss.setX(Main.getGame().geteB().getPosX());
			vidaBoss.setLayoutX(boss.getX());
			disparoEnemigo(Main.getGame().geteB());
		}));
		animationMB.setCycleCount(Timeline.INDEFINITE);
		animationMB.play();
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
		animacionBE=new Timeline(new KeyFrame(Duration.millis(30), e->{
			if(este.isDisparando()) {
				laserBoss.setVisible(true);
				laserBoss.setX(Main.getGame().geteB().getShoot().getX());
				laserBoss.setY(Main.getGame().geteB().getShoot().getY());
			}else {
				laserBoss.setVisible(false);
			}
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
			if(Main.getGame().getUsuario().getNivel()==1) {
				disparoLvl1();
			}else {
				disparoLvl2();
			}
		}));
		animacionB.setCycleCount(Timeline.INDEFINITE);
		animacionB.play();
	}
	
	public void disparoLvl1() {
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
	}
	
	public void disparoLvl2() {
		if(boss.contains(new Point2D(laserU.getX(), laserU.getY()))) {
			Main.getGame().geteB().setVida(Main.getGame().geteB().getVida()-Main.getGame().getUsuario().getAvatar().getBala().getDamage());
			Main.getGame().getUsuario().setPuntos(Main.getGame().getUsuario().getPuntos()+Main.getGame().geteB().getPuntos());
			vidaBoss.setProgress(vidaBoss.getProgress()+Main.getGame().getUsuario().getAvatar().getBala().getDamage());
			System.out.println(vidaBoss.getProgress());
		}
		laserU.setVisible(true);
		Main.getGame().getUsuario().getAvatar().reloadP();
		dejarDeDisparar();
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
	
	public void verPuntajes(int x) {
		Alert puntajes=new Alert(AlertType.NONE);
		
		ButtonType aceptar=new ButtonType("Aceptar");
		
		puntajes.getButtonTypes().add(aceptar);
		
		puntajes.setHeaderText("PUNTAJES DE LA PIPOL QUE HA JUGADO");
		puntajes.setTitle("This game");
		if(x>0) {
			puntajes.setContentText(Main.getGame().arrayToString(Main.getGame().ordenPuntos()));
		}else {
			puntajes.setContentText(Main.getGame().arrayToString(Main.getGame().ordenNombres()));
		}
		Optional<ButtonType> result=puntajes.showAndWait();
		if(result.get()==aceptar) {
			puntajes.close();
		}
	}
	
	public void seleccionarOrden() {
		Alert orden=new Alert(AlertType.CONFIRMATION);
		
		ButtonType nombres=new ButtonType("Orden Alfabético");
		ButtonType puntos=new ButtonType("Orden Puntaje");
		
		orden.getButtonTypes().clear();
		orden.getButtonTypes().addAll(nombres, puntos);
		orden.setHeaderText(null);
		orden.setContentText("Seleccione un tipo de ordenamiento");
		Optional<ButtonType> result=orden.showAndWait();
		if(result.get()==nombres) {
			verPuntajes(-1);
		}else {
			verPuntajes(1);
		}
	}
	
	public void guardar() {
		Main.getGame().guardarUsuarios();
	}
	
	public void buscar(int x) {
		
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Buscar usuario");
		dialog.setHeaderText(null);
		
		Alert puntajes=new Alert(AlertType.NONE);
		puntajes.setHeaderText("PUNTAJES DE LA PIPOL QUE HA JUGADO");
		puntajes.setTitle("This game");
		
		ButtonType aceptar=new ButtonType("Aceptar");
		
		puntajes.getButtonTypes().add(aceptar);
		if(x>0) {
			dialog.setContentText("Escribe el nombre del usuario que quieres buscar");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
				try {
					puntajes.setContentText(Main.getGame().getArbolUsuarios().buscar(Main.getGame().getArbolUsuarios().getRaiz(), result.get()).toString());
					Optional<ButtonType> result2=puntajes.showAndWait();
					if(result2.get()==aceptar) {
						puntajes.close();
					}
				} catch (NumberFormatException | UsuarioNoExisteException e) {
					Main.mostrar(e.getMessage());
				}
			}
		}else {
			dialog.setContentText("Escribe el puntaje del usuario que quieres buscar");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
				try {
					puntajes.setContentText(Main.getGame().busquedaBinaria(Integer.parseInt(result.get())));
					Optional<ButtonType> result2=puntajes.showAndWait();
					if(result2.get()==aceptar) {
						puntajes.close();
					}
				} catch (NumberFormatException | PuntajeNoExiste e) {
					Main.mostrar(e.getMessage());
				}
			}
		}	
	}
	
	public void seleccionarBusqueda() {
		Alert seleccionarBusqueda=new Alert(AlertType.CONFIRMATION);
		seleccionarBusqueda.setContentText("Seleccione el tipo de busqueda");
		seleccionarBusqueda.setHeaderText(null);
		seleccionarBusqueda.getButtonTypes().clear();
		
		ButtonType nombre=new ButtonType("Buscar por nombre");
		ButtonType puntos=new ButtonType("Buscar por puntaje");
		
		seleccionarBusqueda.getButtonTypes().addAll(nombre, puntos);
		Optional<ButtonType> result=seleccionarBusqueda.showAndWait();
		if(result.get()==nombre) {
			buscar(1);
		}else {
			buscar(-1);
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