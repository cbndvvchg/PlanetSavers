package view;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Player;
import model.SmallInfoLabel;
import model.ButtonConfiguration;

public class GameViewManager {
	
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	
	private static final int GAME_WIDTH = 600;
	private static final int GAME_HEIGHT = 700;
	
	private Stage menuStage;
	private ImageView player;
	
	private boolean isLeftKeyPressed;
	private boolean isRightKeyPressed;
	private AnimationTimer gameTimer;
	private int angle;
	
	private GridPane gridPane1;
	private final static String BACKGROUND_IMAGE = "/resources/landSpace.jpg";
	private final static String BACKGROUND_IMAGE_GAME_OVER = "/resources/landSpaceGameOver.jpg"; 
	
	
	private final static String NUCLEAIRE_IMAGE = "/resources/nucleaire.png";
	private final static String TRASH_IMAGE = "/resources/trash.png";
	private final static String RECYCLAGE_IMAGE = "/resources/recyclage.png";
	
	
	 
	private ImageView[] nucleaires;
	private ImageView[] trashes;
	private ImageView[] recyclages;
	private Image icon = new Image("/resources/logo.png");

	Random randomPositionGenerator;
	
	
	private ImageView water;
	private SmallInfoLabel pointsLabel;
	private ImageView[] playerLifes;
	private int playerLife;
	private int points;
	private final static String water_icon = "/resources/water2.png";
	
