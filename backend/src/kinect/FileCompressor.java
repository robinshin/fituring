package kinect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import edu.ufl.digitalworlds.utils.ParallelThread;

public class FileCompressor extends ParallelThread{
	
	KinectFileWriter out;
	int num_of_depth_frames;
	
	public FileCompressor(KinectFileWriter file)
	{
		out=file;
		num_of_depth_frames=out.num_of_depth_frames;
	}
	
	public void run() {
		
		try {
			File f=new File(out.filename+".temp");
			FileInputStream in=new FileInputStream(f);
		
			out.openZipFile();
			byte depth_array[]=new byte[16+12+2*out.depth_width*out.depth_height];
			//byte video_array[]=new byte[4*out.video_width*out.video_height];
			
			setMaxProgress(num_of_depth_frames);
			
			for(int i=0;i<num_of_depth_frames;i++)
			{
				int bytes_read=in.read(depth_array,0,depth_array.length);
				while(bytes_read<depth_array.length)
					bytes_read+=in.read(depth_array,bytes_read,depth_array.length-bytes_read);
				out.writeDepthFrameZip(depth_array);

				/*bytes_read=in.read(video_array,0,video_array.length);
				while(bytes_read<video_array.length)
					bytes_read+=in.read(video_array,bytes_read,video_array.length-bytes_read);
				out.writeVideoFramePNG(video_array);*/
				
				setProgress(i+1);
			}
			in.close();
			out.closeZipFile();
			f.delete();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
