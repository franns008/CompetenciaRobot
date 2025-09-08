package Rip912;

import robocode.JuniorRobot;

public class Muñeco extends Estratega{
    private JuniorRobot robot;
    private Estrategia estrategia;
    private int minimumEnergy;

    public Muñeco(JuniorRobot robot){
        this.robot = robot;
        this.minimumEnergy = 60;
    }

    @Override
    public Estrategia run() {
        if(this.robot.others<minimumEnergy){
            return this.ponerAguantarElPartido(robot);
        }
        return this.ponerFutbolChampagne(robot);
    }

    @Override
    public Estrategia onHitWall() {
        if(this.robot.others<minimumEnergy){
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
        if(this.robot.others<minimumEnergy){
            return this.ponerAguantarElPartido(robot);
        }
        return this.ponerFutbolChampagne(robot);
    }

}
