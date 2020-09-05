package response;

import lombok.Data;

@Data
public class FaceDetectionResponse {
    private String image;
    private int numberOfPersons;
}
