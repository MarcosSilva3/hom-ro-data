# Harvest Optimization Model (HOM)
## In-season model run

In this repository we have a Java project where we update all input data necessary in order to properly run the model for **Romania**.

## How to compile

This project was developed using Java programming language, and uses [Maven](https://maven.apache.org/) to compile and generate a "fat" jar with all dependencies included in it. It will work as a standalone executable that can be deployed easily in Domino, for example.

To compile and generate the standalone fat jar, use the command below in `hom-ro` folder:

```
mvn clean package
```

This will generate the jar with name `hom-ro-in-season-1.0-SNAPSHOT-jar-with-dependencies.jar` in `hom-ro-in-season/target` folder.
