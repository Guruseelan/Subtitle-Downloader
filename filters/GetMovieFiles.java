package filters;
import java.io.*;
import java.util.*;
public class GetMovieFiles
{
	final  String[] mov_ext={".m4v",".mp4",".mpg",".flv",".mov",".avi"};
	 ArrayList<String> movies=new ArrayList<String>();
	boolean is_mov_file(String t)
	{
		for(String s:mov_ext)
		{
			if(t.contains(s))
				return true;
		}
		return false;
	}
	
	boolean sub_nt_exist(File dir,String mov_name)
	{
		File[] fList = dir.listFiles();
		if (mov_name.indexOf(".") > 0) 
					mov_name = mov_name.substring(0, mov_name.lastIndexOf("."));
		for (File file : fList)
		{
			if (file.isFile())
			{
				String t=file.getName();
				if(t.contains(".srt")&&t.contains(mov_name))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public ArrayList<String> getmovies(String dir)
	{
	   
	   int i=0;
	   try
	   {
		File directory = new File(dir); //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList)
		{
            if (file.isFile())
			{
				if(is_mov_file(file.getName()))
				{		
				if(sub_nt_exist(directory,file.getName()))
				{	
					movies.add(""+file);
				}
				}				
            }
			else if (file.isDirectory())
			{
                getmovies(""+file.getAbsolutePath());
            }
		}
        }
		
		catch(Exception e){}
		return(movies);
	}

}
   
