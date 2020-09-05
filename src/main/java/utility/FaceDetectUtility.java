package utility;

import constants.Constants;

import java.io.File;
import java.io.IOException;

import static java.nio.file.Files.readAllBytes;

public class FaceDetectUtility {

    public static byte[] getBytesFromImage(String imageUrl) {
        File localImage = new File(imageUrl);
        byte[] imgBytes = new byte[0];
        try {
            imgBytes = readAllBytes(localImage.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgBytes;
    }
}
