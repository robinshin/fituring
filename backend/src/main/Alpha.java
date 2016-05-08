package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

import classification.Classification;
import classification.Movement;
import kinect.Kinect;
import syntheseAudio.LectureAudio;

public class Alpha {

	public static void main(String[] args) 
	{
	    Scanner sc = new Scanner(System.in);
	    String strIn;

		System.out.println("Creation des instances");
		Kinect kinect = new Kinect();
		Classification cl = new Classification();
		LectureAudio audio = new LectureAudio();
		//DetectionRythme dr = new DetectionRythme();
		
		System.out.println("Initialisation des modules");
		kinect.initKinectModule();
		audio.initLectureAudioModule(new Object(), 100);
		cl.initClassificationModule(kinect);
		//dr.initRythmeModule(kinect, audio);
		
		cl.addGesture("dab.mvt");
		cl.addGesture("Envol.mvt");
		cl.addGesture("I.mvt");
		cl.addGesture("O.mvt");
		cl.addGesture("test1main.mvt");
		cl.addGesture("test2main.mvt");
		cl.addGesture("batterie.mvt");
		cl.addGesture("saxophone.mvt");
		cl.addGesture("ventre.mvt");
		cl.addGesture("discoBras.mvt");
		cl.addGesture("discoMain.mvt");
		cl.addGesture("gangnamStyle.mvt");
		while(true)
		{
			System.out.print("Appuyer sur une Entrer pour commencer (q pour quitter)");
			strIn = sc.nextLine();
		    if(strIn.length() >= 1 && strIn.charAt(0) == 'q')
		    	break;
			System.out.println("Ajout des listeners");

		    cl.startListening();
		    //dr.startListening();
		    sc.nextLine();
		    cl.stopListening();
		    //dr.stopListening();
		}
		//System.out.println(cl.nDollarRecognizer());
	    System.out.println("Stopping Kinect");
		kinect.stop();	
	    System.out.println("Closing scanner stream");
		sc.close();
		System.out.println("FPS: "+kinect.getFPS());
	}

}
