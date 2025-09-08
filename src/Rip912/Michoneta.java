package Rip912;

import robocode.JuniorRobot;

public class Michoneta implements Estratega{

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
