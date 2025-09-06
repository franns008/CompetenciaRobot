package Rip912;
import robocode.*;

// Suarez Francisco
// Bicocchi Damián
public class SapardoMarcelo extends JuniorRobot{

    private Estratega estratega; //deprecated
    private Estrategia estrategia;


    @Override
    public void run() {
        setColors(red, white, white, red, black);
        estratega = new Michoneta(this);
        estrategia = estratega.run();
        estrategia.run();
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */
    @Override
    public void onScannedRobot() {

        estrategia =this.estratega.onScannedRobot();
        estrategia.onScannedRobot();
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    @Override
    public void onHitByBullet() {

        estrategia = this.estratega.onHitByBullet();
        estrategia.onHitByBullet();
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    @Override
    public void onHitWall() {

        estrategia = this.estratega.onHitWall();
        estrategia.onHitWall();
    }


    private class Michoneta implements Estratega{
        private Estrategia estrategiaAguntar;
        private Estrategia estrategiaChampagne;
        private JuniorRobot robot;

        public Michoneta(JuniorRobot robot) {
            this.robot = robot;
            this.estrategiaChampagne = new FutbolChampagne(robot);
            this.estrategiaAguntar = new AguantarElPartido(robot);

        }

        @Override
        public Estrategia onScannedRobot() {
            if(this.robot.others<6){
                return estrategiaAguntar;
            }
            return estrategiaChampagne;
        }

        @Override
        public Estrategia onHitByBullet() {
            if(this.robot.others<6){
                return estrategiaAguntar;
            }
            return estrategiaChampagne;
        }

        @Override
        public Estrategia onHitWall() {
            if(this.robot.others<6){
                return estrategiaAguntar;
            }
            return estrategiaChampagne;
        }

        @Override
        public Estrategia run() {
            if(this.robot.others<6){
                return estrategiaAguntar;
            }
            return estrategiaChampagne;
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
