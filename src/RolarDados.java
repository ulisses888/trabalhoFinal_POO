import java.util.Random;

public class RolarDados {
    private static final Random random = new Random();

    public static int rolarDados6Lados(){
        return random.nextInt(6) + 1;
    }

    public static int rolarDado3Lados(){
        return random.nextInt(3) + 1;
    }
}
