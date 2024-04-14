package assignment1;

public class TcpMain
{
    private static final int PORT_NUMBER = 5000;

    public static void main(String[] args)
    {
        TcpServer tcpServer = new TcpServer();

        try
        {
            tcpServer.startServer(PORT_NUMBER);
            tcpServer.waitForClient();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        while(true)
        {
            try
            {
                String input = tcpServer.listenToClient();

                if(input.equals("quit"))
                {
                    tcpServer.disconnectFromClient();
                    tcpServer.shutdownServer();
                    break;
                }
                else
                {
                    String modifiedInput = addOneToString(input);
                    tcpServer.sendMessage(modifiedInput);
                }
            }
            catch(Exception e)
            {
                System.out.println("An error occurred, disconnecting from client");
                System.out.println("Error message: " + e.getMessage());
                tcpServer.disconnectFromClient();
                tcpServer.waitForClient();
            }
        }
    }

    /**
     * Replace every character in a given String with the next character in the ASCII sequence
     *
     * @param input the original message to be encoded
     * @return the encoded message
     */
    private static String addOneToString(String input)
    {
        StringBuilder output = new StringBuilder();

        for(char c : input.toCharArray())
        {
            output.append((char) (c + 1));
        }

        return output.toString();
    }
}