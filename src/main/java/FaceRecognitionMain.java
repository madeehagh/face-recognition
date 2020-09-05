import controller.FaceDetectionController;

import java.util.Scanner;

public class FaceRecognitionMain {

    public static void main(String[] args) throws Exception {

        FaceDetectionController faceDetectionController = new FaceDetectionController();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            //Option Analyze image is only for the images from the local directory
            //TODO: Add 1 more option to accept remote images
            System.out.println("Select option: Define Required Feature [1], Analyse Image [2]");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    System.out.println("Enter Feature (ex: Categories,Faces, Description,Color ))");
                    faceDetectionController.addFeature(scanner.nextLine());
                    break;

                case "2":
                    faceDetectionController.analyzeLocalImage();
                    break;
                default:
                    System.out.println("Invalid Input");
            }
        }

    }
}
