package scrapers;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Connection;
import org.jsoup.select.Elements;
import java.net.*; 

public class SubScene
{
	public int srchsubscene(String mov_name,String pth) throws Exception 
	{
	try{
		
		System.out.println("srchsubscene");
		int sf=0;
		String g="";
		String sm_url = "https://www.subscene.com/subtitles/title?q="+mov_name;
		Document doc;
		Elements results;
		doc= Jsoup.connect(sm_url).userAgent("Mozilla/5.0").get();
		results = doc.select("a[href]");

		for (Element result : results) 
		{
			String linkHref = result.attr("href");
			String linkText = result.text();
			if((linkText.toLowerCase()).contains(mov_name.toLowerCase()))
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
FileOutputStream out = new FileOutputStream(mov_name+".zip");
out.write(resultImageResponse.bodyAsBytes());
out.close();


    
//un zip
new UnZip().ext(mov_name+".zip",pth);
return 1;
}      
	}
	catch(Exception e){System.out.println(e.getMessage());}
  return 0;
}

public int srchrelsub(String mov_name,String pth) throws Exception 
	{
		
	System.out.println("srchrelsub");
	try{
		
		int sf=0;
		String g="";
		String sm_url = "https://www.subscene.com/subtitles/release?q="+mov_name;
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
FileOutputStream out = new FileOutputStream(mov_name+".zip");
out.write(resultImageResponse.bodyAsBytes());
out.close();


    
//un zip
new UnZip().ext(mov_name+".zip",pth);
return 1;
}      
	}
	catch(Exception e){System.out.println(e.getMessage());}
  return 0;
}
}