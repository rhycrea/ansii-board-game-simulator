
public class Elfs extends Calliance implements InterfaceOfChars{
	
	private int RangedAP = 0;

	public Elfs (int maxHP, int HP, int AP, int maxMove, char attackType, int x, int y, char side,
			char type, String icon, int RangedAP) {
		super( maxHP, HP, AP, maxMove, attackType, x, y, side, type, icon );
		this.RangedAP = RangedAP;
	}
	
	public Elfs () {
		
	}
	
	public int getRangedAP() {
		return RangedAP;
	}
	public void setRangedAP(int rangedAP) {
		RangedAP = rangedAP;
	}

}
