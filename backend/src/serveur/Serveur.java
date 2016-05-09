package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import interfaces.ServeurInterface;
import interfaces.UpdateParamInterface;

public class Serveur implements ServeurInterface, Runnable {

	private int info;
	
	public void run(){
		String args = "5873";
		int portNumber = Integer.parseInt(args);
		
		try ( 
			    ServerSocket serverSocket = new ServerSocket(portNumber);
			    Socket clientSocket = serverSocket.accept();
			    PrintWriter out =
			        new PrintWriter(clientSocket.getOutputStream(), true);
			    BufferedReader in = new BufferedReader(
			        new InputStreamReader(clientSocket.getInputStream()));
			){
			String inputLine, outputLine;
		    outputLine = "Hello";
		    out.println(outputLine);
		    System.out.println(outputLine);
		    while (true) {
		    	inputLine = in.readLine();
		    	if (inputLine != null){		    		
		    		//System.out.println(inputLine);
		    		info = Integer.parseInt(inputLine);
		    		System.out.println(info);
		    	}
		        
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public int getVolume() {
		return (int) (info/100);
	}

	@Override
	public void initServeurModule(UpdateParamInterface engine) {
		engine.setStyle(getStyle());
		engine.setVolume(getVolume());
	}

	@Override
	public int getStyle() {
		return (info%100)/10;
	}

	@Override
	public boolean isConnected() {
	    return false;
	
	}

}
