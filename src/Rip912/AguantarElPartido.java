package Rip912;

import robocode.JuniorRobot;
import robocode.util.Utils;

public class AguantarElPartido implements Estrategia{
    private JuniorRobot robot;
    private boolean onCorner;
    private boolean volverArma =false;
    private int hitByBulletCounter = 0;
    private int cantToques = 0;
    private int gunAngle = 0;
    private int limiteSup = 60;
    private int limiteInf = 0;
    private int movimientoCañon = 10;
    private MapToWall myMap;
    private boolean volverAtras =false;
    private boolean elegirPared = true;

    public AguantarElPartido(JuniorRobot robot) {
        this.robot = robot;
    }

    private double getHipotenusa(double catA, double catB){
        return Math.sqrt(
                (Math.pow(catA,2)+Math.pow(catB,2))
        );
    }



    private void chequearTodosLados(){
        if (volverArma) {
            gunAngle -= movimientoCañon;  // gira a la izquierda
        } else {
            gunAngle += movimientoCañon;  // gira a la derecha
        }
        if (gunAngle >= limiteSup) {
            volverArma = true;  // invertir dirección
        } else if (gunAngle <= limiteInf) {
            volverArma = false; // invertir dirección
        }
    }

    private double chooseACorner() {
        // 0 =  inf-izq , 1 = inf-der, 2 = sup-izq, 3 = sup-der
        double distanceFromNorthWall = robot.fieldHeight - robot.robotY;
        double distanceFromSouthWall = robot.robotY;
        double distanceFromEastWall = robot.fieldWidth - robot.robotX;
        double distanceFromWestWall = robot.robotX;
        double[] arrayOfDistances = {
                getHipotenusa(distanceFromWestWall, distanceFromSouthWall),
                getHipotenusa(distanceFromEastWall, distanceFromSouthWall),
                getHipotenusa(distanceFromWestWall, distanceFromNorthWall),
                getHipotenusa(distanceFromEastWall, distanceFromNorthWall)
        };
        int indexMin = 0;
        for (int i=0; i < arrayOfDistances.length; i++){
            if (arrayOfDistances[i] < arrayOfDistances[indexMin]){
                indexMin = i;
            }
        }
        int angle = 45 * (indexMin + 1);
        if (angle == 315) angle = -45;

        System.out.println("Elegi la esquina" + indexMin);
        robot.turnTo(angle);

        return arrayOfDistances[indexMin];
    }

    private void corregirArma (){
        if(this.robot.robotY ==0 && this.robot.robotX ==0){
            this.robot.bearGunTo(90);
            gunAngle = 90;
            limiteSup = 90;
            limiteInf = 0;
        } else if (this.robot.robotY ==0 && this.robot.robotX ==this.robot.fieldWidth) {
            this.robot.bearGunTo(180);
            gunAngle = 180;
            limiteSup = 180;
            limiteInf=90;
        }
        else if (this.robot.robotY ==this.robot.fieldHeight && this.robot.robotX == 0) {
            this.robot.bearGunTo(90);
            gunAngle = 90;
            limiteSup = 90;
            limiteInf = 0;
        }else {
            gunAngle = 180;
            limiteSup = 180;
            limiteInf = 90;
            this.robot.bearGunTo(180);
        }
        if(volverAtras){
                 limiteInf += 270;
                 limiteSup += 270;
        }
    }

    private double getAngleToShoot(){
        double angleToEnemy = Math.toRadians(this.robot.heading) + Math.toRadians(this.robot.scannedBearing);
        double enemyX = this.robot.robotX + this.robot.scannedDistance * Math.sin(angleToEnemy);
        double enemyY = this.robot.robotY + this.robot.scannedDistance * Math.cos(angleToEnemy);

        // Datos útiles del enemigo
        double enemyHeading = this.robot.scannedHeading;   // en grados
        double enemyVelocity = this.robot.scannedVelocity; // positivo adelante, negativo atras

        // Calcular el ángulo futuro para disparar

        double angleToFire = calcularAnguloDeDisparo(
                this.robot.robotX, this.robot.robotY,
                enemyX, enemyY,
                enemyVelocity, enemyHeading
        );

        // Apuntar el cañón hacia ese ángulo

        double gunTurn = Utils.normalRelativeAngleDegrees(angleToFire - robot.gunHeading);
        return gunTurn;
    }

