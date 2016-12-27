# GPS Simulator
This GPS Simulator can be used for several use cases:
* Mock actual Hardware GPS Modules as virtual prototype to create even more complex hardware modules like a  FCU (Flight Control Unit) for drones.
* Virtual Prototype to make some experience with vHIL (virtual Hardware In the Loop)
* Test Harness for mutation and fault-injection experiments

![gps](https://butlerautogroup.files.wordpress.com/2014/01/gps.jpg)

# TODO: Translate and rewrite the old text ... #

### Virtual Prototype / vHIL
This virtual prototype is assumed to be a software model, that emulates real hardware. This fact
shall make it easier to move the testprocess a little bit more into the front of the already established hard- 
and software development processes.

Also this virtual Prototype is meant to simulate a GPS module as a complete electromechanical system,
known as a virtual Hardware-in-the-Loop environment (vHIL), including mechanical components, microcontroller hardware 
and embedded software. The intention behind this is to accelerate software- and hardware development and integration
and test for electromechanical systems and is behavior under extreme conditions or malicious defects in the soft- or 
hardware with mutation testing and fault-injection. 


# Q & A

How do I start the vHIL experiment?
----------------------------------- 
In the Package "starter" there are three classes:
* GPSParserStarter - Make sure to start this class first. In this class you can see later the generated NMEA sentences
* GPSGeneratorStarter - Start this class as second class. This class will shedule the data generation tasks for the 
NMEA Sentences. Currently only RMC and GGA Sentences are supported
* FIGPSGeneratorStarter - This class is used to convert the virtual Prototype into a fault-injection environment. Currently
a dynamic switching and triggering between the pertubation modes is not supported but my be added later 


Is there a graphical presentation of my virtual flight available?  
------------------------------------------- 
Sure, just take a look at this class: https://github.com/BenjiTrapp/GPSSimulator/blob/master/src/gps/NMEA/graph/NMEAGraphGUI.java

This class is a SWING GUI that can interpret the generated NMEA Sentences that were generated. This GUI helps to proof that
a dash in the coordinates occurred f.e. 

For general graphical presentation of the log files Apache Chainsaw me be coming handy for you. With this tool is a graphical
log viewer, so it's quite easy to analyze the log files.

Link: https://logging.apache.org/chainsaw/