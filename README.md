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
