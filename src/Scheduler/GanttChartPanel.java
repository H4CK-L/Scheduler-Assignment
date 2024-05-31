package Scheduler;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class GanttChartPanel extends JPanel {
    private List<GanttChart> tasks ;
    public GanttChartPanel(List<GanttChart> tasks) {
        this.tasks = tasks;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cnt = 0;
        int taskHeight = 30;
        int taskY = 50;
        int margin = 50;
        int xStart = margin;
        int j =0;
        int k = 0;
        int taskX;
        int maxTime = tasks.stream().mapToInt(GanttChart::getEndTime).max().orElse(0);

        // Draw time labels
        for (int i = 0; i <= maxTime; i++) {
            if(i > 70){
                g.drawString(Integer.toString(i), xStart + (k+1) * 50, taskY+110);
                k++;
            }
            else if(i > 35){
                g.drawString(Integer.toString(i), xStart + (j+1) * 50, taskY+50);
                j++;
            }
            else{
                g.drawString(Integer.toString(i), xStart + i * 50, taskY - 10);
            }

        }

        // Draw tasks
        for (GanttChart task : tasks) {
            int taskWidth = (task.getEndTime() - task.getStartTime()) * 50;
            if(task.getEndTime() > 70 && task.getStartTime() < 70 ){
                int tmp = task.getEndTime() - 70;
                taskWidth = taskWidth - tmp *50;
                taskX = margin + task.getStartTime() * 50;
                g.setColor(task.getColor());
                g.fillRect( taskX, taskY+60*cnt, taskWidth, taskHeight);
                g.setColor(Color.BLACK);
                g.drawRect( taskX, taskY+60*cnt, taskWidth, taskHeight);
                g.drawString(task.getName(), taskX + 5, taskY + 80*cnt);
                /*taskX= xStart;
                g.setColor(task.getColor());
                g.fillRect(taskX, taskY+60*(cnt+1), tmp*50, taskHeight);
                g.setColor(Color.BLACK);
                g.drawRect(taskX, taskY+60*(cnt+1), tmp*50, taskHeight);
                g.drawString(task.getName(), taskX + 5, taskY + 80*(cnt+1));
                cnt++;*/
            }
            else if(task.getStartTime() >= 70 && task.getEndTime() <= 105 ){
                taskX = xStart + (task.getStartTime()-71) * 50;
                g.setColor(task.getColor());
                g.fillRect(taskX, taskY+60*(cnt+1), taskWidth, taskHeight);
                g.setColor(Color.BLACK);
                g.drawRect(taskX, taskY+60*(cnt+1), taskWidth, taskHeight);
                g.drawString(task.getName(), taskX + 5, taskY + 80*cnt);
                cnt++;
            }
            else if(task.getEndTime() > 35 && task.getStartTime() < 35){
                int tmp = task.getEndTime() - 35;
                taskWidth -= tmp *50;
                taskX = xStart + task.getStartTime() * 50;
                g.setColor(task.getColor());
                g.fillRect(taskX, taskY+60*cnt, taskWidth, taskHeight);
                g.setColor(Color.BLACK);
                g.drawRect(taskX, taskY+60*cnt, taskWidth, taskHeight);
                g.drawString(task.getName(), taskX + 5, taskY + 20);

                taskX= xStart;
                g.setColor(task.getColor());
                g.fillRect(taskX, taskY+60*(cnt+1), tmp*50, taskHeight);
                g.setColor(Color.BLACK);
                g.drawRect(taskX, taskY+60*(cnt+1), tmp*50, taskHeight);
                g.drawString(task.getName(), taskX + 5, taskY + 80*(cnt+1));
                cnt++;
            }
            else if(task.getStartTime() >= 35 && task.getEndTime() <= 70){
                taskX = xStart + (task.getStartTime()-35) * 50;
                g.setColor(task.getColor());
                g.fillRect(taskX, taskY+60*cnt, taskWidth, taskHeight);
                g.setColor(Color.BLACK);
                g.drawRect(taskX, taskY+60*cnt, taskWidth, taskHeight);
                g.drawString(task.getName(), taskX + 5, taskY + 80*cnt);
            }
            else{
                taskX = xStart + task.getStartTime() * 50;
                g.setColor(task.getColor());
                g.fillRect(taskX, taskY+110*cnt, taskWidth, taskHeight);
                g.setColor(Color.BLACK);
                g.drawRect(taskX, taskY+110*cnt, taskWidth, taskHeight);
                g.drawString(task.getName(), taskX + 5, taskY + 20);
            }

        }
    }

    @Override
    public Dimension getPreferredSize() {
        int width = 800;
        int height = 150;
        return new Dimension(width, height);
    }
}