package Rip912;

import robocode.JuniorRobot;

public class Muñeco extends DirectorTecnicoRiver{
    private static Muñeco instance;
    private int minimumEnergy = 60;

    private Muñeco(JuniorRobot robot){
        super(robot);
    }

    public static Muñeco getInstance(JuniorRobot robot) {
        if (instance == null){
            instance = new Muñeco(robot);
        }
        return instance;
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
