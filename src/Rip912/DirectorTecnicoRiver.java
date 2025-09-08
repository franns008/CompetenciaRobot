package Rip912;

import robocode.JuniorRobot;
import robocode.util.Utils;

public abstract  class DirectorTecnicoRiver implements Estratega {
    private  FutbolChampagne champagne;
    private  AguantarElPartido aguantar;
    private  JuniorRobot robot;

    public DirectorTecnicoRiver(JuniorRobot robot) {
        this.robot = robot;
    }
    public abstract Estrategia onScannedRobot();

    public abstract Estrategia onHitByBullet();

    public abstract Estrategia onHitWall();

    public abstract Estrategia run();

    protected class FutbolChampagne implements Estrategia {

        private JuniorRobot robot;
        private boolean volver = false;
        private boolean volverArma = false;
        private boolean onAWall = false;
        private MapToWall myMap;
        private int cantDisparosRecibidos = 0;
        private int gunAngle = 0;
        private int cantToques = 0;
        private int movimientoCañon = 20;
        private boolean elegirPared = true;

        public FutbolChampagne(JuniorRobot robot) {
            this.robot = robot;
        }

        private void chequearTodosLados(int limite) {
            // Actualizar ángulo relativo del arma
            if (this.robot.others <= 7) {
                this.movimientoCañon = 10;
            }

            if (volverArma) {
                gunAngle -= movimientoCañon;  // gira a la izquierda
            } else {
                gunAngle += movimientoCañon;  // gira a la derecha
            }
            // Limitar la rotación a ±90° relativo al cuerpo
            if (gunAngle >= limite) {
                volverArma = true;  // invertir dirección
            } else if (gunAngle <= 0) {
                volverArma = false; // invertir dirección
            }
        }

        private void posicionArmaEnPared() {
            if (this.robot.robotX == 0 && this.robot.robotY == 0) {
                this.robot.bearGunTo(180);
                gunAngle = 180;
            } else if (this.robot.robotX == 0 && this.robot.robotY == this.robot.fieldHeight) {
                this.robot.bearGunTo(0);
                gunAngle = 0;
            } else if (this.robot.robotX == this.robot.fieldWidth && this.robot.robotY == 0) {
                this.robot.bearGunTo(180);
                gunAngle = 180;
            } else {
                this.robot.bearGunTo(0);
                gunAngle = 0;
            }
        }

        public void run() {
            // Que hago en cada turno que sea parte de la estrategia
            // Moverse hacia una pared
            // Elegir una pared
            // Si veo a un robot no me importa, estoy buscando una pared

            if (elegirPared) {
                myMap = chooseAWall();
                elegirPared = false;
            }

            if (!onAWall) {
                this.chequearTodosLados(45);
                robot.turnGunTo(robot.heading + gunAngle);
                robot.ahead(15);
            }
            if (onAWall) {
                if (!volver) {
                    robot.ahead(35);
                } else {
                    robot.back(35);
                }
                this.chequearTodosLados(180);
                robot.turnGunTo(robot.heading + gunAngle);
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
            for (int i = 0; i < arrayOfDistances.length; i++) {
                if (arrayOfDistances[i] < arrayOfDistances[indexMin]) {
                    indexMin = i;
                }
            }
            int angle = indexMin * 90;
            if (angle == 270) angle = -90;
            MapToWall map = new MapToWall((int) arrayOfDistances[indexMin], indexMin);
            robot.turnTo(angle);
            return map;
        }

        private void disparo(double daño) {
            if (!onAWall) {
                this.robot.turnGunTo(this.robot.scannedAngle);
                this.robot.fire(daño);
            }
            if (onAWall) {
                this.robot.fire(daño - 0.2);
            }
        }

        @Override
        public void onScannedRobot() {
        /*if(this.robot.scannedDistance<250 || robot.others>5){
            this.disparo(2);
        }*/
            this.disparo(2);

        }

        @Override
        public void onHitByBullet() {
            cantDisparosRecibidos++;
            this.robot.turnGunTo(this.robot.hitByBulletAngle);
            this.robot.fire(2);
            if ((cantDisparosRecibidos % 3) == 0) {
                this.robot.turnAheadRight(80, 90);
                this.robot.ahead(50);
                onAWall = false;
                elegirPared = true;

            } // cuando me pegan 3 veces me muevo.
        }

        @Override
        public void onHitWall() {
            cantToques++;
            if (!onAWall) {
                onAWall = true;
                robot.turnRight(90);
                robot.turnGunRight(90);
            }
            if (cantToques == 2) {
                this.posicionArmaEnPared();

            }
            volver = !volver;
        }

        public boolean soyEse(Estrategia estrategia) {
            return estrategia instanceof FutbolChampagne;
        }
    }

    protected class AguantarElPartido implements Estrategia {
        private JuniorRobot robot;
        private boolean onCorner;
        private boolean volverArma = false;
        private int hitByBulletCounter = 0;
        private int cantToques = 0;
        private int gunAngle = 0;
        private int limiteSup = 60;
        private int limiteInf = 0;
        private int movimientoCañon = 10;
        private MapToWall myMap;
        private boolean volverAtras = false;
        private boolean elegirPared = true;


