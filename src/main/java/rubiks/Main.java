package rubiks;

public class Main {

    public static void main(String[] args) {

        try {
            Cube solvedCube = new Cube().asSolved();
            Cube randomCube = new Cube().asShuffled();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
