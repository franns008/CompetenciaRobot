package Rip912;
import robocode.*;

// Suarez Francisco
// Bicocchi Damián
public class SapardoMarcelo extends JuniorRobot{

    private Estrategia estrategia; //deprecated



    @Override
    public void run() {
        setColors(red, white, white, red, black);
        estrategia = new AguantarElPartido(this);
        estrategia.run();
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


    private class Michoneta implements Estrategia{
        private JuniorRobot robot;
        @Override
        public void onScannedRobot() {

        }

        @Override
        public void onHitByBullet() {

        }

        @Override
        public void onHitWall() {

        }

        @Override
        public void run() {

        }

        public Michoneta(JuniorRobot robot) {
            this.robot = robot;
        }
    }

    private class Muñeco implements Estrategia {

        @Override
        public void onScannedRobot() {

        }

        @Override
        public void onHitByBullet() {

        }

        @Override
        public void onHitWall() {

        }

        @Override
        public void run() {

        }
    }
}
