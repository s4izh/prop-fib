# PROP Grup 13.4
Projectes de Programació, Grup 13, subgrup 13.4. Professor: Salvador Medina Herrera.

## GESTOR DE DOCUMENTS

## Membres del grup

- De Prado Rojo, Dante
- Salvador Nogues, Pol
- Sanz Martinez, Sergio
- Trabsa Biskri, Youcef

## Emails

- dante.de.prado@estudiantat.upc.edu
- pol.salvador@estudiantat.upc.edu
- sergio.sanz.martinez@estudiantat.upc.edu
- youcef.trabsa@estudiantat.upc.edu

## Elements del directori

### DOCS:

Conté tota la documentació del projecte:

- diagrama de casos d'ús amb descripció de cada un d'ells
- diagrama estàtic complet del model conceptual de dades en versió disseny amb breu descripció dels atributs i mètodes de les classes
- relació de les classes implementades per cada membre de l'equip
- descripció de les estructures de dades i algorismes emprats per implementar les funcionalitats de l'entrega
- manual d'ús del programa

### EXE:

Fitxers executables (*.class*) de totes les classes que permeten provar les funcionalitats principals implementades.
Hi ha subdirectoris per cada un dels tipus de classes: test, excepcions, funcions, tipus, que segueixen l'estructura
determinada pels *packages*.

### FONTS:

Codi de les classes de domain associades a les funcionalitats principals implementades fins al moment. Inclou tambe els
tests JUnit. Tots els fitxers estan dins dels subdirectoris que segueixen l'estructura de packages.

El directori inclou un Main amb el que executar l'aplicació

- directori `src`: amb el codi font dividit en packages
  - domain
  - persistence
  - presentation
  - views
  - drivers
- directori `tests`: amb tots els tests utilitzats

### inputDocuments:

Directori amb documents d'exemple que es poden utilizar per probar el programa

### data:

Directori on guardarem les dades internes necesàries de l'aplicació i les metadades de cada
document (aquestes segones en un subdirectori `/data/documents`)

### lib:

S'hi troben les llibreries externes que hem hagut d'utilitzar.

## Provar el programa

Executar l'script `setup.sh` per crear els directoris buits necesaris.

Posteriorment executar el main que trobarem al directori `/FONTS/src`

Per compilar el Driver del DomainController cal anar a `/FONTS/src/` i executar `compileDriverDomain.sh`
Per executarlo cal anar al directori `/EXE i executar playDriverDomain.sh`

Per al Driver que prova funcionalitats de Author cal fer el mateix pero amb les scripts per a Author.
