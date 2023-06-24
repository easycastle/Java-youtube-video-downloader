import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main extends JFrame {
    private JTextField linkTextField;
    private JList<String> linkList;
    private JTextField pathTextField;
    private JProgressBar progressBar;

    public Main() {

        // GUI
        super("Youtube Downloader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 480);
        setResizable(false);

        JPanel linkPanel = new JPanel();
        linkPanel.setBorder(BorderFactory.createTitledBorder("링크"));
        linkPanel.setLayout(new BorderLayout());
        linkTextField = new JTextField();
        linkPanel.add(linkTextField, BorderLayout.CENTER);
        JButton addVideoButton = new JButton("링크 추가");
        linkPanel.add(addVideoButton, BorderLayout.EAST);

        JPanel linkOperationPanel = new JPanel();
        linkOperationPanel.setLayout(new BorderLayout());
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        JScrollPane linkScrollPane = new JScrollPane();
        linkList = new JList<>(new DefaultListModel<>());
        linkScrollPane.setViewportView(linkList);
        listPanel.add(linkScrollPane, BorderLayout.CENTER);
        linkOperationPanel.add(listPanel, BorderLayout.CENTER);

        JPanel deleteButtonPanel = new JPanel();
        deleteButtonPanel.setLayout(new GridLayout(2, 1));
        JButton deleteSelectionButton = new JButton("선택 삭제");
        JButton deleteAllButton = new JButton("전체 삭제");
        deleteButtonPanel.add(deleteSelectionButton);
        deleteButtonPanel.add(deleteAllButton);
        linkOperationPanel.add(deleteButtonPanel, BorderLayout.EAST);

        JPanel pathPanel = new JPanel();
        pathPanel.setBorder(BorderFactory.createTitledBorder("저장 경로"));
        pathPanel.setLayout(new BorderLayout());
        pathTextField = new JTextField();
        pathPanel.add(pathTextField, BorderLayout.CENTER);
        JButton pathButton = new JButton("찾아보기");
        pathPanel.add(pathButton, BorderLayout.EAST);

        JPanel progressPanel = new JPanel();
        progressPanel.setBorder(BorderFactory.createTitledBorder("진행 상황"));
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        progressBar.setPreferredSize(new Dimension(600, 20));
        progressPanel.add(progressBar);

        JPanel runPanel = new JPanel();
        JButton extractionButton = new JButton("추출");
        JButton closeButton = new JButton("닫기");
        runPanel.add(extractionButton);
        runPanel.add(closeButton);

        JPanel pathProgressPanel = new JPanel();
        pathProgressPanel.setLayout(new BorderLayout());
        pathProgressPanel.add(pathPanel, BorderLayout.NORTH);
        pathProgressPanel.add(progressPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(pathProgressPanel, BorderLayout.NORTH);
        bottomPanel.add(runPanel, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(linkPanel, BorderLayout.NORTH);
        add(linkOperationPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);


        // Action Listener
        addVideoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addVideo();
            }
        });

        deleteSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedLink();
            }
        });

        deleteAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAllLink();
            }
        });

        pathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browsePath();
            }
        });

        extractionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extract();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    // Method
    private boolean isLinkAlreadyAdded(DefaultListModel<String> model, String link) {
        for (int i = 0; i < model.getSize(); i++) {
            String element = model.getElementAt(i);
            if (element.equals(link)) {
                return true;
            }
        }
        return false;
    }

    private void addVideo() {
        String link = linkTextField.getText();

        if (link.equals("")) {
            JOptionPane.showMessageDialog(this, "링크를 작성하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
        }
        else if (link.contains("https://www.youtube.com/") && !link.equals("https://www.youtube.com/")) {
            DefaultListModel<String> model = (DefaultListModel<String>) linkList.getModel();

            if (model.getSize() > 0 && isLinkAlreadyAdded(model, link)) {
                JOptionPane.showMessageDialog(this, "이미 추가된 링크입니다.", "경고", JOptionPane.WARNING_MESSAGE);
            }
            else {
                model.addElement(link);
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "유효하지 않은 링크입니다.", "경고", JOptionPane.WARNING_MESSAGE);
        }

        linkTextField.setText("");
    }

    private void deleteSelectedLink() {
        int[] selectedIndices = linkList.getSelectedIndices();

        if (selectedIndices.length == 0) {
            JOptionPane.showMessageDialog(this, "선택된 링크가 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
        }
        else {
            DefaultListModel<String> model = (DefaultListModel<String>) linkList.getModel();
            for (int i = selectedIndices.length - 1; i >= 0; i--) {
                model.remove(selectedIndices[i]);
            }
        }
    }

    private void deleteAllLink() {
        DefaultListModel<String> model = (DefaultListModel<String>) linkList.getModel();
        int size = model.getSize();

        if (size == 0) {
            JOptionPane.showMessageDialog(this, "링크가 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
        }
        else {
            int response = JOptionPane.showConfirmDialog(this, "전체 링크를 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                model.clear();
            }
        }
    }

    private void browsePath() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            pathTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void extract() {
        DefaultListModel<String> model = (DefaultListModel<String>) linkList.getModel();
        int size = model.getSize();
        String path = pathTextField.getText();

        progressBar.setValue(0);

        if (size == 0) {
            JOptionPane.showMessageDialog(this, "링크가 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);
        }
        else if (path.equals("")) {
            JOptionPane.showMessageDialog(this, "저장 경로를 지정하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
        }
        else {
            SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
                @Override
                protected Void doInBackground() throws Exception {
                    for (int i = 0; i < size; i++) {
                        String link = model.getElementAt(i);

                        try {
                            ProcessBuilder builder = new ProcessBuilder("yt-dlp", "-P", path, link);
                            Process process = builder.start();
                            process.waitFor();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        int progress = (i + 1) * 100 / size;
                        publish(progress);
                    }

                    return null;
                }

                @Override
                protected void process(java.util.List<Integer> chunks) {
                    int progress = chunks.get(chunks.size() - 1);
                    progressBar.setValue(progress);
                }

                @Override
                protected void done() {
                    JOptionPane.showMessageDialog(Main.this, "추출이 완료되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                }
            };

            worker.execute();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}