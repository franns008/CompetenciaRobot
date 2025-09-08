package Rip912;

import robocode.JuniorRobot;

public class Muñeco extends Estratega{
    private JuniorRobot robot;
    private Estrategia estrategia;
    private int minimumEnergy = 60;

    public Muñeco(JuniorRobot robot){
        this.robot = robot;
    }

    @Override
    public Estrategia run() {
        if(this.robot.energy<minimumEnergy){
            System.out.println("entre en energia minima");
            return this.ponerAguantarElPartido(robot);
        }
        return this.ponerFutbolChampagne(robot);
    }

    @Override
    public Estrategia onHitWall() {
        if(this.robot.energy<minimumEnergy ){
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
        if(this.robot.energy<minimumEnergy){
            return this.ponerAguantarElPartido(robot);
        }
        return this.ponerFutbolChampagne(robot);
    }

}
