package enemies;

/**
 * @author Tom
 * Basic slow ground enemy.
 */
public class EColiEnemy extends Enemies {
	/**
	 * Default constructor.
	 */
	public EColiEnemy() {
		health = 10;
		fast = false;
		worth = 1;
		name = "E-Coli";
		air = false;
	}
}
