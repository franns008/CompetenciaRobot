package Rip912;

import robocode.JuniorRobot;

public interface Estrategia {

    void onScannedRobot();

    void onHitByBullet();

    void onHitWall();

    void run();

}


