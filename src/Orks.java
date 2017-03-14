
public class Orks extends Zorde implements InterfaceOfChars {
	private int HealPoint = 0;

	public Orks (int maxHP, int HP, int AP, int maxMove, char attackType, int x, int y, char side,
			char type, String icon, int HealPoint) {
		super( maxHP, HP, AP, maxMove, attackType, x, y, side, type, icon );
		this.HealPoint = HealPoint;
	}
	
	public Orks () {
		
	}
	
	public int getHealPoint() {
		return HealPoint;
	}

	public void setHealPoint(int healPoint) {
		HealPoint = healPoint;
	}
}
