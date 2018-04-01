//jar -cvmf Downloader.mf Downloader.jar Downloader.class 12 scrapers filters
//set classpath=e:\fnsr\jsoup-1.8.1.jar;.;%classpath% 
//set path="C:\Program Files\Java\jdk1.8.0_151\bin"
import filters.*;
import scrapers.*;
import java.io.*;
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;
import java.lang.Object;
class Downloader extends JFrame implements ActionListener
{
JTextField t1;
JFrame f;
JLabel l;
JButton b,bf;
JTextArea msg;
Font fn;

public void download(String dir)
{
		ArrayList<String> mov_files=new ArrayList<String>();  
		String mov_name=""; 
		
		mov_files= new GetMovieFiles().getmovies(dir);
		int df=0;
		try
		{
		for(String s:mov_files)
		{  
		df=0;
		mov_name=new MovieNameFilter().filter(s);
			
		if(df==0)
			df=new Yify().srchyify(mov_name,s);
		if(df==0)
			df=new SubScene().srchsubscene(mov_name,s);
		if(df==0)
			df=new SubScene().srchrelsub(new File(s).getName(),s);
		
		if(df==1)
			msg.setText(msg.getText()+mov_name+".srt downloaded\n");
		else
			msg.setText(msg.getText()+mov_name+".srt not found\n");
		}  
		}
		catch(Exception e){}
		
	
}
public void actionPerformed(ActionEvent e)
{
	if(e.getSource()==b)
	{
		t1.setText(dirchoose());
	}
	else if(e.getSource()==bf)
	{
		download(t1.getText());
	}
}
Downloader()
{   
	f=new JFrame("Subtitle Downloader"); 
	
	
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


public String dirchoose()
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
	new Downloader();	
}
}
