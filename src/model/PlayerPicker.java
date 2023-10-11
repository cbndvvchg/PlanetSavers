package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class PlayerPicker extends VBox {

	private ImageView circleImage;
	private ImageView playerImage;
	
	private String circleNotChoosen = "/resources/grey_circle.png";
	private String circleChoosen = "/resources/red_choosen.png";
	
	private Player player;
	
	private boolean isCircleChoosen;
	
	//Constructeur
	public PlayerPicker(Player player) {
		circleImage = new ImageView(circleNotChoosen);
		playerImage = new ImageView(player.getUrl());
		this.player = player;
		isCircleChoosen = false;
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		this.getChildren().add(circleImage);
		this.getChildren().add(playerImage);
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public boolean getCircleChoosen() {
		return isCircleChoosen;
	}
	
	public void setIsCircleChoosen(boolean isCircleChoosen) {
		this.isCircleChoosen = isCircleChoosen;
		String imageToSet = this.isCircleChoosen ? circleChoosen : circleNotChoosen;
		circleImage.setImage(new Image(imageToSet));
	}
}
