import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionClient;
import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionManager;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.FaceDescription;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.ImageAnalysis;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.VisualFeatureTypes;
import constants.Constants;

import static java.nio.file.Files.readAllBytes;

public class FaceMain {

            //"https://www.pilofficial.com/images/gallery/2013/tombk03.jpg";

    public static void main(String[] args) {
        ComputerVisionClient compVisClient =
                ComputerVisionManager.authenticate(Constants.SUBSCRIPTION_KEY).withEndpoint(Constants.END_POINT);
        analyzeLocalImage(compVisClient);

        /*try {
            URIBuilder builder = new URIBuilder(uriBase);

            // Request parameters. All of them are optional.
            builder.setParameter("visualFeatures", "Categories,Faces,Description,Color");

            // Prepare the URI for the REST API method.
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers.
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

            // Request body.
            StringEntity requestEntity =
                    new StringEntity("{\"url\":\"" + imageToAnalyze + "\"}");
            request.setEntity(requestEntity);

            // Call the REST API method and get the response entity.
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // Format and display the JSON response.
                String jsonString = EntityUtils.toString(entity);
                JSONObject json = new JSONObject(jsonString);
                System.out.println("REST Response:\n");
                System.out.println(json.toString(2));
            }
        } catch (Exception e) {
            // Display error message.
            System.out.println(e.getMessage());
        }*/
    }

    private static void analyzeLocalImage(ComputerVisionClient compVisClient) {
        // Need a byte array for analyzing a local image.
        File localImage = new File(Constants.IMAGE_DIR);
        byte[] imgBytes = new byte[0];
        try {
            imgBytes = readAllBytes(localImage.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<VisualFeatureTypes> features = new ArrayList<>();
        features.add(VisualFeatureTypes.FACES);

        // Detect faces in local image
        ImageAnalysis analysisLocal = compVisClient.computerVision().analyzeImageInStream()
                .withImage(imgBytes)
                .withVisualFeatures(features)
                .execute();
        /*.analyzeImageInStream().withImage(imageByteArray)
                .withVisualFeatures(featuresToExtractFromLocalImage).execute();*/
        // Display any faces found in the image and their location.
        System.out.println("\nFaces: ");
        for (FaceDescription face : analysisLocal.faces()) {
            System.out.printf("\'%s\' of age %d at location (%d, %d), (%d, %d)\n", face.gender(), face.age(),
                    face.faceRectangle().left(), face.faceRectangle().top(),
                    face.faceRectangle().left() + face.faceRectangle().width(),
                    face.faceRectangle().top() + face.faceRectangle().height());
        }
    }

}
