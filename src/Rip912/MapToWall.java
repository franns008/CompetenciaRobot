package Rip912;

public class MapToWall {
    private Direction direction;
    private int distanceFromWall;
    private int stoppingPoint;

    public MapToWall(int distanceFromWall, int hardcodedDirection) {
        this.distanceFromWall = distanceFromWall;
        // 0 = Pared arriba, 1 = Pared derecha, 2 = Pared abajo, 3 = Pared izq
        switch (hardcodedDirection){
            case 0:
                direction = Direction.NORTH;
                break;
            case 1:
                direction = Direction.EAST;
                break;
            case 2:
                direction = Direction.SOUTH;
                break;
            case 3:
                direction = Direction.WEST;
                break;
            default:
                throw new IllegalArgumentException("Código inválido, no existe esa pared");
        }
    }

    public int getDistanceFromWall() {
        return distanceFromWall;
    }
}
