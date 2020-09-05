package controller;

import service.FaceDetectService;
import service.FaceDetectServiceImpl;

public class FaceDetectionController {
    FaceDetectService faceDetectService;

    public FaceDetectionController() {
        this.faceDetectService = new FaceDetectServiceImpl();
    }


    public void analyzeLocalImage() throws Exception {
            faceDetectService.analyzeLocalImage();

    }
    public void addFeature(String featureName) {
        faceDetectService.addFeatures(featureName.toUpperCase());
    }
}
