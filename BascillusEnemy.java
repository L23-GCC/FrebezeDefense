package enemies;

/**
 * @author Tom
 * Basic fast ground enemy.
 */
public class BascillusEnemy extends Enemies {
	/**
	 * Default constructor.
	 */
	public BascillusEnemy() {
		health = 5;
		fast = true;
		worth = 2;
		name = "Bascillus";
		air = false;
	}
}