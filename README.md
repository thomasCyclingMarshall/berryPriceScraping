#READ ME

##To Run:
If you have Maven on your command line, you should be able to run
`mvn test` in the top directory.
This will install the dependencies, run the tests and then scrape the sepcified page and spit out the required JSON.

I've been using Java 1.8 and Maven 3.5.0

##Dependencies Used
* JSoup - for HTML parsing
* GSON - for JSON generation
* Lombok - to generate data classes.