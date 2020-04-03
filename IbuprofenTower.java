
public class IbuprofenTower extends GroundTowers{
	public IbuprofenTower(int posX, int posY){
		super(posX, posY);
		cost = 10;
		range = 3;
		damage = 6;
		rateOfFire = 3;
		name = "Ibuprofen Tower";
	}
	
	public String toString() {
		StringBuilder data = new StringBuilder();
		data.append("Type: " + name + "\nCost: " + cost
				+ "\nDamage: " + damage + "\nRange: " + range
				+ "\n RoF: " + rateOfFire);
		return data.toString();
	}
}
