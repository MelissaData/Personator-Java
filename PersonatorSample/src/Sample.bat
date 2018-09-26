@echo off
javac -classpath ".\melissadata\personator\org.apache.sling.commons.json-2.0.20.jar;" .\melissadata\personator\*.java .\melissadata\personator\view\*.java ./melissadata\personator\model\*.java
java -classpath ".\melissadata\personator\org.apache.sling.commons.json-2.0.20.jar;"; melissadata.personator.Main melissadata.personator.view.PersonatorController melissadata.personator.view.PersonatorTransactionController melissadata.personator.view.RootLayoutController melissadata.personator.model.Option
del .\melissadata\personator\*.class 
del .\melissadata\personator\view\*.class 
del .\melissadata\personator\model\*.class