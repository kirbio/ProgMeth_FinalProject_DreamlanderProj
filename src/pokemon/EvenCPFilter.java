package pokemon;

public class EvenCPFilter implements Filter{

	@Override
	public boolean checkPokemon(Pokemon p) {
		// TODO Auto-generated method stub
		int pokeCp = (int) p.getCP();
		return pokeCp%2==0;
	}

}
