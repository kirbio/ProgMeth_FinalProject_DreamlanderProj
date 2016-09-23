package pokemon;

import java.util.Random;

public class Pokemon {
	private String species;
	private double cp;
	private boolean shiny; 
	private String shininess = "";
	public Pokemon(double cp){
		species = "MissingNo.";
		this.cp = cp;
		GenerateShiny();
	}
	public Pokemon(String species,double cp){
		this.species = species;
		this.cp = cp;
		GenerateShiny();
	}
	
	public double getCP(){
		return cp;
	}
	public String getSpecies(){
		return species;
	}
	public boolean IsShiny(){
		return shiny;
	}
	public boolean CompareTo(Pokemon p){
		return cp > p.getCP();
	}
	public String toString(){
		return species+shininess+" Lv. "+(int)cp;
	}
	private void GenerateShiny(){
		Random rand = new Random(); 
		int i = rand.nextInt(4096); 
		if(i==0){
			shininess="*";
			shiny = true;
		}else{
			shiny = false;
		}
	}
}
