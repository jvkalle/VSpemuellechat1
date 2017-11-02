package com.example.vspemuellechat.ch.ethz.inf.vs.a3.clock;

import java.util.Comparator;


public class VectorClockComparator implements Comparator<VectorClock> {

    @Override
    public int compare(VectorClock lhs, VectorClock rhs) {
        if (lhs.happenedBefore(rhs)) {
            return -1;
        } else if(rhs.happenedBefore(lhs)) {
            return 1;
        } else return 0;
    }
}
