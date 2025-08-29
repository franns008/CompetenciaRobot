package Rip912;

import robocode.JuniorRobot;

public class futbolChampagne implements Estrategia{

    private JuniorRobot robot;

    public futbolChampagne(SapardoMarcelo robot) {
        this.robot = robot;
    }

    public void run(){

    }
    @Override
    public void onScannedRobot() {
        // Si la vida del robot es alta

        // Si la vida del robot es baja
    }

    @Override
    public void onHitByBullet() {
        // Correr hacia algun lado

        // Calcular por donde estaría

        // Disparar
    }

    @Override
    public void onHitWall() {
        // Si puedo saber donde hay una esquina, es ir en la contraria

        // Si no se puede saber, es girar a la derecha o izquierda con una chance
    }
}