    @Override
    public void onScannedRobot() {
        double angleToFire = getAngleToShoot();

        // Apuntar el cañón hacia ese ángulo
        double gunTurn = Utils.normalRelativeAngleDegrees(angleToFire);
        robot.turnGunRight((int)gunTurn);
        if (robot.scannedDistance < 250){
            robot.fire(2);
        }
    }


    public double calcularAnguloDeDisparo(
            double miX, double miY,
            double enemigoX, double enemigoY,
            double enemigoVel, double enemigoHeading) {
            // Tiempo que tarda la bala en recorrer la distancia actual


            // Predicción de posición futura del enemigo
            double futuroX = enemigoX + Math.sin(Math.toRadians(enemigoHeading)) * enemigoVel ;
            double futuroY = enemigoY + Math.cos(Math.toRadians(enemigoHeading)) * enemigoVel ;

            // Calcular ángulo desde mi posición hasta la posición futura
            double dx = futuroX - miX;
            double dy = futuroY - miY;

            double angulo = Math.toDegrees(Math.atan2(dx, dy));

            // Normalizar entre 0°–360°
            if (angulo < 0) {
                angulo += 360;
            }

        return angulo;
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
        map.setPared(indexMin);
        robot.turnTo(angle);
        return map;
    }

    private int conocerEsquinaMasCercana() {
        int mitadX = robot.fieldWidth / 2;
        int mitadY = robot.fieldHeight / 2;

        // Mitades
        boolean enIzquierda = robot.robotX < mitadX;
        boolean enDerecha = robot.robotX >= mitadX;
        boolean enAbajo = robot.robotY < mitadY;
        boolean enArriba = robot.robotY >= mitadY;

        // Determinar la esquina más cercana según el cuadrante
        int esquina = -1;
        if (enIzquierda && enAbajo) {
            esquina = 0; // inf-izq
        } else if (enDerecha && enAbajo) {
            esquina = 1; // inf-der
        } else if (enIzquierda && enArriba) {
            esquina = 2; // sup-izq
        } else if (enDerecha && enArriba) {
            esquina = 3; // sup-der
        }
        System.out.println("Esquina a la que tengo que ir "+esquina);
        return esquina;
    }

    private void movimientoEsquina(int esquina){
        switch (myMap.getPared()){
            case 0: //pared arriba
                if(esquina==2) {
                    volverAtras = true;
                }else{
                    volverAtras = false;
                }
                break;
            case 1://pared derecha
                if(esquina==3) {
                    volverAtras = true;
                }else{
                    volverAtras = false;
                }
                break;
            case 2: // pared abajo
                if(esquina==1) {
                    volverAtras = true;
                }else{
                    volverAtras = false;
                }
                break;
            case 3: // pared izquieda
                if(esquina==0) {
                    volverAtras = true;
                }else{
                    volverAtras = false;
                }
                break;
        }

    }

                @Override
    public void onHitByBullet() {
        hitByBulletCounter++;
        System.out.println("Me dieron");
        if (hitByBulletCounter % 3 == 0){
            System.out.println("No aguanto más");
            double attackingAngle = getAngleToShoot();
            if (volverAtras) {
                robot.turnAheadRight(50, 90);
                robot.ahead(30);
            }else {
                robot.turnBackRight(50,90);
                robot.back(30);
            }
            robot.turnGunRight((int)attackingAngle);
            robot.fire(1.5);
            onCorner = false;
            cantToques=0;
            elegirPared =true;
        }
    }

    @Override
    public void onHitWall() {
        cantToques++;
        if(cantToques ==2) {
            this.corregirArma();
            this.onCorner = true;
        } else if (cantToques < 2) {
            this.onCorner = false;
            robot.turnGunRight(90);
            robot.turnRight(90);
            int esquina = this.conocerEsquinaMasCercana();
            this.movimientoEsquina(esquina);
        }
    }

    @Override
    public void run() {

        if (elegirPared) {
            myMap = chooseAWall();
            elegirPared = false;
        }
        if (cantToques ==0){
            this.robot.ahead(20);
            this.chequearTodosLados();
            robot.turnGunTo(robot.heading + gunAngle);
        }
        if(!onCorner) {
            if (cantToques == 1) {
                if (volverAtras) {
                    this.robot.back(20);
                    this.chequearTodosLados();
                    System.out.println("entro 1");
                } else {
                    this.robot.ahead(20);
                    this.chequearTodosLados();
                    System.out.println("entro 2");
                }
                robot.turnGunTo(robot.heading + gunAngle);
            }
        }
        if(onCorner){

            this.chequearTodosLados();
            this.robot.turnGunTo(this.robot.heading + gunAngle);
        }
    }
}