        public AguantarElPartido(JuniorRobot robot) {
            this.robot = robot;
        }



        private void chequearTodosLados() {
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

        private void corregirArma() {
            if (this.robot.robotY == 0 && this.robot.robotX == 0) {
                this.robot.bearGunTo(90);
                gunAngle = 90;
                limiteSup = 90;
                limiteInf = 0;
            } else if (this.robot.robotY == 0 && this.robot.robotX == this.robot.fieldWidth) {
                this.robot.bearGunTo(180);
                gunAngle = 180;
                limiteSup = 180;
                limiteInf = 90;
            } else if (this.robot.robotY == this.robot.fieldHeight && this.robot.robotX == 0) {
                this.robot.bearGunTo(90);
                gunAngle = 90;
                limiteSup = 90;
                limiteInf = 0;
            } else {
                gunAngle = 180;
                limiteSup = 180;
                limiteInf = 90;
                this.robot.bearGunTo(180);
            }
            if (volverAtras) {
                limiteInf += 270;
                limiteSup += 270;
            }
        }

        private double getAngleToShoot() {
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
            robot.turnGunRight((int) gunTurn);
            if (robot.scannedDistance < 100) {
                robot.fire(3);
            }
            if (robot.scannedDistance < 250) {
                robot.fire(2);
            }


        }


        public double calcularAnguloDeDisparo(
                double miX, double miY,
                double enemigoX, double enemigoY,
                double enemigoVel, double enemigoHeading) {
            // Tiempo que tarda la bala en recorrer la distancia actual


            // Predicción de posición futura del enemigo
            // Lo que hace es una traducción a coordenadas polares
            // Las coordenadas polares me dicen cuanto me desplazo (la magnitud)
            // y en que direccion (el heading)
            // Para pasar esto a coordenadas comunes, usamos seno y coseno multiplicado por la magnitud(velocidad)
            // y le sumamos la posición del enemigo para obtener el eje x e y donde supuestamente
            // podria estar
            double futuroX = enemigoX + Math.sin(Math.toRadians(enemigoHeading)) * enemigoVel;
            double futuroY = enemigoY + Math.cos(Math.toRadians(enemigoHeading)) * enemigoVel;

            // Calcular ángulo desde mi posición hasta la posición futura
            double dx = futuroX - miX;
            double dy = futuroY - miY;

            // arcotangente2 es medio mágico
            // Devuelve el angulo de un vector (algo que tiene X e Y) en el mapa
            // El algo que tiene X e Y son las variables dx y dy
            // Esto me da el angulo al que tengo que apuntar desde donde estoy para pegarle
            // a mi prediccion de donde esta
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
            for (int i = 0; i < arrayOfDistances.length; i++) {
                if (arrayOfDistances[i] < arrayOfDistances[indexMin]) {
                    indexMin = i;
                }
            }
            int angle = indexMin * 90;
            if (angle == 270) angle = -90;
            MapToWall map = new MapToWall((int) arrayOfDistances[indexMin], indexMin);
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
            System.out.println("Esquina a la que tengo que ir " + esquina);
            return esquina;
        }

        private void movimientoEsquina(int esquina) {
            switch (myMap.getPared()) {
                case 0: //pared arriba
                    if (esquina == 2) {
                        volverAtras = true;
                    } else {
                        volverAtras = false;
                    }
                    break;
                case 1://pared derecha
                    if (esquina == 3) {
                        volverAtras = true;
                    } else {
                        volverAtras = false;
                    }
                    break;
                case 2: // pared abajo
                    if (esquina == 1) {
                        volverAtras = true;
                    } else {
                        volverAtras = false;
                    }
                    break;
                case 3: // pared izquieda
                    if (esquina == 0) {
                        volverAtras = true;
                    } else {
                        volverAtras = false;
                    }
                    break;
            }

        }

        @Override
        public void onHitByBullet() {
            hitByBulletCounter++;
            System.out.println("Me dieron");
            if (hitByBulletCounter % 3 == 0) {
                System.out.println("No aguanto más");
                double attackingAngle = getAngleToShoot();
                if (volverAtras) {
                    robot.turnAheadRight(50, 90);
                    robot.ahead(30);
                } else {
                    robot.turnBackRight(50, 90);
                    robot.back(30);
                }
                robot.turnGunRight((int) attackingAngle);
                robot.fire(1.5);
                onCorner = false;
                cantToques = 0;
                elegirPared = true;
            }
        }

        @Override
        public void onHitWall() {
            cantToques++;
            if (cantToques == 2) {
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
            if (cantToques == 0) {
                this.robot.ahead(20);
                this.chequearTodosLados();
                robot.turnGunTo(robot.heading + gunAngle);
            }
            if (!onCorner) {
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
            if (onCorner) {

                this.chequearTodosLados();
                this.robot.turnGunTo(this.robot.heading + gunAngle);
            }
        }

        public boolean soyEse(Estrategia estrategia) {
            return estrategia instanceof AguantarElPartido;
        }

    }

}


