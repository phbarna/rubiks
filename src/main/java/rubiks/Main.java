package rubiks;

public class Main {

    public static void main(String[] args) {

        try {
            Cube solvedCube = new Cube();
            Cube randomCube = new Cube().shuffle();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
