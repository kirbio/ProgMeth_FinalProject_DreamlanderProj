package pokemon;

import java.util.Random;

public class Horde {
	String[] pokeArr;
	Pokemon[] genArr = new Pokemon[5]; 
	int levelmin;
	int levelmax;
	public Horde(String[] pokeArr,int levelmin,int levelmax){
		this.pokeArr = pokeArr;
		this.levelmin = levelmin;
		this.levelmax = levelmax;
		Generate();
	}
	
	public void Generate(){
		Random rand = new Random(); 
		String species;
		for(int i =0;i<5;i++){
			int chosen = rand.nextInt(pokeArr.length);
			species = pokeArr[chosen];
			genArr[i]=new Pokemon(species,rand.nextInt(levelmax-levelmin)+levelmin);
		}
		
	}
	
	public Pokemon[] getHorde(){
		return genArr;
	}
}
