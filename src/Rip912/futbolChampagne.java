package Rip912;

import robocode.JuniorRobot;


public class futbolChampagne implements Estrategia{

    private JuniorRobot robot;
    private boolean stopWhenISeeARobot = false;
    private boolean volver =false;
    private boolean volverArma =false;
    private boolean onAWall = false;
    private boolean defensiveMode = false;

    private int turnsOnDefensiveMode = 15;
    private MapToWall myMap;

    public futbolChampagne(JuniorRobot robot) {
        this.robot = robot;
    }

    public void run() {
        // Que hago en cada turno que sea parte de la estrategia
        // Elegir una pared
        myMap = chooseAWall();
        // Moverse hacia ella
        while (!onAWall) {
            robot.ahead(10);
        }

        robot.turnRight(90);
        robot.turnGunRight(90);



        while (onAWall) {
            // Mover el robot
            if (!volver) {
                robot.ahead(20);
            } else {
                robot.back(20);
            }
            shootingStrategy();


        }
    }

    private void shootingStrategy() {
        // Estoy en modo defensa en pared

        if (defensiveMode){
            // No giro el arma, sigue estando apuntando al posible walls
            this.robot.fire(10);
            this.turnsOnDefensiveMode--;
            if (this.turnsOnDefensiveMode == 0){
                defensiveMode = false;
                turnsOnDefensiveMode += 15;

                this.robot.turnGunTo(myMap.getAngleToTheCenterOfTheMap());
            }
        }

        // Modo torreta
        if (robot.others >= 10){
            this.robot.fire(2);
        } else {
            // Modo estratégico
        }


    }

    private MapToWall chooseAWall() {
        // 0 = Pared arriba, 1 = Pared derecha, 2 = Pared abajo, 3 = Pared izq
        double[] arrayOfDistances = {
                robot.fieldHeight - robot.robotY,
                robot.fieldWidth - robot.robotX,
                robot.robotY,
                robot.robotX
        };
        int indexMin = 0;
        for (int i=0; i < arrayOfDistances.length; i++){
            if (arrayOfDistances[i] < arrayOfDistances[indexMin]){
                indexMin = i;
            }
        }
        int angle = indexMin * 90;
        if (angle == 270) angle = -90;

        MapToWall map = new MapToWall((int)arrayOfDistances[indexMin], indexMin);
        robot.turnTo(angle);
        return map;
    }

    @Override
    public void onScannedRobot() {

        if (!onAWall) {
            int angulo = this.robot.gunBearing;
            this.robot.turnGunTo(this.robot.scannedAngle);
            this.robot.fire(2);
            this.robot.turnGunTo(angulo);
        }
        if (defensiveMode){
            // Seguramente escanee al que me esta pegando
            this.robot.fire(10);
        }


    }

    @Override
    public void onHitByBullet() {
        int bearing = this.robot.hitByBulletAngle;
        System.out.println(bearing);
        int gunTurn = 0;

        if (!onAWall){
            return;
        }

        if (!defensiveMode && myMap.askForBulletDirection(bearing) != 0){
            // Hay alguien en una esquina
            robot.turnGunTo(bearing);
        }


        if (defensiveMode){
            robot.turnGunRight(gunTurn - robot.gunHeading);
        } else {
            this.robot.turnGunTo(this.robot.hitByBulletAngle-5);
            this.robot.fire(2);
        }


    }

    @Override
    public void onHitWall() {
        if(!onAWall){onAWall = true;}
        volver=!volver;
        // Si puedo saber donde hay una esquina, es ir en la contraria

        // Si no se puede saber, es girar a la derecha o izquierda con una chance
    }
}