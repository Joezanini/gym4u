package com.example.gym4u;

import java.io.Serializable;
import java.util.HashMap;

public class Schedule implements Serializable {
    private String name;
    private String start;
    private String end;

    public Schedule() {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    //firebase time slot
    public HashMap<String,String> toFirebaseObject() {
        HashMap<String,String> time =  new HashMap<String,String>();
        time.put("name", name);
        time.put("start", start);
        time.put("end", end);

        return time;
    }

}
