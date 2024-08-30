public class Main {
    public static void main(String[] args) throws InterruptedException {
        Grid grid = new Grid();

        while (grid.areCellsAlive()) {
            System.out.println(grid);
            System.out.println();
            Thread.sleep(2000);
            grid.advanceGeneration();
        }
    }
}