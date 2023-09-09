package a;

import javax.swing.*;

class Slider extends JSlider {
    public Slider() {
        super(0,100);
    }
    synchronized public void Increase(int increment){
        setValue(getValue() + increment);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Thread Priority a.Slider Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        Slider slider = new Slider();
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(2);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        Thread thread1 = new Thread(new SliderUpdateTask(slider, 10), "Thread 1 to 10");
        Thread thread2 = new Thread(new SliderUpdateTask(slider, 90), "Thread 2 to 90");

        JSpinner spinner1 = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
        spinner1.addChangeListener(e ->thread1.setPriority((int)(spinner1.getValue())));

        JSpinner spinner2 = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
        spinner2.addChangeListener(e -> thread2.setPriority((int)(spinner2.getValue())));

        JPanel panel = new JPanel();
        panel.add(new JLabel("Thread 1 to 10 Priority: "));
        panel.add(spinner1);
        panel.add(new JLabel("Thread 2 tp 90 Priority: "));
        panel.add(spinner2);
        panel.add(slider);

        JButton btn = new JButton("Start");

        btn.addActionListener(e -> {
            thread1.start();
            thread2.start();
            btn.setEnabled(false);
        });

        panel.add(btn);

        frame.add(panel);
        frame.setVisible(true);
    }

    static class SliderUpdateTask implements Runnable {
        private final Slider slider;
        private final int targetValue;

        public SliderUpdateTask(Slider slider, int targetValue) {
            this.slider = slider;
            this.targetValue = targetValue;
        }

        @Override
        public void run() {
            while (slider.getValue() != targetValue) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }

                if (slider.getValue() < targetValue) {
                    slider.Increase(1);
                } else {
                    slider.Increase( -1);
                }
            }
        }
    }
}
