package model;
//type de joueur+type life
public enum Player {
	
	BOY("/resources/boy.png", "/resources/boy_life.png"),
	GIRL("/resources/girl.png", "/resources/girl_life.png");
	
	
	String urlPlayer;
	String urlLife;
	
	private Player(String urlPlayer, String urlLife) {
		this.urlPlayer = urlPlayer;
		this.urlLife = urlLife;
	}
	
	public String getUrl() {
		return this.urlPlayer;
	}
	
	public String getUrlLife() {
		return urlLife;
	}

}
