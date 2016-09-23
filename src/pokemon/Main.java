package pokemon;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] pokeList = {"Psyduck","Zoroark","Pikachu","Lycanroc","Oddish","Bellsprout","Flabebe","Hoppip"};
		//ArrayList<Pokemon> newList =  Main.retain(pokeList,  new StrongThanFilter(new Pokemon(13)));
		Horde testHorde = new Horde(pokeList,29,35);
		for(Pokemon i:testHorde.getHorde()){
			System.out.println(i);
		}
	}
	
	public static ArrayList<Pokemon> remove(ArrayList<Pokemon> pokemon_list,  Filter filter){
		ArrayList<Pokemon> newpokeList = new ArrayList<Pokemon>();
		for(Pokemon i:pokemon_list){
			if(!filter.checkPokemon(i)){
				newpokeList.add(i);
			}
		}
		return newpokeList;
		
	}
	
	public static ArrayList<Pokemon> retain(ArrayList<Pokemon> pokemon_list, Filter filter){
		ArrayList<Pokemon> newpokeList = new ArrayList<Pokemon>();
		for(Pokemon i:pokemon_list){
			if(filter.checkPokemon(i)){
				newpokeList.add(i);
			}
		}
		return newpokeList;
		
	}

}
