package filters;
import java.io.*;
import java.util.*;
public class MovieNameFilter
{
	
	final String jnks[]={"moviescounter","hdpopcorns","blueray","bluray","720p","1080p","hd","movies","download","movie","single","part","dvd","rip","dvdrip"};
boolean rmv_jnk(String w)
	{
		for(String s:jnks)
		{
			if(w.contains(s))
				return false;
		}
		return true;
	}
	public String filter(String t)
	{


	t=new File(t).getName();
	
	if (t.indexOf(".") > 0) 
					t = t.substring(0, t.lastIndexOf("."));
	String[] words=t.toLowerCase().split("\\.|\\-|\\(|\\)");
	
	t="";
	for(String w:words)
	{
		
		if(rmv_jnk(w))
			t=t+" "+w;
	}
	t=t.trim();
	System.out.println(t);

	return(t);
}
	
}