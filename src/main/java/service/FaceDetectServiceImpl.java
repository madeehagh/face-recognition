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

    /**
     * This method adds all the features requested by user to capture in the response for any given image.
     * The method makes sure that duplicate feature is not added in the list.
     * @param name : Feature Value passed through console
     * @return
     */
    @Override
    public boolean addFeatures(String name) {
        if (!features.contains(VisualFeatureTypes.valueOf(name))) {
            features.add(VisualFeatureTypes.valueOf(name));
            return true;
        }
        return false;
    }

    /**
     * This method proceses all the images in a given local directory.
     * The method invokes analyzeImageInStream() of ComputerVision(azure)
     * and capture the features mentioned in features list.
     * @throws IOException
     * @throws Exception
     */
    @Override
    public void analyzeLocalImage() throws IOException, Exception {

        //TODO: The image directory path is hard coded for now, can be improvised by accepting the image path from user.

        File directoryPath = new File(Constants.IMAGE_DIR);
        File filesList[] = directoryPath.listFiles();
        BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.OUTPUT_FILE));
        ComputerVisionClient computerVisionClient = getComputerVisionClient();

        for (File file : filesList) {
            FaceDetectionResponse faceDetectionResponse = analyzeLocalImage(computerVisionClient, file.getPath());
            writer.write(faceDetectionResponse.getImage() + "," + faceDetectionResponse.getNumberOfPersons());
            writer.newLine();
            //System.out.println(faceDetectionResponse.toString());
        }
        writer.close();
    }

    private ComputerVisionClient getComputerVisionClient() {
        return ComputerVisionManager.authenticate(Constants.SUBSCRIPTION_KEY).withEndpoint(Constants.END_POINT);
    }

    /** This method processes all the images in a given remote directory/file.
     * The method invokes analyzeImage() of ComputerVision(azure)
     * and capture the features mentioned in features list.
     * @param computerVisionClient
     * @param remoteImageURL
     */
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
