package enemies;

/**
 * @author Tom
 * Basic air enemy.
 */
public class FluEnemy extends Enemies {
	/**
	 * Default constructor.
	 */
	public FluEnemy() {
		health = 10;
		fast = false;
		worth = 5;
		name = "Influenza";
		air = true;
	}
}