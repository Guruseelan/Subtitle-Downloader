//jar -cvmf Fnsr.mf Fnsr.jar Fnsr.class
//set classpath=e:\fnsr\jsoup-1.8.1.jar;.;%classpath% 

import java.io.*;
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.util.*;
import javax.imageio.*;
import java.lang.Object;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Connection;
import org.jsoup.select.Elements;
import java.net.*; 


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

class Fnsr extends JFrame implements ActionListener
{
JTextField t1;
JFrame f;
JLabel l;
JButton b,bf;
JTextArea msg;
Font fn;
Fnsr()
{   
	f=new JFrame("FNSR Subtitle Downloader"); 
	
	
	try
		{
           JLabel jl=new JLabel(new ImageIcon(ImageIO.read(new File("12.jpg"))));
		   jl.setBounds(0,0,850,650); 
		   f.setContentPane(jl);
        } catch (IOException e) {}
		f.pack();
	
	fn=new Font("Arial",Font.BOLD,15); 
	setFont(fn);
	
	l=new JLabel("Choose Folder.");  l.setFont(fn);l.setForeground(Color.white);
	l.setBounds(70,70, 120,30); 
	f.add(l);
	
	t1=new JTextField();  
    t1.setBounds(200,70, 300,30); 
	t1.setEditable(false); 	
	f.add(t1); 
	
	fn=new Font("Arial",Font.BOLD,22); 
	b=new JButton("Browse");  b.setFont(fn);
    b.setBounds(500,70,120,30); 
	f.add(b);    
	b.addActionListener(this);  
	
	bf=new JButton("Download");  bf.setFont(fn);
    bf.setBounds(500,190,120,30); 
	f.add(bf);    
	bf.addActionListener(this);  
	
	msg=new JTextArea();  
	
    JScrollPane scroll= new JScrollPane(msg);  
  
    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
    scroll.setBounds(100,300,600,300);  
    f.add(scroll);  
	
	f.setSize(850,650);  
    f.setLocationRelativeTo(null);
    f.setLayout(null);    
    f.setVisible(true); 
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	
}
  public void actionPerformed(ActionEvent e)
  {
	if(e.getSource()==b)
	{
		t1.setText(dirc());
	}
	else if(e.getSource()==bf)
	{
		listf(t1.getText());
	}
  }
  void listf(String dir)
  {
	   try
	   {
		File directory = new File(dir); //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList)
		{
            if (file.isFile())
			{
				String t=file.getName();
				if(t.contains(".mp4")||t.contains(".flv")||t.contains(".mov")||t.contains(".avi"))
				{		
					 //already tr dup .sbt
					int sub_tr=0;
					if (t.indexOf(".") > 0)
						t = t.substring(0, t.lastIndexOf("."));
						File[] fList1 = directory.listFiles();	
						for (File file1 : fList1)
						{
							if (file1.isFile())
							{
							String t1=file1.getName();
							if(t1.contains(".srt")&&t1.contains(t))
							{
								sub_tr=1;break;
							}
								
						}
						}
				if(sub_tr==0)
				{	
					namefilter(""+file);//srchweb(""+file);
				}
				}				
            }
			else if (file.isDirectory())
			{
                listf(""+file.getAbsolutePath());
            }
		}
        }
		
		catch(Exception e)
		{
			//System.out.println(e.getMessage());
		}
  }

void namefilter(String mn)throws Exception
{
File fn=new File(mn);
String t=fn.getName();
	if (t.indexOf(".") > 0) 
					t = t.substring(0, t.lastIndexOf("."));	
	String[] words=t.toLowerCase().split("\\.|\\-|\\(|\\)");
	System.out.println(t);
	t="";
	for(String w:words)
	{
		
		if(!(w.contains("moviescounter")||w.contains("hdpopcorns")||w.contains("blueray")||w.contains("bluray")||w.contains("720p")||w.contains("1080p")))
			t=t+" "+w;//System.out.println(w);  
	}
	t=t.trim();
	System.out.println(t);

	int df=0;
	
	if(df==0)
		df=srchyify(t,mn);
	if(df==0)
		df=srchsubscene(t,mn);
	if(df==0)
		df=srchrelsub(fn.getName(),mn);

	
}	
   
int srchyify(String t,String pth) throws IOException 
	{
		
	//JOptionPane.showMessageDialog(null, "srcweb");
	int sf=0;
		String g="";
		String s_url = "https://www.yifysubtitles.com/search";//searching page1
		if (t.indexOf(".") > 0) 
					t = t.substring(0, t.lastIndexOf("."));
						
		String searchTerm = t;
		String sm_url = s_url + "?q="+searchTerm;
		Document doc;
		Elements results;
		doc= Jsoup.connect(sm_url).userAgent("Mozilla/5.0").get();
		results = doc.select("a[href]");

		for (Element result : results) 
		{
			String linkHref = result.attr("href");
			String linkText = result.text();
			//System.out.println("Text::" + linkText + ", URL::" + linkHref);
			if((linkText.toLowerCase()).contains(searchTerm.toLowerCase()))
			{	sf=1;g=linkHref; break;}
		}
		

		
//page2 download page url

//	System.out.println("result: "+g);
		if(sf==1)
		{
			sf=0;
			sm_url = "https://www.yifysubtitles.com/"+g;
			doc= Jsoup.connect(sm_url).userAgent("Mozilla/5.0").get();
			results = doc.getElementsByTag("tr");
			for (Element result : results)
			{
			Elements results2 = result.getElementsByClass("flag-cell");//language
			for (Element result2 : results2)
			{
			String tdText = result2.text();
			//System.out.println(tdText);
			if((tdText.toLowerCase()).contains("english"))
			{	
			sf=1;g=""+result.select("a[href]").attr("href");
			break;
			}
			}
			if(sf==1)
				break;
			}
		}
	
//System.out.println("result: "+g);

//page3 download file page url
	if(sf==1)
	{
		sf=0;
		
		sm_url = "https://www.yifysubtitles.com/"+g;
		doc= Jsoup.connect(sm_url).userAgent("Mozilla/5.0").get();
		results = doc.select("a[href]");;

		for (Element result : results)
			{
			String linkHref = result.attr("href");
			String linkText = result.text();
			if((linkText.toUpperCase()).contains("DOWNLOAD SUBTITLE"))
			{	sf=1;g=linkHref; break;}
		}
	}
	
//System.out.println("result: "+g);

//download 

if(sf==1)
{
	sf=0;
	msg.setText(msg.getText()+g+"downloading\n");
	try 
{
            URL url = new URL(g);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            FileOutputStream out = new FileOutputStream(searchTerm+".zip");
            byte[] b = new byte[1024];
            int count;
            while ((count = in.read(b)) >= 0) {
                out.write(b, 0, count);
            }
            out.flush(); out.close(); in.close();                   
 
        } catch (IOException e) {
            //e.printStackTrace();
        }
    
//un zip
ext(searchTerm+".zip",pth);
return 1;
}      
  return 0;
}

int srchsubscene(String t,String pth) throws Exception 
	{
		
	//JOptionPane.showMessageDialog(null, "srcweb");
	try{
		
		int sf=0;
		String g="";
		String s_url = "https://www.subscene.com/subtitles/title?q=";//searching page1
		if (t.indexOf(".") > 0) 
					t = t.substring(0, t.lastIndexOf("."));
						
		String searchTerm = t;
		String sm_url = s_url+searchTerm;
		Document doc;
		Elements results;
		doc= Jsoup.connect(sm_url).userAgent("Mozilla/5.0").get();
		results = doc.select("a[href]");

		for (Element result : results) 
		{
			String linkHref = result.attr("href");
			String linkText = result.text();
			//System.out.println("Text::" + linkText + ", URL::" + linkHref);
			if((linkText.toLowerCase()).contains(searchTerm.toLowerCase()))
			{	sf=1;g=linkHref; break;}
		}
		
System.out.println(g);
		if(sf==1)
		{
			sf=0;
			sm_url = "https://www.subscene.com/"+g;
			doc= Jsoup.connect(sm_url).userAgent("Mozilla/5.0").get();
			results = doc.select("a[href]");
			for (Element result : results) 
			{
			String linkHref = result.attr("href");
			String linkText = result.text();
			//System.out.println("Text::" + linkText + ", URL::" + linkHref);
			if((linkText.toLowerCase()).contains("english"))
			{	
			sf=1;g=linkHref;
			break;
			}
			}
			
		}
	
System.out.println("result: "+g);

if(sf==1)
{
	sf=0;
      
Document subDownloadPage = Jsoup.connect("https://www.subscene.com"+g).userAgent("Mozilla/5.0").get();
g = subDownloadPage.select("a#downloadButton").attr("href");
System.out.println("result: "+g);


Connection connection = Jsoup.connect("https://www.subscene.com"+g).userAgent("Mozilla/5.0");
connection.timeout(5000);
Connection.Response resultImageResponse = connection.ignoreContentType(true).execute();

// save to file
FileOutputStream out = new FileOutputStream(searchTerm+".zip");
out.write(resultImageResponse.bodyAsBytes());
out.close();


    
//un zip
ext(searchTerm+".zip",pth);
return 1;
}      
	}
	catch(Exception e){System.out.println(e.getMessage());}
  return 0;
}

int srchrelsub(String t,String pth) throws Exception 
	{
		
	//JOptionPane.showMessageDialog(null, "srcweb");
	try{
		
		int sf=0;
		String g="";
		String s_url = "https://www.subscene.com/subtitles/release?q=";//searching page1
		if (t.indexOf(".") > 0) 
					t = t.substring(0, t.lastIndexOf("."));
						
		String searchTerm = t;
		String sm_url = s_url+searchTerm;
		Document doc;
		Elements results;
		doc= Jsoup.connect(sm_url).userAgent("Mozilla/5.0").get();
		results = doc.select("h1");

		for (Element result : results) 
		{
			//String linkHref = result.attr("href");
			String linkText = result.text();
			//System.out.println("Text::" + linkText + ", URL::" + linkHref);
			if((linkText.contains("Subtitle search by release name")))
			{	sf=1; break;}
		}

		if(sf==1)
		{
			sf=0;
			//sm_url = "https://www.subscene.com/"+g;
			doc= Jsoup.connect(sm_url).userAgent("Mozilla/5.0").get();
			results = doc.select("a[href]");
			for (Element result : results) 
			{
			String linkHref = result.attr("href");
			String linkText = result.text();
			//System.out.println("Text::" + linkText + ", URL::" + linkHref);
			if((linkText.toLowerCase()).contains("english"))
			{	
			sf=1;g=linkHref;
			break;
			}
			}
			
		}
	
System.out.println("result: "+g);

if(sf==1)
{
	sf=0;
      
Document subDownloadPage = Jsoup.connect("https://www.subscene.com"+g).userAgent("Mozilla/5.0").get();
g = subDownloadPage.select("a#downloadButton").attr("href");
System.out.println("result: "+g);


Connection connection = Jsoup.connect("https://www.subscene.com"+g).userAgent("Mozilla/5.0");
connection.timeout(5000);
Connection.Response resultImageResponse = connection.ignoreContentType(true).execute();

// save to file
FileOutputStream out = new FileOutputStream(searchTerm+".zip");
out.write(resultImageResponse.bodyAsBytes());
out.close();


    
//un zip
ext(searchTerm+".zip",pth);
return 1;
}      
	}
	catch(Exception e){System.out.println(e.getMessage());}
  return 0;
}

public void ext(String file,String pth)
		{
	//	JOptionPane.showMessageDialog(null, "ext fun");
		String t;
		t=pth;
		if (t.indexOf("\\") > 0) 
					t = t.substring(0, t.lastIndexOf("\\"))+"\\";
		//		System.out.println(t);
		FileInputStream fis = null;
        ZipInputStream zipIs = null;
        ZipEntry zEntry = null;
        try 
		{
            fis = new FileInputStream(file);
            zipIs = new ZipInputStream(new BufferedInputStream(fis));
            while((zEntry = zipIs.getNextEntry()) != null)
			{
                try
				{
                    byte[] tmp = new byte[4*1024];
                    FileOutputStream fos = null;
                    String opFilePath = t+zEntry.getName();
                 //   System.out.println("Extracting file to "+opFilePath);
					
                    fos = new FileOutputStream(opFilePath);
                    int size = 0;
                    while((size = zipIs.read(tmp)) != -1){
                        fos.write(tmp, 0 , size);
                    }
					t=pth;
					if (t.indexOf(".") > 0) 
					t = t.substring(0, t.lastIndexOf("."));
				
					
					
                    fos.flush();
                    fos.close();
					File fosn=new File(opFilePath);
					fosn.renameTo(new File(t+".srt"));
					msg.setText(msg.getText()+t+".srt downloaded\n");
                } 
				catch(Exception ex)
				{
                     System.out.println(ex.getMessage());
                }
            }
            zipIs.close();
			new File(file).delete();
        } 
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) { e.printStackTrace();}
		}
  public String dirc()
  {
	JFileChooser chooser; 
    chooser = new JFileChooser(); 
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle("Choose Directory/Folder");
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.setAcceptAllFileFilterUsed(false);
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
		return ("" + chooser.getSelectedFile());
	else
		 return ""; 
   }  
   public static void main(String arg[])
{
	Fnsr f=new Fnsr();	
}
}