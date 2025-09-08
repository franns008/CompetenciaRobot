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
        estratega = new Muñeco(this);
        while(true) {
            estrategia = estratega.run();
            estrategia.run();
        }
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


   /* private static class Michoneta implements Estratega{
        private Estrategia estrategiaAguantar;
        private Estrategia estrategiaChampagne;
        private JuniorRobot robot;
        private static Michoneta instance;
        private final int minimumNumberOfRobots = 6;

        private Michoneta(JuniorRobot robot) {
            this.robot = robot;
            this.estrategiaChampagne = new FutbolChampagne(robot);
            this.estrategiaAguantar = new AguantarElPartido(robot);

        }

        public static Michoneta getInstance(JuniorRobot robot) {
            if (instance == null) {
                instance = new Michoneta(robot);
            }
            return instance;
        }

        @Override
        public Estrategia onScannedRobot() {
            if(this.robot.others<minimumNumberOfRobots){
                return estrategiaAguantar;
            }
            return estrategiaChampagne;
        }

        @Override
        public Estrategia onHitByBullet() {
            if(this.robot.others<minimumNumberOfRobots){
                return estrategiaAguantar;
            }
            return estrategiaChampagne;
        }

        @Override
        public Estrategia onHitWall() {
            if(this.robot.others<minimumNumberOfRobots){
                return estrategiaAguantar;
            }
            return estrategiaChampagne;
        }

        @Override
        public Estrategia run() {
            if(this.robot.others<minimumNumberOfRobots){
                return estrategiaAguantar;
            }
            return estrategiaChampagne;
        }



    }

    private static class Muñeco implements Estratega {
        private Estrategia estrategiaAguantar;
        private Estrategia estrategiaChampagne;
        private JuniorRobot robot;
        private static Muñeco instance;
        private final int minimumEnergy = 60;

        private Muñeco(JuniorRobot robot) {
            this.robot = robot;
            this.estrategiaChampagne = new FutbolChampagne(robot);
            this.estrategiaAguantar = new AguantarElPartido(robot);

        }
        @Override
        public Estrategia onScannedRobot() {
            return this.estrategiaAguantar;
        }

        public static Muñeco getInstance(JuniorRobot robot) {
            if (instance == null || instance.robot != robot) {
                instance = new Muñeco(robot);
            }
            return instance;
        }


        @Override
        public Estrategia onHitByBullet() {
            if(this.robot.energy > minimumEnergy){
                return this.estrategiaChampagne;
            }
            return this.estrategiaAguantar;
        }

        @Override
        public Estrategia onHitWall() {
            if(this.robot.energy >minimumEnergy){
                return this.estrategiaChampagne;
            }
            return this.estrategiaAguantar;
        }

        @Override
        public Estrategia run() {
            if(this.robot.energy >minimumEnergy ){
                return this.estrategiaChampagne;

            }
            return this.estrategiaAguantar;
        }

        private boolean estoyEnPared(){
            if ((this.robot.robotY == 0) || (this.robot.robotX == 0)
                    || (this.robot.robotX == this.robot.fieldWidth) || (this.robot.robotY == this.robot.fieldHeight)){
                return true;
            }
            return false;
        }
    }

    */

}
