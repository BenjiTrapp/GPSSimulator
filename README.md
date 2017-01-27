# (vHIL) GPS Simulator and Environment for Fault-Injection experiments
This GPS Simulator can be used for several use cases:
* Mock actual Hardware-GPS-Modules as a virtual prototype to create and test even more complex hardware modules like a FCU (Flight Control Unit) for drones.
* Virtual Prototype to make some experience with vHIL (virtual Hardware In the Loop)
* Test harness for mutation testing and fault-injection experiments

![gps](https://butlerautogroup.files.wordpress.com/2014/01/gps.jpg)

### Virtual Prototype / virtual Hardware IN the Loop (vHIL)
This virtual prototype is assumed to be a software model, that emulates real hardware. This fact
shall make it easier to move the test process a little bit more into the front of the already established hard- 
and software development processes.

Also this virtual Prototype is meant to simulate a GPS module as a complete electromechanical system,
known as a virtual Hardware-in-the-Loop environment (vHIL), including mechanical components, microcontroller hardware 
and embedded software. The intention behind this is to accelerate software- and hardware development and integration
and test for electromechanical systems and is behavior under extreme conditions or malicious defects in the soft- or 
hardware with mutation testing and fault-injection. 

### Architecture of the GPS Simulator
![architecture](https://github.com/BenjiTrapp/GPSSimulator/blob/master/doc/Architektur.PNG)
* __GPS-Generator__: Creates with TimerTaks and a Timer randomized NMEA sentences.
* __GPS-Parser__: Syntactic analyse of the generated NMEA sentences.
* __Logger__: Simple Logging Facade (for Log4J)
* __Telemetry Dummy__: Simulates some "code" or "hardware component" that works with the the parsed and processed NMEA sentences as a serialized  object.
* __Communication Module__: There are currently three implementations available
    * A StringWriter and StringReader variant to simulate the communication between "real hardware" components
    * The ComJammer module can be used to perturb the simulated communication and inject the byte manipulation functions
    * A module for real serial communication like RS-232 to test the robustness of real hardware modules with perturbed NMEA sentences.

### Grammar of a NMEA sentence
 __\<$GP><- - ->,\<X>,...,\<Xn>*\<Checksum>\<CR>\<LF>__
* __\<$GP>__&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Talker ID ($GP = GPS, $GL = GLONAS)
* __<- - ->__&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Name of the Sentence f.e. RMC, GGA, DTM, …
* __\<x>,…,\<Xn>__&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Predefined amount of data in the context of the NMEA sentence
* __\*\<Checksum>__&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Result of the checksum calculation
* __\<CR>\<LF>__&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; CR = Carriage Return and LF = Linefeed



### Fault-Injection Environment

![fi-env](https://github.com/BenjiTrapp/GPSSimulator/blob/master/doc/FI-System.PNG)
* __Controller__: Steers the Fault-Injection Experiment. The Controller itself is a tiny piece of code, that runs on 
a local or distributed on remote machines. Currently the Controller is encapsulated in the PerturbationBuilder and the GPSSimulatorStarter. Check this class to set up your experiment
* __Workload generator__: Creates the execution commands, that shall be processed by the SUT. This part is represented by the GPSGenerator.
* __Workload library__: Contains diverse scenarios for the go live of the SUT and is represented by the available NMEA sentences.
* __Monitor__: Observers the execution of the commands and and channels the collection of the data, if needed.
    *__Data collectors__: Instance to collect the data -> typically the used logger(s) and maybe later a neo4j database.
* __Data analyzer__:  Processes and analyses the data -> must be done manually at the moment. When the neo4j database is appended, this part will be automated
* __Fault injector__: Module to inject faults into the SUT and executes the commands of the workload generator. This is represented by the ComJammer Module to perturb the communication and functions that manipulates the GPSData directly. 
* __Fault Library__: Contains the types of faults that shall be injected into the SUT -> Currently there are two types available, PerturbationStrategies and ByteManipulations. To test the exception handling, there can some BMUnit-Tests be used.

# Q & A

How do I start the vHIL experiment?
----------------------------------- 
In the sources root directory are two classes with different purposes:
* The Class GPSSimulatorStarter is used to start the GPS Simulation. In short, that means, GPS Positions will be randomly generated and get parsed by a fictive Telemetry Dummy for further processing as data objects.
* To spread the Chaos in form of a Fault-Injection Experiment, you can set up your favourite Perturbation Functions and compromise the generated data of the GPS Simulator.

Is there a graphical presentation of my virtual flight available?  
------------------------------------------- 
Sure, just take a look at this class: https://github.com/BenjiTrapp/GPSSimulator/blob/master/src/gps/NMEA/graph/NMEAGraphGI.java

This class is a SWING GUI that can interpret the generated NMEA Sentences that were generated. This GUI helps to proof that
a dash in the coordinates occurred f.e. 

For general graphical presentation of the log files Apache Chainsaw me be coming handy for you. With this tool is a graphical
log viewer, so it's quite easy to analyze the log files.

Link: https://logging.apache.org/chainsaw/
Maybe later there will be some support for neo4j and keylines

Is there some more information available?
-----------------------------------------
Sure, but only on german. Take a look in the doc folder.


