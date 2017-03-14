
public interface InterfaceOfChars {
	public int HP = 0;
	public int MP = 0;
	public int x = 0;
	public int y = 0;
	public char side = '\0';
	public char type = '\0';
	public String icon = null;

	
	public int getMaxHP();
	public void setMaxHP(int maxHP);
	public int getHP();
	public void setHP(int HP);
	public int getAP();
	public void setAP(int AP);
	public int getX();
	public void setX(int x);
	public int getY();
	public void setY(int y);
	public char getSide();
	public void setSide(char side);
	public char getType();
	public void setType(char type);
	public String getIcon();
	public void setIcon(String icon);
}
