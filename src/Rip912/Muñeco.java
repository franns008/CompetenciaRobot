package Rip912;

import robocode.JuniorRobot;

public class Muñeco extends DirectorTecnicoRiver{

    private Estrategia estrategia;
    private int minimumEnergy = 60;

    public Muñeco(JuniorRobot robot){
        super(robot);
    }

    @Override
    public Estrategia run() {
        if(this.robot.energy<minimumEnergy){
            System.out.println("entre en energia minima");
            return this.aguantar;
        }
        return this.champagne;
    }

    @Override
    public Estrategia onHitWall() {
        if(this.robot.energy<minimumEnergy ){
            return this.aguantar;
        }
        return this.champagne;
    }

    @Override
    public Estrategia onScannedRobot() {
        return this.aguantar;
    }

    @Override
    public Estrategia onHitByBullet() {
        if(this.robot.energy<minimumEnergy){
            return this.aguantar;
        }
        return this.champagne;
    }

}
