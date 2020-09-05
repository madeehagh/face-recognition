package service;

import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionClient;
import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionManager;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.ImageAnalysis;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.VisualFeatureTypes;
import constants.Constants;
import response.FaceDetectionResponse;
import utility.FaceDetectUtility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FaceDetectServiceImpl implements FaceDetectService {

    List<VisualFeatureTypes> features;

    public FaceDetectServiceImpl() {
        this.features = new ArrayList();
    }

    @Override
    public void addFeatures(String name) {
        if (!features.contains(VisualFeatureTypes.valueOf(name)))
         features.add(VisualFeatureTypes.valueOf(name));
    }

    @Override
    public void analyzeLocalImage() throws IOException, Exception {
        File directoryPath = new File(Constants.IMAGE_DIR);
        File filesList[] = directoryPath.listFiles();
        BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.OUTPUT_FILE));
        ComputerVisionClient computerVisionClient = getComputerVisionClient();

        for (File file : filesList) {
            FaceDetectionResponse faceDetectionResponse = analyzeLocalImage(computerVisionClient, file.getPath());
            writer.write(faceDetectionResponse.getImage() + " " + faceDetectionResponse.getNumberOfPersons());
            writer.newLine();
            //System.out.println(faceDetectionResponse.toString());
        }
        writer.close();

    }

    private ComputerVisionClient getComputerVisionClient() {
        return ComputerVisionManager.authenticate(Constants.SUBSCRIPTION_KEY).withEndpoint(Constants.END_POINT);
    }

    //TODO: complete the implementation for remote image processing
    @Override
    public void analyzeRemoteImage(ComputerVisionClient computerVisionClient, String remoteImageURL) {
        ImageAnalysis analysisRemote = computerVisionClient.computerVision().analyzeImage()
                .withUrl(remoteImageURL)
                .withVisualFeatures(features)
                .execute();
        //process analysisRemote object to get the response
    }

    private FaceDetectionResponse analyzeLocalImage(ComputerVisionClient compVisClient, String imagePath) {
        int lastIndex = imagePath.lastIndexOf("/");
        String imageName = imagePath.substring(lastIndex+1, imagePath.length());
        byte[] imgBytes = FaceDetectUtility.getBytesFromImage(imagePath);
        List<VisualFeatureTypes> features = new ArrayList<>();
        features.add(VisualFeatureTypes.FACES);

        ImageAnalysis analysisLocal = compVisClient.computerVision().analyzeImageInStream()
                .withImage(imgBytes)
                .withVisualFeatures(features)
                .execute();

        FaceDetectionResponse faceDetectionResponse = new FaceDetectionResponse();
        faceDetectionResponse.setImage(imageName);
        faceDetectionResponse.setNumberOfPersons(analysisLocal.faces().size());
        return faceDetectionResponse;
    }


}
