package Rip912;
import robocode.*;

public class SapardoMarcelo extends JuniorRobot{

    private Estrategia estrategia;

    @Override
    public void run() {
        setColors(red, white, white, red, black);
        estrategia = new futbolChampagne(this);
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */
    @Override
    public void onScannedRobot() {
        this.estrategia.onScannedRobot();
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    @Override
    public void onHitByBullet() {
        this.estrategia.onHitByBullet();
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    @Override
    public void onHitWall() {
        this.estrategia.onHitWall();
    }
}
