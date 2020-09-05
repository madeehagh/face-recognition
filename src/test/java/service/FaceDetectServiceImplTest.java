package service;

import constants.Constants;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class FaceDetectServiceImplTest {

    static FaceDetectService faceDetectService;

    @BeforeClass
    public static void init(){
        faceDetectService = new FaceDetectServiceImpl();
    }

    @Test
    public void test_addFeatures() {
        assertTrue(faceDetectService.addFeatures("FACES"));
    }

    @Test
    public void analyzeLocalImage() {
        File outputFile = new File(Constants.OUTPUT_FILE);
        try {
            faceDetectService.analyzeLocalImage();
            assertTrue(outputFile.length() > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}