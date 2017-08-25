Content API
=================================

This is a web service which backend as a service which support both static and dynamic content written in java using Play Framework.
In this service, we can create any collection, define its properties with constraints. During adding content in collection, validation is done to make sure right content is going in system. Content can be added dynamically using REST Apis and fetched by providing right auth token.
No UI is added in this code as i didn't have all rights to do that.

How to run this:
=================================

Step 1: Clone this project using : git clone https://github.com/MiteshSharma/ContentApi.git

Step 2: Go to folder ContentApi which is created after cloing project.

Step 3: Run ./activator clean compile to compile project. You can find any compilation errors if any. This is an optional step.

Step 4: Run ./activator run to run this project. Default port is 9000 on this this web service will run.

Step 5: Go to browser or terminal to check for http://localhost:9000 host port. If this runs successfully, this means project is running properly.

Step 6: If you want to deploy this on production and want to create a distribution file then run: ./activator dist

No UI code is added to this code as not had all rights to open source it. Although i used angularjs for this project.

Database used:
=================================
Mongo database is used in this project, as it is flexible and any number of columns can be added in it dynamically. If needed any different database can code can be used just by changing code in repository folder. We are using one master and one shard database for better manageability of content, whose configration can be updated in conf/application.conf file.

Media Upload
================================
Media upload is being done on S3. Right aws configration can be added in conf/application.conf file.

Play Framework:
=================================
Play framework tutorial for better understanding : https://www.slideshare.net/mitesh_sharma/play-framework-a-walkthrough 

Video (ContentApi backend with frontend)
=================================
![Contet API web server](https://img.youtube.com/vi/UKPuEffJJ1A/0.jpg)

Click Here to view video : https://youtu.be/UKPuEffJJ1A

