package com.example.vspemuellechat.ch.ethz.inf.vs.a3.clock;

/**
 * Created by peter on 29.10.17.
 */

public class LamportClock implements Clock {

    private int time;

    void setTime(int time) {
        this.time = time;
    }

    int getTime() {
        return time;
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
    public void update(Clock other) {
        if(((LamportClock) other).getTime()>time) {
            time = ((LamportClock) other).getTime();
        }
    }

    /**
     * Change the current clock with a new one, overwriting the
     * old values.
     * @param other
     */
    @Override
    public void setClock(Clock other) {
        time = ((LamportClock)other).getTime();
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
    public void tick(Integer pid) {
        time++;
    }

    /**
     * Check whether a clock has happened before another one.
     *
     * @param other
     * @return True if a clock has happened before, false otherwise.
     */
    @Override
    public boolean happenedBefore(Clock other) {

        return (time<((LamportClock) other).getTime());

    }

    /**
     * toString
     *
     * @return String representation of the clock.
     */
    @Override
    public String toString() {

        return Integer.toString(time);
    }

    /**
     * Set a clock given it's string representation.
     *
     * @param clock
     */
    @Override
    public void setClockFromString(String clock) {
        try {
            time = Integer.valueOf(clock);
        } catch (NumberFormatException error) {}


    }
}
