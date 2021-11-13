import concurrentcube.Cube;

public class test {
    public static void main(String[] args) {
        var counter = new Object() { int value = 0; };
        Cube cube = new Cube(4,
                (x,y) -> { ++counter.value; }, 
                (x,y) -> { ++counter.value; },
                () -> { ++counter.value; },
                () -> { ++counter.value; });
        System.out.println(cube.sequentialShow());

        System.out.println(cube.showTest());
    }
}
