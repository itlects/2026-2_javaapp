package week03.example07;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CancellationException;

public class SwingWorkerCancelExample extends JFrame {
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final JButton btnStart = new JButton("Start Download");
    private final JButton btnCancel = new JButton("Cancel Download");
    private final JLabel lblStatus = new JLabel("Waiting...", SwingConstants.CENTER);
    private DownloadTask downloadTask;

    public SwingWorkerCancelExample() {
        setTitle("SwingWorker Download + Cancel");
        setSize(450, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));

        btnCancel.setEnabled(false);
        progressBar.setPreferredSize(new Dimension(300, 25));
        progressBar.setStringPainted(true);
        lblStatus.setPreferredSize(new Dimension(320, 20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnStart);
        buttonPanel.add(btnCancel);

        add(buttonPanel);
        add(progressBar);
        add(lblStatus);

        btnStart.addActionListener(e -> {
            btnStart.setEnabled(false);
            btnCancel.setEnabled(true);
            progressBar.setValue(0);
            lblStatus.setText("Starting download...");
            downloadTask = new DownloadTask();
            downloadTask.execute();
        });

        btnCancel.addActionListener(e -> {
            if (downloadTask != null && !downloadTask.isDone()) {
                downloadTask.cancel(true);
            }
        });
    }

    private class DownloadTask extends SwingWorker<Void, Integer> {
        @Override
        protected Void doInBackground() throws Exception {
            for (int i = 1; i <= 100; i++) {
                if (isCancelled()) break;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    if (isCancelled()) break;
                    throw e;
                }
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
            btnStart.setEnabled(true);
            btnCancel.setEnabled(false);
            try {
                get();
                lblStatus.setText("Download completed!");
            } catch (CancellationException e) {
                lblStatus.setText("Download canceled.");
            } catch (Exception e) {
                lblStatus.setText("An error occurred.");
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
            new SwingWorkerCancelExample().setVisible(true)
        );
    }
}
