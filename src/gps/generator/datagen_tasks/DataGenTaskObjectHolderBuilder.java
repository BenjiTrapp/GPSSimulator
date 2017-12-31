package gps.generator.datagen_tasks;

import java.util.*;

import static gps.generator.GPSGenEnumHolder.*;
import static gps.generator.GPSGenEnumHolder.Patterns.*;

public class DataGenTaskObjectHolderBuilder {
    private Map<Patterns, Modes> dataGenTaskValues;
    private AngleUnits angleUnit;
    private DataGenTaskObjectHolder dataGenTaskObjectHolder;

    public DataGenTaskObjectHolderBuilder(){
        this.dataGenTaskValues = Collections.synchronizedMap(new HashMap<Patterns, Modes>(){{put(LATITUDE, null);
                                                                                             put(LONGITUDE, null);
                                                                                             put(ALTITUDE, null);
                                                                                             put(VELOCITY, null);
                                                                                             put(DOP, null); }});
        this.dataGenTaskObjectHolder = new  DataGenTaskObjectHolder(dataGenTaskValues, angleUnit);
    }

    public synchronized DataGenTaskObjectHolderBuilder addMode(Patterns pattern, Modes mode){
        this.dataGenTaskObjectHolder.setDataGenTaskValues(pattern, mode);
        return this;
    }

    public synchronized DataGenTaskObjectHolderBuilder addAngleUnit(AngleUnits angleUnit){
        this.dataGenTaskObjectHolder.setAngleUnit(angleUnit);
        return this;
    }

    public synchronized DataGenTaskObjectHolder build(){
        this.dataGenTaskValues.keySet().forEach(pattern -> {if(dataGenTaskValues.get(pattern) == null) dataGenTaskValues.replace(pattern,Modes.RANDOM);});
        if(this.angleUnit == null) {this.angleUnit = AngleUnits.GON;}
        return new DataGenTaskObjectHolder(this.dataGenTaskValues, this.angleUnit);
    }
}
