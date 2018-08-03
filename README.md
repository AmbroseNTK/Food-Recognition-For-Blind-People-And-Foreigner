![Title](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/JMLC2018-title.png)
<h1>
Food Recognition For Blind People And Foreigner
</h1>
<h2>
Content
</h2>
<p>

1. About me
2. What is it?
3. Why?
4. My Plan
5. Methodology
6. My result
7. After this camp
8. Summary
9. Reference
</p>

<h2>
1. About me
</h2>
<p>

Welcome to my MLC GitHub page. My name is ***Kiet Nguyen Tuan (Ambrose)*** . I worte these lines when I was a senior student at ***Hoasen University, Vietnam***. Because of special love with Machine Learning, so I have come to this ML Camp hosted at Jeju National University to do my project. I also want to meet new friends from other countries. It was a experiement I never forget.
</p>

<h2>
2. What is it?
</h2>
<p>

In this project, I would like to develop a collection of model that can use to recognize food and its ingredients. For me, I create these models for Vietnamese food first. Developers in other countries can contribute their national food models to my collection. After that, every people can use these models free, so that apply to their app.
</p>

<h2>
3. Why?
</h2>
<p>
When I found a idea for this camp, I realised that food is an important part of country's culture so that every people should have knowledge about food of place where they stay at. Moreover, disable people, specially blind people also need help to know what food they eat. For foreigner, I will be an useful food's guideline at place their visit and also be an assitant to track what their eat and suggest what they should or should not eat.
<p>

<h2>
4. My schedule
</h2>
<p>
Due to shortly time of this camp (July 22 to August 7, 2018) and I do not have enough data, I cannot finish all project at the camp. But I still continue to finish.</br>
Here is my plan</br>

Day | Date | What I did
--- | --- | ---
1 | July 22, 2018 | I came to Jeju island lately due to a storm at Shanghai.
2 | July 23, 2018 | I prepared a introduction presentation and attended MLC Opening Ceremony.
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
5. Methodology
</h2>
<p>
After researched machine learning and implemented libraries, I refer to use TensorFlow with Python programming language and Java/Android for my mobile app. Nowaday, I suppose that TensorFlow is one of the best frameworks for Machine Learning and also Deep Learning. Moreover, TensorFlow had large developer community which ready to help when I go to problems.</br>
Project has 3 parts:</br>

* Food Recognition: It is a model to recognize food in a photo in summary. In my case, it returns result like Bun, Pho, Com, Banh-mi etc. I have downloaded above 600 photo per food on Google Image. After that, I have deleted unneccesary images and just keep correct images. I used these images to train the model.
* Ingredient Detection: According to result which I have after applied Food Recognition model I use ingredient Detection model to detect ingredient in food one by one, so that I can calculate its nutrition, predict its taste. Because of kind variation of food, each food has Ingredient Detection model differently. For example, Pho is a popular food in Vietnam, and its ingredient change its nutrition a lot.

Pho-bo | Pho-ga
--- | ---
![phobo](http://www.savourydays.com/wp-content/uploads/2013/01/PhoBoHN.jpg) | ![phoga](http://giadinh.mediacdn.vn/zoom/655_361/2014/7-1412602058-tu-lam-pho-ga-7-1412654910607-crop-1412654928337.jpg)

The first photo is Pho-bo means Pho with beef. The second is Pho-ga means Pho with chicken. All of them is Pho, but they have different ingredient. Because of nutrition difference of beef and chicken, so Pho-bo and Pho-ga have different nutrition. So Ingredient Detection is an important step to solve this problem which is common in anothor food's culture.
* Mobile App: This is a place where I apply these models all together so that reach project target. I developed an Android app with TensorFlow Lite for mobile. The mobile app capture food photo, then, it uses these models to extract what food and ingredient, finally, it returns all food's information (Name, original, ingredient, taste prediction, nutrition values,...). To help blind people, I use module text-to-speed, so that translate these information into voice, and blind people can hear it.

![FoodRecognition](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/ActivityDiagram.jpg)
</p>

<h2>
6. My result
</h2>
<p>
Project is developing, I show you current result. It will be updated continously.</br>

* Food Recognition: I have trained food recogntion model for 11 basic Vietnamese food (Bun, Com, Pho,...). Here is a cross entropy graph.
![CrossEntropy_ImageRecognition](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/CrossEntropyTrainImageDetection.PNG)
Training process have done with result:</br>
![ImageRecognitionTrainResult](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/ImageRecognitionTrainResult.PNG)

Test Accurancy is 78.8%, it is not the best, because I do not have enough dataset. To improve it, I would like to increase number of photo in dataset about 1000 photos per food.</br>
Below is test result:</br>

Photo | Target | Output | Result
--- | --- | --- | ---
![test_bunbo](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/test_bunbo.jpg) | bun bo | bun bo: 0.99073255 | ![](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/correct.png)
![test_banhmi](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/test_banhmi.jpg) | banh mi | banh mi 0.99794585 | ![](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/correct.png)
![test_com](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/test_com.jpg) | com | com 0.85801125 | ![](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/correct.png)
![test_banhbao](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/test_banhbao.jpg) | banh bao | banh bao 0.99786466 | ![](https://github.com/AmbroseNTK/Food-Recognition-For-Blind-People-And-Foreigner/blob/master/img/correct.png)

</p>