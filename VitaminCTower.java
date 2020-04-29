
public class VitaminCTower extends GroundTowers {
	
	public VitaminCTower(int x, int y) {
		super(x, y);
		cost = 20;
		range = 2;
		damage = 10;
		fireDelay = 4;
		name = "Vitamin C Tower";
		chain = true;
		chainEffect = 2;
	}

}
