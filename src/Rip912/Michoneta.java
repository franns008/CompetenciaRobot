package Rip912;

import robocode.JuniorRobot;

public class Michoneta extends Estratega{
    private JuniorRobot robot;
    private int minimumNumberOfRobots;

    public Michoneta(JuniorRobot robot){
        this.robot = robot;
        this.minimumNumberOfRobots = 6;
    }

    @Override
    public Estrategia run() {
        if(this.robot.others<minimumNumberOfRobots){
            return this.ponerAguantarElPartido(robot);
        }
        return this.ponerFutbolChampagne(robot);
    }

    @Override
    public Estrategia onHitWall() {
        if(this.robot.others<minimumNumberOfRobots){
            return this.ponerAguantarElPartido(robot);
        }
        return this.ponerFutbolChampagne(robot);
    }

    @Override
    public Estrategia onScannedRobot() {

        return this.ponerAguantarElPartido(robot);

    }

    @Override
    public Estrategia onHitByBullet() {
        if(this.robot.others<minimumNumberOfRobots){
            return this.ponerAguantarElPartido(robot);
        }
        return this.ponerFutbolChampagne(robot);
    }
}
