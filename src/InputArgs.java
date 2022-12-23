public class InputArgs {
    static String inputFile;
    static String outputFile;
    static boolean areFilesCorrect = true;

    public void setInputFile(String inputFile) {
        InputArgs.inputFile = inputFile;
    }

    public void setOutputFile(String outputFile) {
        InputArgs.outputFile = outputFile;
    }

    public void setAreFilesCorrect(boolean areFilesCorrect) {
        this.areFilesCorrect = areFilesCorrect;
    }

    public String getInputFile() {
        return inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public boolean getAreFilesCorrect() {
        return areFilesCorrect;
    }
}
