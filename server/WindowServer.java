package messenger.server;

import messenger.client.ClientGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WindowServer extends JFrame {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 400;
    private ClientGUI client;
    private JButton btnStart, btnStop;
    private JPanel panelBottom;
    private static final String LOG_PATH = "messenger/log/log.txt";
    JTextArea log;
    boolean work;
    private String[] users;

    public WindowServer() throws HeadlessException {
        setTitle("Chat server");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        initComponents();
        setActions();
    }

    private void initComponents() {
        btnStart = new JButton("Start server");
        btnStop = new JButton("Stop server");
        panelBottom = new JPanel(new GridLayout(1, 2));
        panelBottom.add(btnStart);
        panelBottom.add(btnStop);
        add(panelBottom, BorderLayout.SOUTH);
        log = new JTextArea();
        add(log, BorderLayout.CENTER);
        btnStart.setBackground(Color.GREEN);
        btnStop.setBackground(Color.RED);
        btnStop.setEnabled(false);
        work = false;
        users = createUserDatabase();
    }
    public void writeToFile(String message){
        String path = "log.txt";
        File file = new File(path);
        try (FileWriter writer = new FileWriter(file,true)) {
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setActions() {
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
                log.append("Server started \n");
                work = true;
            }
        });
        setVisible(true);
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnStop.setEnabled(false);
                btnStart.setEnabled(true);
                log.append("\n" + "Server stopped");
                work = false;


            }
        });
    }

    public void addMessage(String message) {
        StringBuilder builder = new StringBuilder();
        builder.append(" \n").append(message);
        log.append(String.valueOf(builder));
        writeToFile(message);
    }

    public boolean srvIsEnabled() {
        return work;
    }

    public String[] createUserDatabase() {
        users = new String[]{"john", "sarah", "michael"};
        return users;
    }

    public String getUser(String user) {
        for (String s : users) {
            if (Objects.equals(s, user)) return user;
        }
        return null;
    }

    public void disconnectFromServer(ClientGUI client) {
        client.disconnect();
    }
//    public String upHistory{
//        try {
//            FileReader reader = new FileReader("log.txt");
//
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
}
