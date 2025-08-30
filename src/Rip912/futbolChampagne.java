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

        int gunAngle = 0; // Ángulo relativo al cuerpo
        boolean volverArma = false;

        while (onAWall) {
            // Mover el robot
            if (!volver) {
                robot.ahead(20);
            } else {
                robot.back(20);
            }
            shootingStrategy();

            /*
            // Actualizar ángulo relativo del arma
            if (volverArma) {
                gunAngle -= 5;  // gira a la izquierda
            } else {
                gunAngle += 5;  // gira a la derecha
            }

            // Limitar la rotación a ±90° relativo al cuerpo
            if (gunAngle >= 90) {
                gunAngle = 90;
                volverArma = true;  // invertir dirección
            } else if (gunAngle <= -10) {
                gunAngle = -10;
                volverArma = false; // invertir dirección
            }

            // Girar el arma al ángulo relativo
            robot.turnGunTo(robot.heading + gunAngle);
            */

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
        if (!stopWhenISeeARobot){
            return;
        }
        if (!onAWall) {
            int angulo = this.robot.gunBearing;
            this.robot.turnGunTo(this.robot.scannedAngle);
            this.robot.fire(2);
            this.robot.turnGunTo(angulo);
        }
        if (defensiveMode){
            // Seguramente escanee al que me esta pegando

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
        // norte
        if (bearing > 225 && bearing < 315) {
            gunTurn = 0;
            defensiveMode = true;
            System.out.println("Norte");
        }
        // este
        else if (bearing > 135 && bearing < 225){
            gunTurn = 90;
            defensiveMode = true;
            System.out.println("Este");
        }
        // Sur
        else if (bearing > 45 && bearing < 135) {
            gunTurn = 180;
            defensiveMode = true;
            System.out.println("sur");
        }
        // Oeste
        else if (bearing == 360) {
            gunTurn = 270;
            defensiveMode = true;
            System.out.println("Oeste");
        }


        if (defensiveMode){
            robot.turnGunRight(gunTurn - robot.gunHeading);
        }

        //this.robot.turnGunTo(this.robot.hitByBulletAngle-5);
        //this.robot.fire(2);
    }

    @Override
    public void onHitWall() {
        if(!onAWall){onAWall = true;}
        volver=!volver;
        // Si puedo saber donde hay una esquina, es ir en la contraria

        // Si no se puede saber, es girar a la derecha o izquierda con una chance
    }
}