
public class VaccineTower extends AirTowers{
	
	public VaccineTower(int posX, int posY) {
		super(posX, posY);
		cost = 15;
		range = 2;
		damage = 5;
		fireDelay = 4;
		name = "Vaccine Tower";
		poison = true;
		
	}

}
