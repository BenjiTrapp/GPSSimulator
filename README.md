# (vHIL) GPS Simulator and Environment for Fault-Injection experiments
This GPS Simulator can be used for several use cases:
* Mock actual Hardware-GPS-Modules as a virtual prototype to create and test even more complex hardware modules like a FCU (Flight Control Unit) for drones.
* Virtual Prototype to make some experience with vHIL (virtual Hardware In the Loop)
* Test harness for mutation testing and fault-injection experiments

![gps](https://butlerautogroup.files.wordpress.com/2014/01/gps.jpg)

### Virtual Prototype / vHIL
This virtual prototype is assumed to be a software model, that emulates real hardware. This fact
shall make it easier to move the test process a little bit more into the front of the already established hard- 
and software development processes.

Also this virtual Prototype is meant to simulate a GPS module as a complete electromechanical system,
known as a virtual Hardware-in-the-Loop environment (vHIL), including mechanical components, microcontroller hardware 
and embedded software. The intention behind this is to accelerate software- and hardware development and integration
and test for electromechanical systems and is behavior under extreme conditions or malicious defects in the soft- or 
hardware with mutation testing and fault-injection. 


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
Sure, e was implemented during my bachelor thesis: http://edoc.sub.uni-hamburg.de/haw/volltexte/2014/2572/pdf/BA_Trapp.pdf


