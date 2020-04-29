public class ProbioticTower extends AirTowers{
	public ProbioticTower(int posX, int posY) {
		super(posX, posY);
		cost = 32;
		range = 4;
		damage = 40;
		fireDelay = 6;
		name = "Probiotic Tower";
		single = true;
	}
}