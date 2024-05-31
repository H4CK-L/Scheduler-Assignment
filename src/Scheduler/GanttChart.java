package Scheduler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class GanttChart {
    private String name;
    private int startTime;
    private int endTime;
    private Color color;

    public GanttChart(String name, Color color){
        this.name = name;
        this.color = color;
    }
    public GanttChart(String name, int startTime, int endTime, Color color) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public Color getColor() {
        return color;
    }

    public void setStartEnd( int startTime, int endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

