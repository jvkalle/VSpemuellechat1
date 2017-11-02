package com.example.vspemuellechat.ch.ethz.inf.vs.a3.clock;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.valueOf;

/**
 * Created by peter on 29.10.17.
 */

public class VectorClock implements Clock {



    private Map<Integer, Integer> vector = new HashMap<>();

    private Map<Integer, Integer> getVector() {
        return vector;
    }

    int getTime(Integer pid) {
        return vector.get(pid);
    }

    void addProcess(Integer pid, int time) {
        vector.put(pid,time);
    }

    /**
     * Update the current clock with a new one, taking into
     * account the values of the incoming clock.
     *
     * E.g. for vector clocks, c1 = [2 1 0], c2 = [1 2 0],
     * the c1.update(c2) will lead to [2 2 0].
     * @param other
     */
    @Override
    public void update(Clock other){
        Map<Integer, Integer> newVecotr = new HashMap<>();

        newVecotr.putAll(((VectorClock)other).getVector());
        newVecotr.putAll(vector);

        vector=newVecotr;

        for (int pid:((VectorClock)other).getVector().keySet()){

            if(vector.get(pid) < ((VectorClock)other).getTime(pid))

                vector.put(pid,((VectorClock)other).getTime(pid));
        }

    }

    /**
     * Change the current clock with a new one, overwriting the
     * old values.
     * @param other
     */
    @Override
    public void setClock(Clock other){
        vector = new HashMap<>();

        vector.putAll(((VectorClock)other).getVector());

    }

    /**
     * Tick a clock given the process id.
     *
     * For Lamport timestamps, since there is only one logical time,
     * the method can be called with the "null" parameter. (e.g.
     * clock.tick(null).
     * @param pid
     */
    @Override
    public void tick(Integer pid){
        Integer time = vector.get(pid);
        time++;
        vector.put(pid,time);
    }

    /**
     * Check whether a clock has happened before another one.
     *
     * @param other
     * @return True if a clock has happened before, false otherwise.
     */
    @Override
    public boolean happenedBefore(Clock other){

        for (int pid:vector.keySet()){

            if(vector.get(pid)>((VectorClock)other).getTime(pid))
                return false;
        }

        if (getVector().equals(((VectorClock)other).getVector()))
            return false;

        return true;
    }

    /**
     * toString
     *
     * @return String representation of the clock.
     */
    @Override
    public String toString(){
        if (vector.toString().equals("{}")) return "{}";

        String outputString = vector.toString();
        outputString = outputString.replaceAll("=","\":").replaceAll(", ",",\"").replaceAll("\\{","{\"");

        return outputString;


    }

    /**
     * Set a clock given it's string representation.
     *
     * @param clock
     */
    @Override
    public void setClockFromString(String clock){
        Map<Integer, Integer> tempMap = new HashMap<>();

        int i = clock.indexOf(":");
        int s = clock.indexOf("{")+2;

        int key,value;


        while (i != -1) {
            int e = clock.indexOf(",", i);

            if(e == -1) {

                e = clock.indexOf("}",i);
            }

            try{

                key = valueOf(clock.substring(s,i - 1));
                value = valueOf(clock.substring(i + 1, e));
                tempMap.put(key,value);

            } catch (NumberFormatException error) {
                return;
            }

            s = clock.indexOf("\"",i + 1) + 1;
            i = clock.indexOf(":", i + 1);
        }


        vector = new HashMap<>();
        vector.putAll(tempMap);
    }
}
