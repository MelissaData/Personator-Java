@echo off
cd target
java -classpath "lib\gson-2.8.9.jar;" com.melissadata.personator.Main com.melissadata.personator.view.PersonatorController com.melissadata.personator.view.RootLayoutController com.melissadata.personator.model.PersonatorOptions com.melissadata.personator.model.PersonatorTransaction
cd ..