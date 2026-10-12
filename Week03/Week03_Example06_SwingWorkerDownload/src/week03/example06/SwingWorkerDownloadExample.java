package week03.example06;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SwingWorkerDownloadExample extends JFrame {
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final JButton btnStart = new JButton("Start Download");
    private final JLabel lblStatus = new JLabel("Waiting...", SwingConstants.CENTER);

    public SwingWorkerDownloadExample() {
        setTitle("SwingWorker Download Example");
        setSize(430, 190);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        progressBar.setPreferredSize(new Dimension(300, 25));
        progressBar.setStringPainted(true);
        lblStatus.setPreferredSize(new Dimension(300, 20));

        add(btnStart);
        add(progressBar);
        add(lblStatus);

        btnStart.addActionListener(e -> {
            btnStart.setEnabled(false);
            progressBar.setValue(0);
            lblStatus.setText("Starting download...");
            new DownloadTask().execute();
        });
    }

    private class DownloadTask extends SwingWorker<Void, Integer> {
        @Override
        protected Void doInBackground() throws Exception {
            for (int i = 1; i <= 100; i++) {
                Thread.sleep(50);
                publish(i);
            }
            return null;
        }

        @Override
        protected void process(List<Integer> chunks) {
            int latest = chunks.get(chunks.size() - 1);
            progressBar.setValue(latest);
            lblStatus.setText("Downloading... (" + latest + "%)");
        }

        @Override
        protected void done() {
            lblStatus.setText("Download completed!");
            btnStart.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
            new SwingWorkerDownloadExample().setVisible(true)
        );
    }
}
