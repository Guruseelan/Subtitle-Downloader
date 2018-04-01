package scrapers;
import java.io.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZip
{
public void ext(String zipfile,String p)
{
		String t;
		t=new File(p).getName();
		if (t.indexOf(".") > 0) 
					t = t.substring(0, t.lastIndexOf("."));
		if (p.indexOf("\\") > 0) 
					p = p.substring(0, p.lastIndexOf("\\"))+"\\";
		FileInputStream fis = null;
        ZipInputStream zipIs = null;
        ZipEntry zEntry = null;
        try 
		{
            fis = new FileInputStream(zipfile);
            zipIs = new ZipInputStream(new BufferedInputStream(fis));
            while((zEntry = zipIs.getNextEntry()) != null)
			{
                try
				{
                    byte[] tmp = new byte[4*1024];
                    FileOutputStream fos = null;
                    String opFilePath = zEntry.getName();
                    fos = new FileOutputStream(opFilePath);
                    int size = 0;
                    while((size = zipIs.read(tmp)) != -1){
                        fos.write(tmp, 0 , size);
                    }
						
					
                    fos.flush();
                    fos.close();
					File fosn=new File(opFilePath);
					fosn.renameTo(new File(p+t+".srt"));
					System.out.println("Downloaded: "+fosn.getName());
				} 
				catch(Exception ex)
				{
                     System.out.println(ex.getMessage());
                }
            }
            zipIs.close();
			new File(zipfile).delete();
        } 
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) { e.printStackTrace();}
		}	
}