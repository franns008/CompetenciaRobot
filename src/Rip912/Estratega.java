package Rip912;

public interface Estratega {
    public Estrategia onScannedRobot();

    public Estrategia onHitByBullet();

    public Estrategia onHitWall();

    public Estrategia run();
}
