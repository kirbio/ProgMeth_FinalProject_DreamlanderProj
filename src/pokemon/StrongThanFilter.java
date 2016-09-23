package pokemon;

public class StrongThanFilter implements Filter{
	Pokemon selfP;
	public StrongThanFilter(Pokemon p){
		selfP = p;
	}
	@Override
	public boolean checkPokemon(Pokemon p) {
		return p.CompareTo(selfP);
	}

}
