import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Point> readInfoFromFile(String fileName) {
        List<Point> points = new ArrayList<Point>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                String[] splitLine = line.split(" ");
                Point point = new Point(Integer.parseInt(splitLine[0]),
                        Integer.parseInt(splitLine[1]));
                points.add(point);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return points;
    }

    public static boolean writeInfoToFile(String fileName, List<Point> points) throws IOException {
        FileWriter fw = new FileWriter(fileName);

        for (Point p : points) {
            fw.write(p.getX() + " " + p.getY() + '\n');
        }

        fw.close();

        return true;
    }

    public static InputArgs parseCmdArgs(String[] args) {
        InputArgs Args = new InputArgs();
        try {
            String inputFile = args[0];
            String outputFile = args[1];
            if (!checkIfFileExists(inputFile)) {
                throw new Exception();
            }
            assert !checkIfFileExists(inputFile);
            Args.setInputFile(inputFile);
            Args.setOutputFile(outputFile);
        } catch (Exception ex) {
            Args.setAreFilesCorrect(false);
        }
        return Args;
    }

    public static boolean checkIfFileExists(String fileName) {
        File f = new File(fileName);
        return f.exists();
    }

    public static List<Point> getResult(List<Point> points) {
        int n = points.size();
        List<Point> result = new ArrayList<>();
        if (n < 3)
            return result;
        Point p1 = points.get(0);
        Point p2 = points.get(1);
        Point p3 = points.get(2);
        result.add(p1);
        result.add(p2);
        result.add(p3);

        double minPerimeter = countTrianglePerimeter(p1, p2, p3);

        for (int i = 1; i < n - 2; i++) {
            p1 = points.get(i);
            for (int j = i + 1; j < n - 1; j++) {
                p2 = points.get(j);
                for (int l = j + 1; l < n; l++) {
                    p3 = points.get(l);

                    double perimeter = countTrianglePerimeter(p1, p2, p3);
                    if (perimeter < minPerimeter) {
                        minPerimeter = perimeter;
                        result.set(0, p1);
                        result.set(1, p2);
                        result.set(2, p3);
                    }
                }
            }
        }

        return result;
    }

    public static double countTrianglePerimeter(Point p1, Point p2, Point p3) {
        double l1 = Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) +
                Math.pow(p2.getY() - p1.getY(), 2));

        double l2 = Math.sqrt(Math.pow(p3.getX() - p2.getX(), 2) +
                Math.pow(p3.getY() - p2.getY(), 2));

        double l3 = Math.sqrt(Math.pow(p1.getX() - p3.getX(), 2) +
                Math.pow(p1.getY() - p3.getY(), 2));

        return l1 + l2 + l3;
    }
}
