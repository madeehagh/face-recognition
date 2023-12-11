## face-recognition

There are a lot of photos with people in each given photo.
This project can tell us the number of distinct faces in each photo and also return the total
number of faces.

### Dataset

Link to photos
https://drive.google.com/file/d/1IoYida6sWq-jyAK-SDTuc2lemu6321YF/view
This contains a zip file containing 107 images, each with a different number of faces in
between. File names are of the format 108.jpeg , 109.jpeg , 110.jpeg , ..., 214.jpeg .


### Input
src/main/resources/images/

### Output

The program needs to return the output of the following format (this is just sample output):
filename,faces
108.jpeg,1
109.jpeg,5
.
.
.
.
214.jpeg,2
Total,256
Each line contains a filename of the image and the number of faces present in the image.

The output of this program is written into output.txt under dir /src/resources


### Plugins/Dependecies added 
1. lombok
2. org.json
3. azure-cognitiveservices-computervision
4. azure-cognitiveservices-faceapi

### Main Class 
src/main/java/FaceRecognitionMain.java

# Run Application
 mvn clean compile exec:java


