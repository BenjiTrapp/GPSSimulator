# GPS Simulator
This GPS Simulator can be used for several use cases:
* Mock actual Hardware GPS Modules as virtual prototype to create even more complex hardware modules like a  FCU (Flight Control Unit) for drones.
* Virtual Prototype Framework to exercise fault-injection experiments.
* Test Harness for mutation tests.

![gps](https://butlerautogroup.files.wordpress.com/2014/01/gps.jpg)

# TODO: Translate and rewrite the old text ... #

### Virtueller Prototyp
Nachfolgend wird zun�chst die Package Struktur erl�utert und anschlie�end 
die Benutzung des virtuellen Prototyps sowie die Verwendung des Prototyps 
als Fault-Injection Environment beschrieben. Ferner wird auch beschrieben 
wie der virtuelle Flug graphisch dargestellt werden kann.

### Package Struktur 
Die Packages Communication, gps.data.GPSData, gps.data.GPSGenerator, gps.NMEA, gps.NMEA.NMEAParser geh�ren
zum virtuellen Prototyp. Diese Packages Bilden die Funktionalit�ten der 
einzelnen Komponenten getrennt voneinander ab.

F�r die Fault-Injection Environment wurde das Package test.FaultInjection
hinzugef�gt. An dieser Stelle werden alle relevanten Klassen gekapselt.

Das Package Starter enth�lt die Main-Methoden f�r die Ausf�hrung von sowohl 
dem virtuellen Prototypen als auch der Fault-Injection Environment.

Im Package Factories befinden sich die Factories f�r die Erstellung des 
virtuellen Prototyps und der Fault-Injection Environment. Hierbei k�nnen 
die Kollaboratur Klassen und deren Konfiguration beliebig vorgenommen 
werden. F�r einen einfachen Einstieg reicht jedoch eine Implementierung wie 
sie beispielsweise in den Starter Klassen vorhanden ist.
 
Alle f�r den Mutationstest ben�tigten Tests, befinden sich im Package test. 
Die f�r die Evaluation vorgenommenen �nderungen sind hier jedoch nicht 
beigef�gt worden. Um alle Tests hintereinander auszuf�hren kann die Klasse 
RunTestCampaign verwendet werden.

Das Package gps.NMEA.NMEAGraph enth�lt eine GUI f�r die Visualisierung des
virtuellen Flugs.

Verwendung des virtuellen Prototyps 
----------------------------------- 

Wichtig f�r die Verwendung ist, dass der Parser VOR dem starten des 
Generators ausgef�hrt wird. Dies ist notwendig, da sonst keine 
Kommunikation via ByteStream m�glich ist. Im aktuellen Starter des 
Generators wird die Art und Weise der Randomisierung vorinitialisiert 
verwendet. Um die Konfiguration der Randomisierung beliebig anzupassen, 
muss ein Objekt der Klasse DataGenTask der Factory �bergeben werden. In 
dieser Klasse k�nnen alle gew�nschten Konfigurationen zur Ausf�hrung nach 
den eigenen W�nschen enstprechend vorgenommen werden. 




Verwendung des virtuellen Prototyps als Fault-Injection Environment 
-------------------------------------------------------------------

Auch hier muss beachtet werden, dass der Parser VOR dem Generator gestartet 
wurde. Innerhalb der Factory resp. des Starters, kann aus den drei 
Fehlertypen durch die Verwendung des entsprechenden Enums ausgew�hlt 
werden. Je nach Enum �ndert sich das betrachtete Fehlerszenario, wobei hier 
zu beachten ist, dass eine Vermischung der drei Enums untereinander nicht 
gew�nscht ist und daher beim Wechseln des Szenarios, das durchzuf�hrende 
Experiment von neuem ausgef�hrt werden muss. Ein dynamischer Wechsel 
zwischen den Szenarien ist gegenw�rtig nicht implementiert worden. Im Zuge 
einer Automatisierung des Fault-Injection Experiments bietet es sich an 
eine GUI zur Planung des Experiments bereit zu stellen.




Graphische Darstellung des virtuellen Flugs 
------------------------------------------- 

Die bereitgestellte GUI verf�gt �ber einen FileChooser mit dessen Hilfe die 
zu visualisierende Log-Datei ausgew�hlt werden kann. Beim �ffnen wird 
automatisch das Verzeichnis "log" referenziert, in dem auch die automatisch 
generierten Dateien gespeichert werden. 

Als Grundlage f�r die Visualisierung dient der GGA-Datensatz. Jedoch wurde 
aufgrund der bereits bestehenden Komplexit�t auf eine Visualisierung der 
H�he derzeitig verzichtet, da hierbei lediglich die Nachvollziehbarkeit des 
Flugs aus einer Sicht von oben erm�glicht werden sollte. 

F�r eine m�gliche Weiterentwicklung, k�nnte das Tool auch zu einem "Live-
Ticker" umfunktioniert werden. Dar�ber hinaus k�nnten die einzelnen 
Positionspunkte farbig f�r die Repr�sentation eines bestimmten 
H�henbereichs dargestellt werden. 


Kleiner Fun-Fact: als Startpunkt f�r den virtuellen Flug dient der Raum 
7.01 des E&I HAW-Geb�udes am Berliner Tor 7. 
