import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        InputArgs Args = Utils.parseCmdArgs(args);
        if (!Args.getAreFilesCorrect()) {
            System.err.println("Ошибка: введенные данные не корректны.");
            System.exit(0);
        }
        List<Point> points = Utils.readInfoFromFile(Args.getInputFile());
        List<Point> result = Utils.getResult(points);
        Utils.writeInfoToFile(Args.getOutputFile(), result);
    }
}