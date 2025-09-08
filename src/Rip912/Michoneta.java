package Rip912;

import robocode.JuniorRobot;

public class Michoneta extends DirectorTecnicoRiver{

    private static Michoneta instance;
    private final int minimumNumberOfRobots = 6;

    private Michoneta(JuniorRobot robot){
        super(robot);
    }

    public static Michoneta getInstance(JuniorRobot robot){
        if (instance == null){
            instance = new Michoneta(robot);
        }
        return instance;
    }



    @Override
    public Estrategia run() {
        if(this.robot.others<minimumNumberOfRobots){
            return this.aguantar;
        }
        return this.champagne;
    }

    @Override
    public Estrategia onHitWall() {
        if(this.robot.others<minimumNumberOfRobots){
            return this.aguantar;
        }
        return this.aguantar;
    }

    @Override
    public Estrategia onScannedRobot() {

        return this.aguantar;

    }

    @Override
    public Estrategia onHitByBullet() {
        if(this.robot.others<minimumNumberOfRobots){
            return this.aguantar;
        }
        return this.champagne;
    }
}
