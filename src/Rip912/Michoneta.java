package Rip912;

import robocode.JuniorRobot;

public class Michoneta extends DirectorTecnicoRiver{

    private final int minimumNumberOfRobots = 6;

    public Michoneta(JuniorRobot robot){
        super(robot);
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
