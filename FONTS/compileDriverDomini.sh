#!/bin/bash

javac -d ../EXE/out/production/PROP/domini/ ./src/domini/*.java ./src/drivers/DriverDomain.java
jar cmvf ./src/drivers/Domain.mf ../EXE/domini/DriverDomini.jar -C ../EXE/out/production/PROP/domini/ .
