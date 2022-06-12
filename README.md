# Harvest Optimizatino Model (HOM) - CSW output

This repository shows an example of saving HOM results in CSW, including how to get AWS credentials in *Vault*, to save the csv file with the output in an S3 bucket provided by Location 360.

The data used for this example uses HOM output from **Romania** 2021 harvest season.

## How to compile

This project was developed using Java programming language, and uses [Maven](https://maven.apache.org/) to compile and generate a "fat" jar with all dependencies included in it. It will work as a standalone executable that can be deployed easily in Domino, for example.

To compile and generate the standalone fat jar, use the command below in `hom-ro` folder:

```
mvn clean package
```

This will generate the jar with name `hom-ro-1.0-SNAPSHOT-jar-with-dependencies.jar` in 
`hom-ro/target` folder.


