package service;

import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionClient;

public interface FaceDetectService {
     void addFeatures(String featureName);
     void analyzeLocalImage() throws Exception;
     void analyzeRemoteImage(ComputerVisionClient computerVisionClient, String remoteImageURL) throws Exception;
}
