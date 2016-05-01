package kinect;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;

public class Kinect extends J4KSDK 
{
	static boolean record = false;
	Skeleton currentSkeleton = null;
	static PrintWriter pw = null;
	static String filename = "donneesStan130BPM"
			+ ".csv";
	int counter=0;
	long time=0;
	//KinectRecorder kinectRecorder = new KinectRecorder(new XEDConvertApp());
	
	@Override
	public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] positions, float[] orientations, byte[] joint_status) {
		System.out.println("New skeleton !");
		if (record == false)
			return;
		int skeletonIdent=0;
		for(int i = 5; i>=0; i--)
		{
			if(skeleton_tracked[i])
			{
				skeletonIdent = i;
			}
		}
		currentSkeleton = Skeleton.getSkeleton(skeletonIdent, skeleton_tracked, positions, orientations, joint_status, this);
		
		
		pw.print(new Date().getTime()-time);
		for(int i=0; i<Skeleton.JOINT_COUNT; i++)
		{
			pw.print(";");
			pw.print(currentSkeleton.get3DJointX(i));
			pw.print(",");
			pw.print(currentSkeleton.get3DJointY(i));
			pw.print(",");
			pw.print(currentSkeleton.get3DJointZ(i));
		}
		pw.println();
	}
	
	@Override
	public void onDepthFrameEvent(short[] arg0, byte[] arg1, float[] arg2, float[] arg3) 
	{
		if(counter==0)
			time=new Date().getTime();
		counter+=1;
	}
	
	@Override
	public void onColorFrameEvent(byte[] arg0) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args)
	{
		 Scanner sc = new Scanner(System.in);
		 String strIn;
		 
		 Kinect kinect=new Kinect();
		
		try {
			pw = new PrintWriter(filename);
		} catch (FileNotFoundException e) 
		{
			System.err.println("File not found !");
		} catch(SecurityException e) {
			System.err.println("Security error !");
		} catch (Exception e) {
			System.err.println("Error !");
			e.printStackTrace(System.err);
		} 
		
		pw.println("Time;SPINE_BASE;SPINE_MID;NECK;HEAD;SHOULDER_LEFT;ELBOW_LEFT;WRIST_LEFT;HAND_LEFT;"
				+ "SHOULDER_RIGHT;ELBOW_RIGHT;WRIST_RIGHT;HAND_RIGHT;HIP_LEFT;KNEE_LEFT;"
				+ "ANKLE_LEFT;FOOT_LEFT;HIP_RIGHT;KNEE_RIGHT;ANKLE_RIGHT;FOOT_RIGHT;"
				+ "SPINE_SHOULDER;HAND_TIP_LEFT;THUMB_LEFT;HAND_TIP_RIGHT;THUMB_RIGHT");

		kinect.start(J4KSDK.DEPTH|J4KSDK.SKELETON);
		System.out.print("Start ?");
		strIn = sc.nextLine();
		
		record = true;
				
		System.out.print("Stop ?");
		strIn = sc.nextLine();

		record = false;
		
		kinect.stop();		
		System.out.println("FPS: "+kinect.counter*1000.0/(new Date().getTime()-kinect.time));
	}
	
}