	private final static int ECO_Friendly_RADIUS = 12;
	private final static int PLAYER_RADIUS = 27;
	private final static int NON_ECO_Friendly_RADIUS = 20;
	//Constructeur
	public GameViewManager() {
		initializeStage();
		createKeyListeners();
		randomPositionGenerator = new Random();
		
	}
	//Déplacement du joueur
	private void createKeyListeners() {
		
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = true;
					
				} else if (event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = true;
				}
				
			}
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = false;
					
				} else if (event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = false;
					
				}
				
			}
		});
	}
	
	//initialiser l'AnchorPane , le stage et la scene 
	private void initializeStage() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
		gameStage = new Stage();
		gameStage.setTitle("planetsaver");
		gameStage.getIcons().add(icon);
		gameStage.setScene(gameScene);
	}
	
	//masquer le menu et lancer le jeu 
	public void createNewGame(Stage menuStage, Player choosenPlayer) {
	
		this.menuStage = menuStage;
		this.menuStage.hide();
		createBackground(BACKGROUND_IMAGE);
		createPlayer(choosenPlayer);
		createGameElements(choosenPlayer);
		createGameLoop();
		gameStage.show();
	}
	//créer la scene du GameOver
	public void createGameOver() {
		createBackground(BACKGROUND_IMAGE_GAME_OVER);
		gameStage.show();
	}
	//Créer les élements du jeux ( Score , objets tombant , Vies )
	private void createGameElements(Player choosenPlayer) {
		
		playerLife = 2;
		water = new ImageView(water_icon);
		setNewElementPosition(water);
		gamePane.getChildren().add(water);
		pointsLabel = new SmallInfoLabel("Scores:00");
		pointsLabel.setLayoutX(460);
		pointsLabel.setLayoutY(20);
		gamePane.getChildren().add(pointsLabel);
		playerLifes = new ImageView[3];
		
		for(int i = 0; i < playerLifes.length; i++) {
			playerLifes[i] = new ImageView(choosenPlayer.getUrlLife());
			playerLifes[i].setLayoutX(455 + (i * 50));
			playerLifes[i].setLayoutY(80);
			gamePane.getChildren().add(playerLifes[i]);
			
		}
		
		
		nucleaires = new ImageView[3];
		for(int i = 0; i < nucleaires.length; i++) {
			nucleaires[i] = new ImageView(NUCLEAIRE_IMAGE);
			setNewElementPosition(nucleaires[i]);
			gamePane.getChildren().add(nucleaires[i]);
		}
		trashes = new ImageView[3];
		for(int i = 0; i < trashes.length; i++) {
			trashes[i] = new ImageView(TRASH_IMAGE);
			setNewElementPosition(trashes[i]);
			gamePane.getChildren().add(trashes[i]);
		}
		
		recyclages = new ImageView[1];
		for(int i = 0; i < recyclages.length; i++) {
			recyclages[i] = new ImageView(RECYCLAGE_IMAGE);
			setNewElementPosition(recyclages[i]);
			gamePane.getChildren().add(recyclages[i]);
		}

	}
	
	//Faire tomber les objets
	private void moveGameElements() {
	
		water.setLayoutY(water.getLayoutY() + 5);
		
		for(int i = 0; i < nucleaires.length; i++) {
			nucleaires[i].setLayoutY(nucleaires[i].getLayoutY()+5);
			nucleaires[i].setRotate(nucleaires[i].getRotate());
		}
		
		for(int i = 0; i < trashes.length; i++) {
			trashes[i].setLayoutY(trashes[i].getLayoutY()+5);
			trashes[i].setRotate(trashes[i].getRotate());
		}
		
		for(int i = 0; i < recyclages.length; i++) {
			recyclages[i].setLayoutY(recyclages[i].getLayoutY()+7);
			recyclages[i].setRotate(recyclages[i].getRotate()+5);
		}
	}
	//Ajouter des objets pour qu'ils remplaces les objets qui ont dissparu 
	private void checkIfElementAreBehindThePlayerAndRelocated() {
		
		if(water.getLayoutY() > 1200) {
			setNewElementPosition(water);
		}
		
		for(int i = 0; i< nucleaires.length; i++) {
			if(nucleaires[i].getLayoutY() > 900) {
				setNewElementPosition(nucleaires[i]);
			}
		}
		
		
		for(int i = 0; i< trashes.length; i++) {
			if(trashes[i].getLayoutY() > 900) {
				setNewElementPosition(trashes[i]);
			}
		}
		for(int i = 0; i< recyclages.length; i++) {
			if(recyclages[i].getLayoutY() > 900) {
				setNewElementPosition(recyclages[i]);
			}
		}
	}
	
	
	
	
	//Positionner les objets tombant aléatoirement
	private void setNewElementPosition(ImageView image) {
		
		image.setLayoutX(randomPositionGenerator.nextInt(370));
		image.setLayoutY(-randomPositionGenerator.nextInt(3200)+600);
		
	}
	
	

	
	//Créer le joueur et le positionner 
	private void createPlayer(Player choosenPlayer) {
		player = new ImageView(choosenPlayer.getUrl());
		player.setLayoutX(GAME_WIDTH/2);
		player.setLayoutY(GAME_HEIGHT - 90);
		gamePane.getChildren().add(player);
	}
	
	//Animer le jeu , donner la notion du temps au jeu 
	private void createGameLoop() {
		gameTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				moveGameElements();
				checkIfElementAreBehindThePlayerAndRelocated();
				checkIfElementsCollide();
				movePlayer();
				
			}
			
		};
		gameTimer.start();
	}
	//Déplacer le joueur
	private void movePlayer() {
		
		if (isLeftKeyPressed && !isRightKeyPressed) {
			if(angle > -30) {
				angle -= 5;
			}
			player.setRotate(angle);
			if(player.getLayoutX() > -20) {
				player.setLayoutX(player.getLayoutX() - 5);
			}
		}
		
		if (isRightKeyPressed && !isLeftKeyPressed) {
			if(angle < 30) {
				angle += 5;
			}
			player.setRotate(angle);
			if(player.getLayoutX() < 522) {
				player.setLayoutX(player.getLayoutX() + 5);
			}
		}
		
		if (!isLeftKeyPressed && !isRightKeyPressed) {
			if(angle < 0) {
				angle += 5;
			} else if (angle > 0) {
				angle -= 5;
			}
			player.setRotate(angle);
		}
		
		if (isLeftKeyPressed &&  isRightKeyPressed) {
			if(angle < 0) {
				angle += 5;
			} else if (angle > 0) {
				angle -= 5;
			}
			player.setRotate(angle);
		}
		
	}
	//Créer l'image d'arrière plan
	private void createBackground(String url) {
		gridPane1 = new GridPane();	
		for (int i = 0 ; i < 12; i++) {
			ImageView backgroundImage1 = new ImageView(url);
			GridPane.setConstraints(backgroundImage1, i, i );
			gridPane1.getChildren().add(backgroundImage1);
		}
		gamePane.getChildren().add(gridPane1);

	}
	//voir si le joueur touche les objets tombant et augmenter le score 
	private void checkIfElementsCollide() {
		
		if(PLAYER_RADIUS + ECO_Friendly_RADIUS > calculateDistance(player.getLayoutX(), water.getLayoutX(),
				player.getLayoutY(), water.getLayoutY())) {
			setNewElementPosition(water);
			
			points++;
			pointsLabel.setText(""+ points);
		}
		
		for (int i = 0; i < nucleaires.length; i++) {
			
			if (NON_ECO_Friendly_RADIUS + PLAYER_RADIUS > calculateDistance(player.getLayoutX(), nucleaires[i].getLayoutX(),
					player.getLayoutY(), nucleaires[i].getLayoutY())) {
				
				removeLife();
				setNewElementPosition(nucleaires[i]);
			}
			
		}
		
		for (int i = 0; i < trashes.length; i++) {
			
			if (NON_ECO_Friendly_RADIUS + PLAYER_RADIUS > calculateDistance(player.getLayoutX(), trashes[i].getLayoutX(),
					player.getLayoutY(), trashes[i].getLayoutY())) {
				
				removeLife();
				setNewElementPosition(trashes[i]);
			}
			
		}
		
        for (int i = 0; i < recyclages.length; i++) {
			
			if (ECO_Friendly_RADIUS + PLAYER_RADIUS > calculateDistance(player.getLayoutX(), recyclages[i].getLayoutX(),
					player.getLayoutY(), recyclages[i].getLayoutY())) {
				setNewElementPosition(recyclages[i]);
				
				
				 if(playerLife <2) {
					 playerLife++;
					gamePane.getChildren().add(playerLifes[playerLife]);
					
				}
				else if(playerLife==2) {
					points=points +10;
					pointsLabel.setText(""+ points);
				}
				else {
					removeLife();
				}
			}
			
		}
	}
	
	//diminuer les vies et mettre fin au jeu quand les vies finissent
	private void removeLife() {
		
		gamePane.getChildren().remove(playerLifes[playerLife]);
		
		playerLife--;
		if(playerLife < 0) {
			gameTimer.stop();
			createGameOver();
			pointsLabel = new SmallInfoLabel(""+points);
			pointsLabel.setLayoutX(460);
			pointsLabel.setLayoutY(20);
			gamePane.getChildren().add(pointsLabel);
			gamePane.getChildren().add(createButtonToExit());
			ImageView logo = new ImageView("/resources/gameOver.png");
			logo.setLayoutX(60);
			logo.setLayoutY(270);
			gamePane.getChildren().add(logo);
		}
	}
	//Ajouter le bouton Exit a la fin du jeu
	public ButtonConfiguration createButtonToExit() {
		ButtonConfiguration exitButton = new ButtonConfiguration("Exit");
		exitButton.setLayoutX(350);
		exitButton.setLayoutY(350);
		
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				gameStage.close();
				
			}
		});
		return exitButton;
	}
	
	
	//Méthode pour calculer la distance entre le joueur et les élements tombants
	private double calculateDistance(double x1, double x2, double y1, double y2) {
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}
	
	
	
	
	
	
	
	
	

	
	
	

}
