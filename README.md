![Title](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/JMLC2018-title.png)
<h1>
Food Recognition For Blind People And Foreigner
</h1>
<h2>
Content
</h2>
<p>

1. [About me](#about)
2. [What is it?](#whatisit)
3. [Why?](#why)
4. [My schedule](#myschedule)
5. [Methodology](#methodology)
6. [Implementation](#implementation)
7. [My result](#myresult)
8. [After this camp](#afterthiscamp)
9. [Summary](#summary)
10. [References](#reference)
11. [Acknowledgement](#acknowledgement)

</p>
<h2>
1. About me <a name="about"></a>
</h2>
<p>

Welcome to my MLC GitHub page. My name is ***Kiet Nguyen Tuan (Ambrose)*** . I wrote these lines when I was a senior student at ***Hoasen University, Vietnam***. Because of special love with Machine Learning, so I have come to this ML Camp hosted at Jeju National University to do my project. I also want to meet new friends from other countries. It was a experiement I never forget.
</p>

<h2>
2. What is it? <a name="whatisit"></a>
</h2>
<p>

In this project, I would like to develop a collection of model that can use to recognize food and its ingredients. For me, I create these models for Vietnamese food first. Developers in other countries can contribute their national food models to my collection. After that, every people can use these models free, so that apply to their app.
</p>

<h2>
3. Why? <a name="why"></a>
</h2>
<p>
When I found a idea for this camp, I realised that food is an important part of country's culture so that every people should have knowledge about food of place where they stay at. Moreover, disable people, specially blind people also need help to know what food they eat. For foreigner, I will be an useful food's guideline at place their visit and also be an assitant to track what their eat and suggest what they should or should not eat.
<p>

<h2>
4. My schedule <a name="myschedule"></a>
</h2>
<p>
Due to shortly time of this camp (July 22 to August 7, 2018) and I do not have enough data, I cannot finish all project at the camp. But I still continue to finish.</br>
Here is my plan</br>

Day | Date | What I did
--- | --- | ---
1 | July 22, 2018 | I came to Jeju island lately due to a storm at Shanghai.
2 | July 23, 2018 | I prepared an introduction presentation and attended MLC Opening Ceremony.
3 | July 24, 2018 | I visited Jeju Development Center and had a seminar about blockchain with AI.
4 | July 25, 2018 | I reviewed all my project which I have prepared at home and viewed Google Cloud Platform tutorial.
5 | July 26, 2018 | I re-designed UI for Android App and applied Firebase.
6 | July 27, 2018 | I checked my dataset for food detection and downloaded more images from Google Image.
7 | July 28, 2018 | I retrained food detection model, it could recognize about 10 Vietnamese food (Bun, pho, com,...).
8 | July 29, 2018 | It was a weekend so I had around trip at Jeju island.
9 | July 30, 2018 | I prepared dataset for ingredient detection.
10 | July 31, 2018 | I tried to train object detection model (ingredient detection) on local PC but it was slowly.
11 | Aug 1, 2018 | I used Google Cloud Platform, I created two Virtual Machine, one at Japan and the other at Taiwan. The VM at Taiwan had large CPU and Memory (vCPU: 8, Memory: 52GB) so it was a little bit faster than my laptop.
12 | Aug 2, 2018 | I returned to my laptop. Installed tensorflow-gpu and tried to use my GPU card NVIDIA GT 740M. I trained on my laptop and it was the fastest (about 1 second per training step). After 2 hours, my GPU card was broken down, I could not use it after.
</p>

<h2>
5. Methodology <a name="methodology"></a>
</h2>
<p>
After researched machine learning and implemented libraries, I refer to use TensorFlow with Python programming language and Java/Android for my mobile app. Nowaday, I suppose that TensorFlow is one of the best frameworks for Machine Learning and also Deep Learning. Moreover, TensorFlow had large developer community which ready to help when I go to problems.</br>
Project has 3 parts:</br>

* **Food Recognition**: It is a model to recognize food in a photo in summary. In my case, it returns result like Bun, Pho, Com, Banh-mi etc. I have downloaded above 600 photo per food on Google Image. After that, I have deleted unneccesary images and just keep correct images. I used these images to train the model.

![FoodRecognitionFlow](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/FoodRecognitionFlow.jpeg)

* **Ingredient Detection**: According to result which I have after applied Food Recognition model I use ingredient Detection model to detect ingredient in food one by one, so that I can calculate its nutrition, predict its taste. Because of kind variation of food, each food has Ingredient Detection model differently. For example, Pho is a popular food in Vietnam, and its ingredient change its nutrition a lot.

![IngredientDetection](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/IngredientDetectionFlow.jpeg)

Pho-bo | Pho-ga
--- | ---
![phobo](http://www.savourydays.com/wp-content/uploads/2013/01/PhoBoHN.jpg) | ![phoga](http://giadinh.mediacdn.vn/zoom/655_361/2014/7-1412602058-tu-lam-pho-ga-7-1412654910607-crop-1412654928337.jpg)

The first photo is Pho-bo means Pho with beef. The second is Pho-ga means Pho with chicken. All of them is Pho, but they have different ingredient. Because of nutrition difference of beef and chicken, so Pho-bo and Pho-ga have different nutrition. So Ingredient Detection is an important step to solve this problem which is common in anothor food's culture.
* **Mobile App**: This is a place where I apply these models all together so that reach project target. I developed an Android app with TensorFlow Lite for mobile. The mobile app capture food photo, then, it uses these models to extract what food and ingredient, finally, it returns all food's information (Name, original, ingredient, taste prediction, nutrition values,...). To help blind people, I use module text-to-speed, so that translate these information into voice, and blind people can hear it.

![FoodRecognition](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/ActivityDiagram.jpg)
</p>

<h2>
6. Implementation <a name="implementation"></a>
</h2>
<p>
In this section, I would like to show you what I have done and how to continue development this project step by step.</br>

<h3>
Step 1. Setup development environment
</h3>
<p>
In this project, you should install some application which is shown in below table.</br>

Tool | Description | Link
--- | --- | ---
![](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/python-logo-master-v3-TM.png) | Python is main language to train models | https://www.python.org/downloads/
![](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/tensorflow-logo.png) | TensorFlow framework supports all things in ML/DL. If your PC have GPU card, you should install TensorFlow-GPU version to get high performance | https://www.tensorflow.org/install/
![](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/Anaconda_Logo.png) | In Windows, some package in unvailable, so you should have Anaconda to install them | https://conda.io/docs/user-guide/install/index.html
</br>
There are three important tools you have to install first, some small tool I will show you after.
</p>

<h3>
Step 2. Prepare data to create Image Recognition model.
</h3>
<p>

There are a lot of ways to collect photos. For me, I refer to collect them on Google Image, because it is the largest search engine, so it contains a lot of photos. The simple way is use a tool that let you download images automatically based on keywords. I have used [this tool](https://github.com/hardikvasa/google-images-download)

. Each food should have different folders. Notice that the folder name is also the label of food, so please check it carefully.</br>

```batch
└───vietnamese_food
    ├───background
    ├───banh bao
    ├───banh mi
    ├───bo bit tet
    ├───bun bo
    ├───com dia
    ├───dau hu
    ├───mi xao
    ├───rau xao
    ├───thit kho tau
    └───trung op la
```
There are 10 common food in Vietnam and backgroud to recognize uneatable things. Here is my sample config</br>

```json
{
    "Records": [
        {
            "keywords": "bun bo",
            "limit":600,
            "type":"photo",
            "format":"jpg",
            "output_directory":"vietnamese_food/",
            "chromedriver":"chromedriver.exe"

        },
        {

        },...
    ]
}
```
After downloading process complete. You need to review all photo, delete error photos or out of topic photo before start training process.
<h3>
Step 3. Prepare dataset for Ingredient Detection model
</h3>
<p>

In this step, you will crop ingredients in food based on photos which you have downloaded in previous step. Before that, you should classify food into groups which have common features. Each group will have a different model. For example, Bun (or noodle rice) in Vietnam has a lot of kinds, so I grouped them into rice-noodle group. This group contains Bun-bo (beef rice noodle), Bun-moc (meatball rice noodle), Bun-ga (chicken rice noodle), etc.. So I would like to put all images of rice-noodle group into rice-noodle folder. After that, I have used [LabelImg](https://github.com/tzutalin/labelImg) to crop ingredients in these images.

![DemoLabelImg](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/LabelImgDemo.PNG)
</br>
After that, dataset folder should have original images and its .xml files which save all information about ingredient boundary rectangle. These .xml files will be converted to csv files. You should take a subset of dataset for test, its about 10% to 20% of dataset. Move these test data into test folder and train data into train folder.
</p>

<h3>
Step 4. Train Food Recognition
</h3>
<p>

Please download [FoodRecognition branch](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/tree/FoodRecognition) in this Git Repository. I have prepared neccessary python script to train Food Recognition model. Download and unzip it, you will have folder structure below

```batch
│
├───ImageRecognizer
│       classify_image.py
│       label_image.py
│       retrain.py
```
Run PowerShell and type this command

```batch
cd <YOUR_IMAGE_RECOGNIZER_DIRECTORY>
```
To start training process

```batch
python retrain.py --image_dir <DIRECTORY_TO_DATASET> --tfhub_module https://tfhub.dev/google/imagenet/mobilenet_v2_100_224/feature_vector/1
```

I haved used mobilenet v2 model, so at tfhub_module I used this link to download model. When training process complete, you will have a model in folder **/tmp/** at root directory. Your model contains two file **"output_graph.pb"** and **"output_labels.txt"**. You need copy them to another place. To use it, run **label_image.py**

```batch
python label_image.py --graph=<DIRECTORY_TO_GRAPH_FILE> --labels=<DIRECTORY_TO_LABELS_FILE> --input_layer=Placeholder --output_layer=final_result --input_height=224 --input_width=224 --image=<YOUR_IMAGE_FILE>
```

</p>

<h3>
Step 5. Train Ingredient Detection
</h3>
<p>

Download [IngredientDetection branch](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/tree/IngredientDetection) to your PC and unzip it. You should focus to folder **models/object_detection**.</br>
Edit file **models/object_detection/training/labelmap.pbtxt**. This file contains all ingredient label so you should edit it to suitable with your case.

```json
item {
  id: 1
  name: '<Label 1>'
}
item {
  id: 2
  name: '<Label 2>'
}
.
.
.
item {
  id: n
  name: '<Label n>'
}
```
Run command to create csv file from dataset.

```batch
# In object_detection folder
python xml_to_csv.py 
```

Edit file **models/generate_tfrecord.py** from line 32. This file help you create tfrecord file which is structured file TensorFlow can understand.

```python
# TO-DO replace this with label map
def class_text_to_int(row_label):
    if row_label == 'Label 1':
        return 1
    elif row_label == 'Label 2':
        return 2
    .
    .
    .
    elif row_label == 'Label n':
        return n
    else:
        return 0
```
Run these command to create tfrecord files.

```batch
python generate_tfrecord.py --csv_input=<TRAIN_FILE_CSV> --image_dir=<TRAIN_FOLDER> --output_path=train.record
python generate_tfrecord.py --csv_input=<TEST_FILE_CSV> --image_dir=<TEST_FOLDER> --output_path=test.record
```

Edit config file of your model which you want to train. For me, I used Inception V2, so I download model from [Here](https://github.com/tensorflow/models/blob/master/research/object_detection/g3doc/detection_model_zoo.md), copy unzip folder to models/object_detection, then I edit file models/object_detection/training/faster_rcnn_inception_v2_pets.config at:</br>
* Line 9  : num_classes: <NUMBER_OF_LABEL>
* Line 106: fine_tune_checkpoint: "object_detection/<MODEL_DIRECTORY>/model.ckpt"
* Line 122: input_path: "<TRAIN_RECORD_FILE>"
* Line 124: label_map_path: "<LABELMAP_FILE>"
* Line 136: input_path: "<TEST_RECORD_FILE>"
* Line 138: label_map_path: "<LABELMAP_FILE>"

Before start training, you should compile protobuf file. Follow these commands to do that.

```batch
conda create -n tensorflow1 pip python=3.5
activate tensorflow1
pip install --ignore-installed --upgrade tensorflow-gpu
conda install -c anaconda protobuf
```
In **models/** folder

```batch
protoc --python_out=. .\object_detection\protos\anchor_generator.proto .\object_detection\protos\argmax_matcher.proto .\object_detection\protos\bipartite_matcher.proto .\object_detection\protos\box_coder.proto .\object_detection\protos\box_predictor.proto .\object_detection\protos\eval.proto .\object_detection\protos\faster_rcnn.proto .\object_detection\protos\faster_rcnn_box_coder.proto .\object_detection\protos\grid_anchor_generator.proto .\object_detection\protos\hyperparams.proto .\object_detection\protos\image_resizer.proto .\object_detection\protos\input_reader.proto .\object_detection\protos\losses.proto .\object_detection\protos\matcher.proto .\object_detection\protos\mean_stddev_box_coder.proto .\object_detection\protos\model.proto .\object_detection\protos\optimizer.proto .\object_detection\protos\pipeline.proto .\object_detection\protos\post_processing.proto .\object_detection\protos\preprocessor.proto .\object_detection\protos\region_similarity_calculator.proto .\object_detection\protos\square_box_coder.proto .\object_detection\protos\ssd.proto .\object_detection\protos\ssd_anchor_generator.proto .\object_detection\protos\string_int_label_map.proto .\object_detection\protos\train.proto .\object_detection\protos\keypoint_box_coder.proto .\object_detection\protos\multiscale_anchor_generator.proto .\object_detection\protos\graph_rewriter.proto
```
Then run this command

```batch
protoc object_detection/protos/*.proto --python_out=.
```

We are ready for training. To start, run this command

```batch
python train.py --logtostderr --train_dir=object_detection/training --pipeline_config_path=object_detection/training/<YOUR_MODEL_CONFIG_FILE>
```

Wait for training, In my case, I haved use GPU card, it consumes about 1 second per step. I run above 3000 steps and stop. Here is my result

![IngredientDetectionTrainResult](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/IngredientDetectionTrainingResult.PNG)

To finish, we need extract model from checkpoint file by using export_inference_graph.py

```batch
python export_inference_graph.py --input_type image_tensor --pipeline_config_path training/<YOUR_MODEL_CONFIG_FILE> --trained_checkpoint_prefix training/model.ckpt-<HIGHEST_NUMBER> --output_directory inference_graph
```

Your final model will be saved in folder models/inference_graph</br>
To use the model, edit file Python models/object_detection/Object_detection_image.py at lines

* line 34: IMAGE_NAME = '<INPUT_DIRECTORY>'
* line 50: NUM_CLASSES = <NUMBER_OF_INGREDIENTS>

Save and run it

```batch
python Object_detection_image.py
```

</p>

</p>

<h2>
7. My result <a name="myresult"></a>
</h2>
<p>
Project is developing, I show you current result. It will be updated continuously.</br>

* Food Recognition: I have trained food recogntion model for 10 basic Vietnamese food (Bun, Com, Pho,...). Here is a cross entropy graph.
![CrossEntropy_ImageRecognition](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/CrossEntropyTrainImageDetection.PNG)
Training process have done with result:</br>
![ImageRecognitionTrainResult](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/ImageRecognitionTrainResult.PNG)

Test accuracy is **78.8%**, it is not the best, because I do not have enough dataset. To improve it, I would like to increase number of photo in dataset about 1000 photos per food.</br>
Below is test result:</br>

Photo | Target | Output | Result
--- | --- | --- | ---
![test_bunbo](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/test_bunbo.jpg) | bun bo | bun bo: 0.99073255 | ![](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/correct.png)
![test_banhmi](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/test_banhmi.jpg) | banh mi | banh mi 0.99794585 | ![](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/correct.png)
![test_com](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/test_com.jpg) | com | com 0.85801125 | ![](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/correct.png)
![test_banhbao](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/test_banhbao.jpg) | banh bao | banh bao 0.99786466 | ![](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/correct.png)

* Ingredient Detection: Because I do not have enough photo (just 100 photos) so model still has low accuracy.
![test_ingredientDetection1](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/test_object_detection1.PNG)
</br>
Here is graphs

![](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/GraphIngredientDetection.PNG)

* Mobile App: It is unfinish. Now, it can capture photo and recognize food, then show food information. Here is some screenshots </br>


![](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/Screenshot_20180804-174258.png) | ![](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/Screenshot_20180804-174435.png)
--- | ---

</p>

</p>

<h2>
8. After this camp <a name="afterthiscamp"></a>
</h2>
<p>
After this camp, my project is still unfinish. So I have to continue my work. What I will do is shown below

.No | Task
--- | ---
1 | Prepare more data for Food Recognition. Not only 10 Vietnamese food, but also more food.
2 | Prepare more data for Ingredient Detection. I will classify these food into a lot of subclass. I will collect 500 photos per subclass.
3 | Try anothor models such as mobilenet v2,...
4 | Finish food tracking function in Android app.
5 | Create app for iOS platform.
6 | Improve accuracy and performance. I would like to serve as a service via Web API, so these model will be stored and ran on server.
</p>

<h2>
9. Summary <a name="summary"></a>
</h2>
<p>
All in all, This project was complete 70%. During the camp, I had more experience in work with Machine Learning and learnt how to do teamwork effeciently. Moreover, I had new friends from other countries, so we can share knowledge and experience together. The camp was really worthy and interesting.
</p>

<h2>
10. References <a name="reference"></a>
</h2>
<p>

1. Image recogntion: https://www.tensorflow.org/hub/tutorials/image_retraining
2. Object detection: https://github.com/EdjeElectronics/TensorFlow-Object-Detection-API-Tutorial-Train-Multiple-Objects-Windows-10
3. NutriNet: A Deep Learning Food and Drink Image Recognition System for Dietary Assessment
Simon Mezgec 1,* and Barbara Koroušic´ Seljak 2 
4. Deep Learning Based Food Recognition (Qian Yu Stanford University qiany@stanford.edu, Dongyuan Mao, Stanford University dmao@stanford.edu, Jingfan Wang Stanford University jingfan@stanford.edu)
5. FOODIMAGERECOGNITIONUSINGDEEPCONVOLUTIONALNETWORK WITHPRE-TRAININGANDFINE-TUNING (Keiji Yanai, Yoshiyuki Kawano)
 
 
</p>

<h2>
11. Acknowledgement <a name="acknowledgement"></a>
</h2>
<p>

This project is not possible without the overwhelming suppport from  **Jeju National University**, **Jeju Developement Center** and other selfless sponsors. I would like to specifically give a big thanks to **Prof. Yungcheol Byun** for being the best host ever and my mentor **Dr.Lap Nguyen Trung** for the help and guidance.
</p>
