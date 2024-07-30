package messenger.client;

import messenger.server.WindowServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ClientGUI extends JFrame {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 400;
    private WindowServer server;
    private JEditorPane chat,userName,password;
    private JButton send,connect;
    private JPanel panelBottom;
    private JPanel panelTop;
    private String activeUser;
    private JTextArea log;
    private boolean connectedToSrv;

    public ClientGUI(WindowServer server) throws HeadlessException {
        this.server = server;
        setTitle("Messenger");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setLocation(server.getX() + 500, server.getY());
        setVisible(true);
        initComponents();
        setActions();
    }
    public void initComponents(){
        chat = new JTextPane();
        send = new JButton("Send");
        send.setEnabled(false);
        log = new JTextArea();
        add(log,BorderLayout.CENTER);
        panelBottom = new JPanel(new GridLayout(1, 2));
        panelBottom.add(chat);
        panelBottom.add(send);
        add(panelBottom, BorderLayout.SOUTH);
        userName = new JTextPane();
        password = new JTextPane();
        connect = new JButton("Connect");
        panelTop = new JPanel(new GridLayout(1,3));
        panelTop.add(userName);
        panelTop.add(password);
        panelTop.add(connect);
        add(panelTop,BorderLayout.NORTH);
    }
    public void setActions(){
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.append(activeUser+":"+chat.getText());
                server.addMessage(activeUser+":"+chat.getText());
                chat.setText("");
                server.writeToFile(activeUser+":"+chat.getText());
            }
        });
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateUser(userName.getText())&&validateConnection()){
                    connectedToSrv = true;
                    panelTop.setVisible(false);
                    send.setEnabled(true);
                    activeUser = userName.getText();
                    setTitle(activeUser);
                    server.addMessage(activeUser + ": is joined");
                    log.setText("");
                }else setLog("Server is disconnected or userName is not valid \n");
            }
        });
    }
    private boolean validateUser(String user){
        return  (Objects.equals(server.getUser(userName.getText()), user));
    }
    private boolean validateConnection(){
        return server.srvIsEnabled();
    }
    public void setLog(String message){
        log.append(message);
    }
    public void disconnect(){
        server.disconnectFromServer(this);
        connectedToSrv = false;
        panelTop.setVisible(true);
        send.setEnabled(false);
        activeUser = "Messenger";
        setTitle(activeUser);

    }
}
