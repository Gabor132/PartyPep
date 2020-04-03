# PartyPep

A simple Java Spring REST-full application meant to exercise basic programming concepts. From different refactoring methods to testing
frameworks and continuous integration setups.



### Trello Board

 * Board available [here](https://trello.com/b/ANK14c63/partypeps)
 * Methodology: Agile as f*ck with a flavour of Kanban Freestyle



### Documentation

Available on [Google Drive](https://drive.google.com/drive/folders/1jC2TNsvXGO7mnBnm5ZCRgGYg29s5uUdr)



### Clone & Go

1. Clone repository
2. Setup you local database and edit **jdbc.properties** accordingly for the DEV profile (IT and PROD should remain unchanged)
3. Create your IDE configuration: `mvn clean install`
4. Run **com.gabor.partypeps.PartyPepsApplication.class** to check if it starts
5. Download [Postman](https://www.postman.com) or [Fiddler](https://www.telerik.com/download/fiddler-everywhere) in order to test the API

### Continuous Integration:

For continuous integration we are using [CircleCI](https://circleci.com/signup) 
Sign up and let me know to add you to the team.



### Deployment:

At the moment, the **PROD** profile does not exist, but the **IT** environment does.
Deployment procedure is the following:
1. A push to any branch will trigger a CircleCI job that will compile and run all the tests (Unit or Integration).
2. If the branch that got pushed is Heroku and CircleCI tests pass, then the code is redeployed on the Heroku app that listens to and only to the Heroku branch.

**Observation:** integration tests do not fail the application build; 
only unit tests will fail the build completely and not allow the automated deployment to heroku.



### Useful links:

* [Application dashboard on Heroku](https://dashboard.heroku.com/apps/partypeps)
* [Application demo on Heroku](https://partypeps.herokuapp.com)
* [CircleCI](https://circleci.com/gh/Gabor132/PartyPep)



**If you want to check if the application works, do https://partypeps.herokuapp.com/users/all and see if you get anything**
