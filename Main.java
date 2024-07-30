package messenger;

import messenger.client.ClientGUI;
import messenger.server.WindowServer;

public class Main {
    public static void main(String[] args) {
        WindowServer server = new WindowServer();
        new ClientGUI(server);
        new ClientGUI(server);
    }
}
