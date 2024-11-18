# Repository per la componente vigilsanpratiche
Questo è il repository per lo sviluppo cloud native della componente vigilsanpratiche in tecnologia spring-boot con la reference per lo sviluppo API.

Il progetto spring-boot è già stato inizializzato con il nome della componente e si basa sulla versione di spring-boot __2.7.1__

## implementazoni di default e modalità di sviluppo ##

### sviluppo contract first ###
Il progetto è impostato per lo sviluppo in modalità contract-first:
* nella cartella ```src/main/resources/META-INF``` è presente il file openapi.yaml contenente uno scheletro di specifica openapi 3, contenente alcune definizioni di base e nel quale è necessario aggiungere le definizioni delle risorse specfiche dell'applicativo
* il pom.xml prevede l'utilizzo del plugin swagger (customizzato CSI) per la generazione delle sole interfacce JAX-RS a partire da tale file di specifiche. La generazione avviene in fase di compilazione (quindi può essere scatenata con il comando ```mvn clean compile```)
* il generatore non genera invece le classi di implementazione delle API, che devono essere definite manualmente ed implementare le interfacce JAX-RS (vedere esempio ```StatusApiServiceImpl```)
* le api sono definite sotto il prefisso ```/api```

* sono predefinite (ed implementate) le seguenti api di base:
  * ```/api/status```, che è un health check ad uso esterno (a differenza delle api standard di health check che non sono esposte all'utente finale)

### altre implementazioni predefinite ###

Non vi sono altre implementazioni predefinite

### api strumentali ###

Il progetto è impostato per esporre una implementazione minimale di default delle API strumentali per:
* health check:
  * liveness: ```/actuator/health/liveness```
  * readyness: ```/actuatore/health/readiness```

E' possibile estendere l'implementazione di default a seconda delle esigenze specifiche.

## Opzioni per lo sviluppo
Per sviluppare in ottica _Cloud Native_ questa componente sono possibili le seguenti alternative

### Sviluppo locale della componente isolata
Lanciando il comando docker che utilizza una cartella .m2 di progetto
```
docker run -v ${PWD}:/usr/src/mymaven -v ${PWD}/.m2:/root/.m2 -p 8080:8080 -p 9009:9009 -it -w /usr/src/mymaven docker-base.ecosis.csi.it/maven:3.8.1-j11-csi-r2 mvn spring-boot:run ""--settings .mvn/conf/settings.xml"" "-Dspring-boot.run.jvmArguments=""-Dspring.profiles.active=dev""  ""-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9009"""
```
Lanciando il comando docker che utilizza la cartella .m2 globale
```
docker run -v ${PWD}:/usr/src/mymaven -v ${HOME}/.m2:/root/.m2 -p 8080:8080 -p 9009:9009 -it -w /usr/src/mymaven docker-base.ecosis.csi.it/maven:3.8.1-j11-csi-r2 mvn spring-boot:run ""--settings .mvn/conf/settings.xml"" "-Dspring-boot.run.jvmArguments=""-Dspring.profiles.active=dev""  ""-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9009"""
```
Oppure eseguendo lo script __run_with_local_m2.ps1__ (per utilizzare la .m2 di progetto) o __run_with_global_m2.ps1__ ((per utilizzare la .m2 globale)  da __Powershell__
```
Set-ExecutionPolicy unrestricted
./run_with_local_m2.ps1
```
```
Set-ExecutionPolicy unrestricted
./run_with_global_m2.ps1
```
### Sviluppo contemporaneo di più componenti
Nel caso in cui sia necessario sviluppare più componenti che comunicano tra di loro allo stesso tempo (ad esempio, API e bff), sono necessarie delle modifiche al procedimento. I container utilizzano di default una rete virtuale e quindi, nonostante sia possibile mappare le loro porte alle porte dell'host, essi non possono chiamarsi tramite l'utilizzo di localhost. Per ovviare a questo problema, è possibile lanciare i container facendo il bind alla rete dell'host utilizzando l'opzione `--network host`. Il comando risultante è quindi il seguente
```
docker run -v ${PWD}:/usr/src/mymaven -v ${PWD}/.m2:/root/.m2 -p 8080:8080 -p 9009:9009 -it --network host -w /usr/src/mymaven docker-base.ecosis.csi.it/maven:3.8.1-j11-csi-r2 mvn spring-boot:run ""--settings .mvn/conf/settings.xml"" "-Dspring-boot.run.jvmArguments=""-Dspring.profiles.active=dev""  ""-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9009"""
```
Utilizzando questo comando, il container farà il bind alla rete dell'host e sarà disponibile alla porta configurata dal parametro `server.port=<porta>` presente nel file application.properties (o configurabile aggiungendo `"-Dserver.port=<porta>"` al fondo del comando scritto sopra) e, nel caso di multipli container, sarà necessario che non si presentino sovrapposizioni di porte. Si suggerisce di cambiare le porte di default **solo se necessario** (ad esempio, per lo sviluppo di due componenti quarkus)

Utilizzando l'esempio di un bff che deve comunicare con delle API, avremo che, lanciando il container delle API con
```
docker run -v ${PWD}:/usr/src/mymaven -v ${PWD}/.m2:/root/.m2 -p 8080:8080 -p 9009:9009 -it --network host -w /usr/src/mymaven docker-base.ecosis.csi.it/maven:3.8.1-j11-csi-r2 mvn spring-boot:run ""--settings .mvn/conf/settings.xml"" "-Dspring-boot.run.jvmArguments=""-Dspring.profiles.active=dev""  ""-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9009"""
```
 
ed il container del bff con

```
docker run -v ${PWD}:/usr/src/mymaven -v ${PWD}/.m2:/root/.m2 -p 8080:8080 -p 5005:5005 -it --network host -w /usr/src/mymaven docker-base.ecosis.csi.it/maven:3.8.1-j11-csi-r2 mvn spring-boot:run   ""--settings .mvn/conf/settings.xml"" "-Dspring-boot.run.jvmArguments=""-Dspring.profiles.active=dev"" ""-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9010"" ""-Dserver.port=9090"""
```
quest'ultimo potrà comunicare con le API chiamando `localhost:8080`.

**NOTA IMPORTANTE**  
Per la messa in **produzione**, è necessario che i valori delle porte su cui l'applicativo sale siano quelli di default. I cambiamenti alle porte necessari per lo sviluppo in contemporanea di più componenti **NON** devo essere presenti nell'application.properties per la produzione.

