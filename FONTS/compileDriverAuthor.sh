#!/bin/bash

javac -d ../EXE/out/production/PROP/domini/ ./src/domini/*.java ./src/drivers/DriverAuthor.java
jar cmvf ./src/drivers/Author.mf ../EXE/author/DriverAuthor.jar -C ../EXE/out/production/PROP/domini/ .
