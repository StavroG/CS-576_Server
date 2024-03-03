package server;

public class Main
{
    private static final int PORT_NUMBER = 5000;

    public static void main(String[] args)
    {
        ServerConnection serverConnection = new ServerConnection();

        try
        {
            serverConnection.startServer(PORT_NUMBER);
            serverConnection.waitForClient();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        while(true)
        {
            try
            {
                String input = serverConnection.listenToClient();

                if(input.equals("quit"))
                {
                    serverConnection.disconnectFromClient();
                    serverConnection.shutdownServer();
                    break;
                }
                else
                {
                    String modifiedInput = addOneToString(input);
                    serverConnection.sendMessage(modifiedInput);
                }
            }
            catch(Exception e)
            {
                System.out.println("An error occurred, disconnecting from client");
                System.out.println("Error message: " + e.getMessage());
                serverConnection.disconnectFromClient();
                serverConnection.waitForClient();
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