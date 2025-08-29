package laboratorio;
import robocode.*;


public class LaboRobot extends JuniorRobot
{
    boolean peek; // Don't turn if there's a robot there
    int moveAmount; // How much to move


    @Override
	public void run() {
		setColors(orange, blue, white, yellow, black);
        // Initialize moveAmount to the maximum possible for this battlefield.
        moveAmount = Math.max(fieldWidth , fieldHeight );
        // Initialize peek to false
        peek = false;
        // turnLeft to face a wall.
        // getHeading() % 90 means the remainder of
        // getHeading() divided by 90.
        turnLeft(heading % 90);
        ahead(moveAmount);
        // Turn the gun to turn right 90 degrees.
        peek = true;
        turnGunRight(90);
        turnRight(90);

        while (true) {
            // Look before we turn when ahead() completes.
            peek = true;
            // Move up the wall
            ahead(moveAmount);
            // Don't look now
            peek = false;
            // Turn to the next wall
            turnRight(90);
        }

	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	@Override
	public void onScannedRobot() {
        turnGunTo(scannedAngle);
        fire(2);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	@Override
	public void onHitByBullet() {
        back(100);
        ahead(50);
        back(20);

	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	@Override
	public void onHitWall() {


	}	
}
