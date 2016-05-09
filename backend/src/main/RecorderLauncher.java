package main;

import java.util.Scanner;
import classification.Classification;
import kinect.Kinect;

public class RecorderLauncher {

	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		String strIn;

		System.out.println("Creation des instances");
		Kinect kinect = new Kinect();
		Classification cl = new Classification();

		System.out.println("Initialisation des modules");
		kinect.initKinectModule();
		cl.initClassificationModule(kinect);
		cl.setRecorder();

		System.out.print("Appuyer sur une Entrer pour commencer (q pour quitter)");
		strIn = sc.nextLine();
		while(true)
		{
			if(strIn.length() >= 1 && strIn.charAt(0) == 'q')
				break;
			
			System.out.println("Ajout des listeners");
			cl.startListening();
			sc.nextLine();
			cl.stopListening();
			
			System.out.print("Appuyer sur une Entrer pour recommencer, s pour sauvegarder le mouvement, q pour quitter");
			strIn = sc.nextLine();
			if (strIn.length() >= 1 && strIn.charAt(0) == 's') {
				Scanner scName = new Scanner(System.in);
				System.out.println("Entrer le nom du mouvement : ");
				String gestureName = scName.nextLine();
				while (gestureName == null || gestureName.equals("")) {
					System.out.println("Champ vide ! Entrer le nom du mouvement : ");
					gestureName = sc.nextLine();
				}
				System.out.println("0=left hand ; 1=right hand ; 2=both hands ; 3=left hand+elbow ; 4=right hand+elbow ; 5=both hands+elbows");
				strIn=sc.nextLine();
				int whichJoint = -1;
				if (strIn.length() >= 1 && strIn.charAt(0) == '0')
					whichJoint = Classification.RECORD_LEFT_HAND;
				if (strIn.length() >= 1 && strIn.charAt(0) == '1')
					whichJoint = Classification.RECORD_RIGHT_HAND;
				if (strIn.length() >= 1 && strIn.charAt(0) == '2')
					whichJoint = Classification.RECORD_BOTH_HANDS;
				if (strIn.length() >= 1 && strIn.charAt(0) == '3')
					whichJoint = Classification.RECORD_LEFT_HAND_ELBOW;
				if (strIn.length() >= 1 && strIn.charAt(0) == '4')
					whichJoint = Classification.RECORD_RIGHT_HAND_ELBOW;
				if (strIn.length() >= 1 && strIn.charAt(0) == '5')
					whichJoint = Classification.RECORD_BOTH_HANDS_ELBOWS;
				
				cl.saveMovement(gestureName, whichJoint);
				System.out.println("Gesture saved !");
				break;
			}		
		}
		System.out.println("Stopping Kinect");
		kinect.stop();	
		System.out.println("Closing scanner stream");
		sc.close();
		System.out.println("FPS: "+kinect.getFPS());
	}
}
