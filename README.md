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

# Deployment
At the moment, the so called "PROD" does not really exist, but the IT environment does.
The deployment procedure is like this:
1. A push to any branch will trigger a CircleCI job that will compile and run all the tests (Unit or Integration).
2. If the branch that got pushed is Heroku and CircleCI tests pass, then the code is redeployed on the Heroku app that listens to and only to the Heroku branch.

  <b>Observation: The Integration Tests do not fail the building of the application, only Unit tests will fail the build completely and not allow the automated deployment to heroku.</b>

Heroku link: https://dashboard.heroku.com/apps/partypeps
Application link on Heroku: https://partypeps.herokuapp.com/
CircleCI link: https://circleci.com/gh/Gabor132/PartyPep

! If you want to check if the application works, do https://partypeps.herokuapp.com/users/all and see if you get anything
