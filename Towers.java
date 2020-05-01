package towers;

public class Towers {
	//Type of Tower:
	protected boolean groundTower;
	protected boolean airTower;
	//General Tower Variables:
	protected int cost;
	protected int range;
	protected int damage;
	protected int posX;
	protected int posY;
	protected int fireDelay;
	protected int curDelay = 0;
	protected int upgradeLevel;
	protected String name;
	//Tower Attacks:
	protected boolean single;
	protected boolean AOE;
	protected boolean chain;
	protected boolean poison;
	//Tower Attack Variables:
	protected int chainEffect;
	
	public Towers() {
	}
	
	public int getDmg() {
		return damage;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getRange() {
		return range;
	}
	
	public int getFireDelay() {
		return fireDelay;
	}
	
	public int getCurDelay() {
		return curDelay;
	}
	
	public void incCurDelay() {
		if (curDelay == fireDelay) {
			curDelay = 0;
		}
		
		else if (curDelay != 0) {
			curDelay++;
		}
	}
	
	public void startDelay() {
		curDelay++;
	}
	
	public int getUpgradeLevel() {
		return upgradeLevel;
	}
	
	public void upgrade() {
		upgradeLevel++;
	}
	
	public void deUpgrade() {
		upgradeLevel--;
	}
	
	public String attackType() {
		if (single) {
			return "single";
		}
		
		else if (chain) {
			return "chain";
		}
		
		else if (AOE) {
			return "AOE";
		}
		
		else if (poison) {
			return "poison";
		}
		return "null";
	}
	
	public int getChainEffect() {
		return chainEffect;
	}
	
	public String toString() {
		StringBuilder data = new StringBuilder();
		data.append("Type: " + name + "\nCost: " + cost
				+ "\nDamage: " + damage + "\nRange: " + range
				+ "\nFireDelay: " + fireDelay);
		return data.toString();
	}
}