public class Characters {

	private int maxHP;
	private int HP;
	private int AP;
	private int maxMove;
	private char attackType; // s:attacks at every [s]tep     f:attacks at [f]inal.
	private int x;
	private int y;
	private char side; // c:calliance    z:zorde.
	private char type; // h:human e:elf d:dwarf o:ork t:troll g:goblin.
	private String icon;

	public Characters(int maxHP, int HP, int AP, int maxMove, char attackType, int x, int y, char side,
			char type, String icon) {
		this.maxHP = maxHP;
		this.HP = HP;
		this.AP = AP;
		this.maxMove = maxMove;
		this.attackType = attackType;
		this.x = x;
		this.y = y;
		this.side = side;
		this.type = type;
		this.icon = icon;
	}

	public Characters() {

	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int HP) {
		this.HP = HP;
	}

	public int getAP() {
		return AP;
	}

	public void setAP(int AP) {
		this.AP = AP;
	}

	public int getMaxMove() {
		return maxMove;
	}

	public void setMaxMove(int maxMove) {
		this.maxMove = maxMove;
	}

	public char getAttackType() {
		return attackType;
	}

	public void setAttackType(char attackType) {
		this.attackType = attackType;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public char getSide() {
		return side;
	}

	public void setSide(char side) {
		this.side = side;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
