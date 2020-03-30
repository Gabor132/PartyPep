# PartyPep

A simple Java Spring REST-full application meant to exercise basic programming concepts. From different refactoring methods to testing
frameworks and continuous integration setups.

# Trello Board
Board link: https://trello.com/b/ANK14c63/partypeps
Methodology: Agile as f*ck with a flavour of Kanban Freestyle

# Documentation
 - Google Drive: https://drive.google.com/drive/folders/1jC2TNsvXGO7mnBnm5ZCRgGYg29s5uUdr

# Clone & Go

1. Clone the repo
2. Fetch the branches
3. Create project with existing sources
4. Check jdbc.properties for the DB configuration and change accordingly for DEV (IT and PROD should stay the same)
5. Setup your local DB and change the jdbc.properties
6. Make sure to never push changes of the jdbc.properties
7. Create your IDE configurations (go with a clasic "mvn clean install")
8. Do a run of the com.gabor.partypeps.PartyPepsApplication.class (see if everything starts)
9. Get yourself Postman

# Continous Integration
CircleCI is what we use, here is the link https://circleci.com/signup/ sign up and let me know to add you to the team.

# Development

For an easier start in development, the following commands can be used (recommend adding the running configurations in the IDE)
1. clean spring-boot:run -PDEV (will run the application directly in DEV mode, only running the JUnit tests)
2. clean spring-boot:run -PIT (same but in the IT configuration)
3. clean spring-boot:run -PPROD (same but in PROD configuration, to work, DB, URL and Security properties should be set as ENV variables)
4. clean test (will run only the UNIT tests using the DEV setup)
5. spring-boot:start -P{ENV} integration-test spring-boot:stop (will only run the JUnit tests as well as the Integration tests)

# Deployment
At the moment, the so called "PROD" does not really exist, but the IT environment does.
The deployment procedure is like this:
1. A push to any branch will trigger a CircleCI job that will compile and run all the tests (Unit and Integration).
2. If the branch that got pushed is Heroku and the CircleCI <b>UNIT</b> tests pass, then the code is redeployed on the Heroku app that listens to and only to the Heroku branch.

  <b>Observation: The Integration Tests do not fail the building of the application, only Unit tests will fail the build completely and not allow the automated deployment to heroku.</b>

Heroku link: https://dashboard.heroku.com/apps/partypeps </br>
Application link on Heroku: https://partypeps.herokuapp.com/ </br>
CircleCI link: https://circleci.com/gh/Gabor132/PartyPep </br>

! If you want to check if the application works, do https://partypeps.herokuapp.com/users/all and see if you get anything
