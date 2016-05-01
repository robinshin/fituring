package camillepact.applicationtest;

import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;
import java.io.*;
import java.net.*;

/**
 * Created by Camille on 01/05/2016.
 */
public class Client {
    public static void execute() throws IOException {

        String[]args = new String[2];
        args[0] = "137.194.23.40";
        args[1] = "5869";
        /*if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }*/

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            while ((fromServer = in.readLine()) != null) {
                Log.d("Server",fromServer);
                if (fromServer.equals("Bye."))
                    break;

                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    Log.d("Client", fromUser);
                    out.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            Log.e("Erreur", "Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            Log.e("Erreur", "Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}