package assignment2;

import java.net.DatagramPacket;

public class UdpMain
{
    private static final int PORT_NUMBER = 5000;
    private static final String QUIT_COMMAND = "quit";
    private static final String HUMOROUS_MESSAGE = "a very humorous message, please laugh";

    public static void main(String[] args)
    {
        UdpServer udpServer = new UdpServer(PORT_NUMBER);

        while(true)
        {
            try
            {
                DatagramPacket messagePacket = udpServer.listenForMessage();

                String message = new String(messagePacket.getData(), 0, messagePacket.getLength());

                System.out.println("Message received: " + message);

                if(message.equalsIgnoreCase(QUIT_COMMAND))
                {
                    udpServer.closeServer();
                    break;
                }
                else
                {
                    String modifiedInput = message + " - " + HUMOROUS_MESSAGE;
                    udpServer.sendResponse(modifiedInput, messagePacket.getAddress(), messagePacket.getPort());
                }
            }
            catch(Exception e)
            {
                System.out.println("An error occurred, disconnecting from client");
                System.out.println("Error message: " + e.getMessage());
                udpServer.closeServer();
            }
        }
    }
}
