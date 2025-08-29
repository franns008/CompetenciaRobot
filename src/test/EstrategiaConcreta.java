package test;

import robocode.JuniorRobot;

public class EstrategiaConcreta implements Estrategia{
    private JuniorRobot robot;

    public EstrategiaConcreta (JuniorRobot robot){
        this.robot =robot;
    }

    @Override
    public void onHitByBullet() {
        robot.ahead(10000);
    }

    @Override
    public void onHitWall() {

    }

    @Override
    public void onScannedRobot() {

    }
}
