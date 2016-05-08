package camillepact.applicationtest;

import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;
import java.io.*;
import java.net.*;

/**
 * Created by Camille on 01/05/2016.
 */
public class Client implements Runnable{
    @Override
    public void run() {
        String[]args = new String[2];
        args[0] = "137.194.35.197";
        args[1] = "5873";

        String info = NewSongActivity.getInfo();
        String info2;
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
            String fromServer = in.readLine();
            if(fromServer.equals("Hello")) {
                out.println(info);
            }

            while (true) {
                //Log.d("Server", fromServer);
                info2 = NewSongActivity.getInfo();
                if (!info2.equals(info)){
                    info = info2;
                    out.println(info);
                    Log.d("Server",info);
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