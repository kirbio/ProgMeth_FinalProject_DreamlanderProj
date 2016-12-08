package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameData {
	class EnemyData{
		private String name;
		private int hp;
		private int atk;
		EnemyData(String name,int hp,int atk){
			this.setName(name);
			this.setHp(hp);
			this.setAtk(atk);
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getHp() {
			return hp;
		}
		public void setHp(int hp) {
			this.hp = hp;
		}
		public int getAtk() {
			return atk;
		}
		public void setAtk(int atk) {
			this.atk = atk;
		}
	}
	
	static ArrayList<EnemyData> enemyDatas = new ArrayList<EnemyData>();
	static ArrayList<LevelData> levelDatas = new ArrayList<LevelData>();
	static ArrayList<LevelData> levelMetaDatas = new ArrayList<LevelData>();
	
	public GameData(){ 
		/*Constructor, call once in Main at initiallize 
		 * to parse the file to local variable
		 */
		Boolean fin = false;
		try {
			Scanner s = new Scanner(new File("enemydata.csv"));
			s.nextLine();
			while(!fin){
				String rawdata = s.nextLine();
				String[] parseddata = rawdata.split(",");
				EnemyData e  = new EnemyData(parseddata[0],Integer.parseInt(parseddata[1]),Integer.parseInt(parseddata[2]));
				enemyDatas.add(e);
				
				if(!s.hasNextLine()){
					fin = true;
				}
			}
			s.close();
			System.out.println("--Enemy Data Parsing Complete--");
		} catch (FileNotFoundException e) {
			System.out.println("--Error: Enemy Data File not found--");
		}
		fin = false;
		try {
			Scanner s = new Scanner(new File("leveldata.csv"));
			s.nextLine(); //Skip First Line
			while(!fin){
				String rawdata = s.nextLine();
				//System.out.println(rawdata);
				String[] parseddata = rawdata.split(",");
				LevelData lvd  = new LevelData();
				String[] levelmeta = parseddata[0].split("#");
				if(levelmeta.length>1){
					int[] attack;
					attack = new int[levelmeta.length-1];
					for(int i=0;i<levelmeta.length-1;i++){	
						attack[i] = Integer.parseInt(levelmeta[i]);
					}
					lvd.setAttackGuage(attack);
					lvd.setAttackSpeed(Integer.parseInt(levelmeta[levelmeta.length-1]));
				}else{
					lvd.setAttackSpeed(Integer.parseInt(levelmeta[0]));
				}
				
				
				parseddata[0]="";
				for(String str: parseddata){
					if(str!=""){
						String[] splitpair = str.split("n"); //Split each pair with delimiter "n"
						lvd.addEncounter(Integer.parseInt(splitpair[0]),Integer.parseInt(splitpair[1]));
					}
				}
				levelDatas.add(lvd);
				
				if(!s.hasNextLine()){
					fin = true;
				}
			}
			s.close();
			System.out.println("--Level Data Parsing Complete--");
		} catch (FileNotFoundException e) {
			System.out.println("--Error: Level Data File not found--");
		}
		
	}
	/*=================================================
	 * Enemy-Related Data
	 ==================================================*/
	//Return Enemy Name, index is based on CSV sheet
	public static String getEnemyName(int index){
		return enemyDatas.get(index).getName();
	}
	
	//Return Enemy HP, index is based on CSV sheet
	public static int getEnemyHp(int index){
		return enemyDatas.get(index).getHp();
	}
	
	//Return Enemy Atk, index is based on CSV sheet
	public static int getEnemyAtk(int index){
		return enemyDatas.get(index).getAtk();
	}
	/*=================================================
	 * Level-Related Data
	 ==================================================*/
	//Return Enemy Atk, index is based on CSV sheet
		public static ArrayList<EnemyEncounterPair> getEnemyList(int index){
			return levelDatas.get(index).getEncounterList();
		}
		
		public static int[] getAttackGuageType(int index){
			return levelDatas.get(index).getAttackGuage();
		}
		
		public static int getAttackGuageSpeed(int index){
			return levelDatas.get(index).getAttackSpeed();
		}
	
	
}
