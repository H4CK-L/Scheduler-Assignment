package Scheduler;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class GanttChartPanel extends JPanel {
    private List<GanttChart> tasks ;
    private GanttChart task;
    public GanttChartPanel(List<GanttChart> tasks) {
        this.tasks = tasks;
    }

    public void ganttAdd(GanttChart task){
        this.task=task;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int taskHeight = 30;
        int taskY = 50;
        int margin = 50;
        int xStart = margin;

        int maxTime = tasks.stream().mapToInt(GanttChart::getEndTime).max().orElse(0);

        for (int i = 0; i <= maxTime; i++) {
            g.drawString(Integer.toString(i), xStart + i * 50, taskY - 10);
        }

        for (GanttChart task : tasks) {
            int taskX = xStart + task.getStartTime() * 50;
            int taskWidth = (task.getEndTime() - task.getStartTime()) * 50;

            g.setColor(task.getColor());
            g.fillRect(taskX, taskY, taskWidth, taskHeight);
            g.setColor(Color.BLACK);
            g.drawRect(taskX, taskY, taskWidth, taskHeight);
            g.drawString(task.getName(), taskX + 5, taskY + 20);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int width = 800;
        int height = 150;
        return new Dimension(width, height);
    }
}