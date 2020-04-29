public class VitaminDTower extends AirTowers{
	public VitaminDTower(int posX, int posY) {
		super(posX, posY);
		cost = 22;
		range = 2;
		damage = 10;
		fireDelay = 4;
		name = "D Tower";
		chain = true;
		chainEffect = 2;
	}
}