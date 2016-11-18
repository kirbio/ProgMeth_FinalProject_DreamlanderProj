package logic;

import java.util.ArrayList;
import java.util.Scanner;

import data.GameData;

public class GameLogic {
	private static boolean isGameOver;
	private static int level;
	private static Player player;
	private static Stage stage;
	
	public GameLogic() {
		isGameOver = false;
		level = 0;
		player = new Player();
		new GameData();
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		GameLogic.isGameOver = isGameOver;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		GameLogic.level = level;
	}
	
	public void startGame() {
		Scanner sc = new Scanner(System.in);
		while(!isGameOver()) {
			stage = new Stage(level);
			stage.addPlayer(player);
			ArrayList<Enemy> enemies = stage.getEnemyList();
			
			
			boolean won = false;
			System.out.println("----------LEVEL "+(level+1)+"----------");
			while(!won) {			
				System.out.println("Press Any Key to Attack");
				sc.next();
				/*int counter = 0;
				int count = 1;
				while(true) {
					System.out.println(""+counter);
					if (sc.next() != null) {
						player.setAttack(counter);
						break;
					}
					counter += count;
					if (counter == 10 || counter == 0) {
						count *= -1;
					}
				}*/
				
				
				for (Enemy e : enemies) {
					if (!e.isDead()) {
						player.attack(e);
					}
				}
				
				won = enemies.get(0).isDead();
				
				for (Enemy e : enemies) {
					if(player.isDead()){
						setGameOver(true);
						System.out.println("Game Over");
						break;
					}
					//System.out.println(e.getName()+" "+e.getHp()+" dead : "+e.isDead());
					if (!e.isDead()) {
						e.attack(player);
					}
					won = won && e.isDead();
				}
				if(isGameOver()){
					break;
				}
			}
			if(!isGameOver()){
			System.out.println("You won!");
			level++;
			}
		}	
		sc.close();
	}
}