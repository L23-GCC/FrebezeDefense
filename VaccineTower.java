
public class VaccineTower extends GroundTowers{
	
	//This is like an ice tower, slows down the fast enemies to normal speed.
	
	private boolean slowed; //Freeze variable.
	
	public VaccineTower(int posX, int posY) {
		super(posX, posY);
		cost = 15;
		range = 1;
		damage = 5;
		rateOfFire = 4;
		name = "Vaccine Tower";
		slowed = true;
		
	}

}
