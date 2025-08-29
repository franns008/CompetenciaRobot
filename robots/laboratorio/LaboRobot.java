package laboratorio;
import robocode.*;


public class LaboRobot extends JuniorRobot
{
    boolean encontre =false;
    boolean volver =false; // Don't turn if there's a robot there
    int moveAmount; // How much to move


    @Override
    public void run() {
        setColors(orange, blue, white, yellow, black);
        // Initialize moveAmount to the maximum possible for this battlefield.
        moveAmount = Math.max(fieldWidth , fieldHeight );

        // turnLeft to face a wall.
        // getHeading() % 90 means the remainder of
        // getHeading() divided by 90.
        turnLeft(heading % 90);
        ahead(moveAmount);
        // Turn the gun to turn right 90 degrees.;
        turnGunRight(90);
        turnRight(90);

        while (true) {
            // Look before we turn when ahead() completes.
            if (volver) {
                back(moveAmount);
                out.println("field "+moveAmount);
            }
            if(encontre){
                turnGunTo(gunHeading-15);
            }
            // Move up the wall
            ahead(moveAmount);
            // Don't look now

        }
    }

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	@Override
	public void onScannedRobot() {
        turnGunTo(scannedAngle);
        fire(2);
        encontre = true;
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	@Override
	public void onHitByBullet() {
        if (hitByBulletBearing < 10){
            turnGunLeft(90);
        }else if (hitByBulletBearing < 180 && hitByBulletBearing > 170){
        }

	}

	/**
	 * onHitWall: What to do when you hit a wall
	 */
	@Override
	public void onHitWall() {
        volver = !volver;
        out.println("toque pared");
	}	
}
