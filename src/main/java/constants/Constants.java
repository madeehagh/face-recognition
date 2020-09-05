package constants;

public class Constants {

    public static final String SUBSCRIPTION_KEY = System.getenv("COMPUTER_VISION_SUBSCRIPTION_KEY");
    public static final String END_POINT = System.getenv("COMPUTER_VISION_ENDPOINT");
    public static final String API_VERSION = "vision/v3.0/analyze";
    public static final String URI_BASE = END_POINT + API_VERSION;
    public static final String IMAGE_DIR = "src/main/resources/images/";
    public static final String OUTPUT_FILE = "src/main/resources/output.txt";
}
