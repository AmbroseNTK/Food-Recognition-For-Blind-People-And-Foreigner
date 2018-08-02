package ntk.ambrose.foodinspector.recognizer;

public class ClassifierInput {
    private String GRAPH_FILE_PATH = "file:///android_asset/graph.pb";
    private String LABELS_FILE_PATH = "file:///android_asset/labels.txt";

    private String GRAPH_INPUT_NAME = "Placeholder";
    private String GRAPH_OUTPUT_NAME = "final_result";

    private int IMAGE_SIZE = 224;
    private int COLOR_CHANNELS = 3;

    private String ASSETS_PATH = "file:///android_asset/";

    public ClassifierInput(String graphFile, String labelsFile, String inputName, String outputName, int imageSize, int colorChannels, String assetPath) {
        GRAPH_FILE_PATH = graphFile;
        LABELS_FILE_PATH = labelsFile;
        GRAPH_INPUT_NAME = inputName;
        GRAPH_OUTPUT_NAME = outputName;
        IMAGE_SIZE = imageSize;
        COLOR_CHANNELS = colorChannels;
        ASSETS_PATH = assetPath;
    }
}
