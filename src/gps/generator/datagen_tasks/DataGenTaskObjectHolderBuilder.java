package gps.generator.datagen_tasks;

import java.util.*;

import static gps.generator.GPSGenEnumHolder.*;

public class DataGenTaskObjectHolderBuilder {
    private Map<Patterns, Modes> dataGenTaskValues;
    private AngleUnits angleUnit;
    private DataGenTaskObjectHolder dataGenTaskObjectHolder;

    public DataGenTaskObjectHolderBuilder(){
        this.dataGenTaskValues = Collections.synchronizedMap(new HashMap<>());
        this.dataGenTaskValues.put(Patterns.LATITUDE, null);
        this.dataGenTaskValues.put(Patterns.LONGITUDE, null);
        this.dataGenTaskValues.put(Patterns.ALTITUDE, null);
        this.dataGenTaskValues.put(Patterns.VELOCITY, null);
        this.dataGenTaskValues.put(Patterns.DOP, null);
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
