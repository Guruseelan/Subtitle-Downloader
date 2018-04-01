package scrapers;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Connection;
import org.jsoup.select.Elements;
import java.net.*; 

public class Yify
{
	public int srchyify(String mov_name,String pth) throws IOException 
	{
		
		System.out.println("yify");
		int sf=0;
		String g="";
		String sm_url = "https://www.yifysubtitles.com/search?q="+mov_name;
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


if(sf==1)
{
	sf=0;
try 
{
            URL url = new URL(g);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            FileOutputStream out = new FileOutputStream(mov_name+".zip");
            byte[] b = new byte[1024];
            int count;
            while ((count = in.read(b)) >= 0) {
                out.write(b, 0, count);
            }
            out.flush(); out.close(); in.close();                   
 
        } catch (IOException e) {
            //e.printStackTrace();
        }

new UnZip().ext(mov_name+".zip",pth);
return 1;
}      
  return 0;
}
	
}