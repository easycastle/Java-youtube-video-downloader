import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JTextField linkTextField;
    private JList<String> linkList;
    private JTextField pathTextField;
    private JProgressBar progressBar;

    public Main() {

        // GUI
        super("Youtube Downloader");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
        linkList = new JList<>();
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
        progressPanel.add(progressBar);

        JPanel runPanel = new JPanel();
        JButton extractionButton = new JButton("추출");
        JButton closeButton = new JButton("닫기");
        runPanel.add(extractionButton);
        runPanel.add(closeButton);

        setLayout(new BorderLayout());
        add(linkPanel, BorderLayout.NORTH);
        add(linkOperationPanel, BorderLayout.CENTER);
        add(pathPanel, BorderLayout.SOUTH);
        add(progressPanel, BorderLayout.SOUTH);
        add(runPanel, BorderLayout.SOUTH);


        // Action Listener
        addVideoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        deleteSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        deleteAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        pathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        extractionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
    public static void main(String[] args) {
        new Main();
    }
}