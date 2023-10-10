package b;

import javax.swing.*;

class myThread extends Thread {

    static volatile int semaphore = 0;
    private final int boundary;
    private final JSlider slider;
    private final JLabel label;
    public myThread(int boundary, JSlider slider, JLabel label){
        this.boundary = boundary;
        this.slider = slider;
        this.label = label;

    }

    @Override
    public void run(){
        while (semaphore != 0) Thread.onSpinWait();
        semaphore = 1;
        label.setText("Зайнято потоком! " + boundary);
        while(!interrupted()) {
            int val = slider.getValue();
            int inc = val < boundary ? 1 : -1;
            slider.setValue(val + inc);
        }
        semaphore = 0;
        label.setText("Вільно");
    }
}
public class Main {
    static private Thread thread1;
    static private Thread thread2;
    public static void main(String[] args) {
        JFrame frame = new JFrame("Thread Priority a.Slider Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();

        JSlider slider = new JSlider();
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(2);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        panel.add(slider);

        JLabel label = new JLabel("Вільно");
        panel.add(label);

        JPanel thread1Panel = new JPanel();

        JButton thread1StartBtn = new JButton("ПУСК1");
        JButton thread1StopBtn = new JButton("СТОП1");
        thread1StopBtn.setEnabled(false);


        thread1StartBtn.addActionListener(e -> {
            thread1 = new myThread(90, slider, label);
            thread1.setPriority(Thread.MIN_PRIORITY);
            thread1StopBtn.setEnabled(true);
            thread1StartBtn.setEnabled(false);
            thread1.start();
        });
        thread1StopBtn.addActionListener(e -> {
            thread1.interrupt();
            thread1StopBtn.setEnabled(false);
            thread1StartBtn.setEnabled(true);
        });

        thread1Panel.add(thread1StartBtn);
        thread1Panel.add(thread1StopBtn);
        panel.add(thread1Panel);

        JPanel thread2Panel = new JPanel();

        JButton thread2StartBtn = new JButton("ПУСК2");
        JButton thread2StopBtn = new JButton("СТОП2");
        thread2StopBtn.setEnabled(false);


        thread2StartBtn.addActionListener(e -> {
            thread2 = new myThread(10, slider, label);
            thread2.setPriority(Thread.MAX_PRIORITY);
            thread2StopBtn.setEnabled(true);
            thread2StartBtn.setEnabled(false);
            thread2.start();
        });
        thread2StopBtn.addActionListener(e -> {
            thread2.interrupt();
            thread2StopBtn.setEnabled(false);
            thread2StartBtn.setEnabled(true);
        });

        thread2Panel.add(thread2StartBtn);
        thread2Panel.add(thread2StopBtn);
        panel.add(thread2Panel);

        frame.add(panel);
        frame.setVisible(true);
    }
}
