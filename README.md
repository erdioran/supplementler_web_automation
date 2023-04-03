
## Run 
terminal -> mvn clean install && allure serve allure-results


RunCucumberTest class -> tags ->

regression = @REGRESSION
web = @WEB
mweb = @MWEB
admin = @ADMIN

etc...

To run a single scenario, you can @TEST the scenario tag and the run tag.


## Report 
It is created in localhost after the test is complete(allure report) 

## Project Detail


-  The browser can be selected by changing the "browser" variable of the [app.properties](https://github.com/DikeyVitamin/qa-web/blob/master/src/test/resources/app.properties) file.

-  For test url selection, change testEnv parameters in [app.properties](https://github.com/DikeyVitamin/qa-web/blob/master/src/test/resources/app.properties) file.

-  Gets "url" and "test data" from file [data.json](https://github.com/DikeyVitamin/qa-web/blob/master/src/test/resources/data.json)

