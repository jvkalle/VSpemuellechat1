package com.example.vspemuellechat.ch.ethz.inf.vs.a3.message;

import java.util.Comparator;
//import ch.ethz.inf.vs.a3.solution.message.Message;

/**
 * Message comparator class. Use with PriorityQueue.
 */
public class MessageComparator implements Comparator<Message> {

    @Override
    public int compare(Message lhs, Message rhs) {
        if(lhs.timestamp < rhs.timestamp) {
            return -1;
        } else if (rhs.timestamp < lhs.timestamp) {
            return 1;
        } else {
            return 0;
        }

    }

}
