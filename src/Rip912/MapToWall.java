package Rip912;

public class MapToWall {
    private Rip912.Direction direction;
    private int distanceFromWall;
    private int pared;

    public MapToWall(int distanceFromWall, int hardcodedDirection) {
        this.distanceFromWall = distanceFromWall;
        // 0 = Pared arriba, 1 = Pared derecha, 2 = Pared abajo, 3 = Pared izq
        switch (hardcodedDirection){
            case 0:
                direction = Rip912.Direction.NORTH;
                break;
            case 1:
                direction = Rip912.Direction.EAST;
                break;
            case 2:
                direction = Rip912.Direction.SOUTH;
                break;
            case 3:
                direction = Direction.WEST;
                break;
            default:
                throw new IllegalArgumentException("Código inválido, no existe esa pared");
        }
    }

    public int getAngleToTheCenterOfTheMap() {
        int pointingToTheCentre = 0;
        switch (direction) {
            case NORTH:
                pointingToTheCentre = 180;
                break;
            case SOUTH:
                pointingToTheCentre = 0;
                break;
            case EAST:
                pointingToTheCentre = 270;
                break;
            case WEST:
                pointingToTheCentre = 90;
                break;
            default:
                throw new IllegalArgumentException("Código inválido, no existe esa pared");
        }

        return pointingToTheCentre;
    }

    public int getPared() {
        return pared;
    }

    public void setPared(int pared) {
        this.pared = pared;
    }
}
