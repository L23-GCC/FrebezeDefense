package enemies;

/**
 * @author Tom
 * Strong air enemy.
 */
public class CovidEnemy extends Enemies {
	/**
	 * Default constructor.
	 */
	public CovidEnemy() {
		health = 20;
		fast = true;
		worth = 10;
		name = "Covid-19";
		air = true;
	}
}