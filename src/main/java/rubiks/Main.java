package rubiks;

public class Main {

    public static void main(String[] args) {
        Cube solvedCube = new Cube();
        try {
            Cube randomCube = new Cube().shuffle();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
