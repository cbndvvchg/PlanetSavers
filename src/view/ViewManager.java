package view;


import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.ScoreLabel;
import model.Player;
import model.PlayerPicker;
import model.ButtonConfiguration;
import model.GameSubScene;


public class ViewManager {
	
	private static final int HEIGHT = 670;
	private static final int WIDTH = 1024;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	private final static int MENU_BUTTON_START_X = 100;
	private final static int MENU_BUTTON_START_Y = 150;
	
	

	private GameSubScene playerChooserSubscene;
	private Image icon = new Image("/resources/logo.png");
	
	
	
	
	List<ButtonConfiguration> menuButtons;
	
	List<PlayerPicker> playerList;
	private Player chosenPlayer;
	//Constructeur 
	public ViewManager() {
		menuButtons = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT );
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		mainStage.setTitle("planetsaver");
		mainStage.getIcons().add(icon);
		createPlayerChooserSubScene();
		CreateButtons();
		createBackground();
		
		
	}
	//Création du menu pour choisir le joueur
		public void createPlayerChooserSubScene() {
			playerChooserSubscene = new GameSubScene();
			mainPane.getChildren().add(playerChooserSubscene);
			ScoreLabel choosePlayerLabel = new ScoreLabel("CHOOSE YOUR PLAYER");
			choosePlayerLabel.setLayoutX(110);
			choosePlayerLabel.setLayoutY(25);
			playerChooserSubscene.getPane().getChildren().add(choosePlayerLabel);
			playerChooserSubscene.getPane().getChildren().add(createPlayerToChoose());
			playerChooserSubscene.getPane().getChildren().add(createButtonToStart());
		}
	
	//Choisir le sexe du joueur
	private HBox createPlayerToChoose() {
		HBox box = new HBox();
		box.setSpacing(60);
		playerList = new ArrayList<>();
		for (Player sexe : Player.values()) {
			PlayerPicker playerToPick = new PlayerPicker(sexe);
			playerList.add(playerToPick);
			box.getChildren().add(playerToPick);
			playerToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					for (PlayerPicker sexe : playerList) {
						sexe.setIsCircleChoosen(false);
					}
					playerToPick.setIsCircleChoosen(true);
					chosenPlayer = playerToPick.getPlayer();
					
				}
			});
		}
		
		box.setLayoutX(300 - (118*2));
		box.setLayoutY(100);
		return box;
	}
	
	
	//Créer le bouton Start et le positionner
	public ButtonConfiguration createButtonToStart() {
		ButtonConfiguration startButton = new ButtonConfiguration("START");
		startButton.setLayoutX(350);
		startButton.setLayoutY(300);
		
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			

			@Override
			public void handle(ActionEvent event) {
				if (chosenPlayer != null) {
					GameViewManager gameManager = new GameViewManager();
					gameManager.createNewGame(mainStage, chosenPlayer);;
				}
				
			}
		});
		
		return startButton;
	}
	//Créer Bouton Exit et le positionner
	public ButtonConfiguration createButtonToExit() {
		ButtonConfiguration exitButton = new ButtonConfiguration("Exit");
		exitButton.setLayoutX(350);
		exitButton.setLayoutY(350);
		
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
				
			}
		});
		return exitButton;
	}
	
	
	
	

//retourner le Mainstage
	public Stage getMainStage() {
		return mainStage;
	}
	//Ajouter les Boutons de menu
	public void AddMenuButtons(ButtonConfiguration button) {
		button.setLayoutX(MENU_BUTTON_START_X);
		button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 100);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	}
	
	
	//Créer les bouttons Start et Exit
	public void CreateButtons() {
		createStartButton();
		createExitButton();
	}
	//Créer le boutton Start et afficher le menu choisir le joueur
	public void createStartButton() {
		ButtonConfiguration startButton = new ButtonConfiguration("PLAY");
		AddMenuButtons(startButton);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				playerChooserSubscene.moveSubScene();;
				
			}
		});
	}
	

	//Créer le Bouton Exit
	public void createExitButton() {
		ButtonConfiguration exitButton = new ButtonConfiguration("EXIT");
		AddMenuButtons(exitButton);
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
				
			}
		});
		
	}
	//Créer le Background
	private void createBackground() {
		Image backgroundImage = new Image("/resources/landSpace.jpg", 0, 0, false, false);
		BackgroundImage background = new BackgroundImage(backgroundImage, null, null, null, null);
		mainPane.setBackground(new Background(background));
	}
	
}
