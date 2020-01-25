/*
 * Jim Klayder -- spring 2018
 * 
 * Here are several utility routines that might be helpful.
 */
package helpers;


import java.io.*;
import java.net.*;


public class Utility {
    
    //this method is based on lines 476 - 488 of In.java
    public static String getFileAsString(String fileName)
    {
        String theFileString = "";
        In in;
        // read one line at a time from file in current directory
        //System.out.println("readLine() from current directory");
        //System.out.println("---------------------------------------------------------------------------");
        try {
            in = new In(fileName);
            while (!in.isEmpty()) {
                theFileString += in.readLine()+"\n";
                
            }
        }
        catch (Exception e) { System.out.println(e); }
        System.out.println("theFileString\n"+theFileString);
        return theFileString;
    }
    
    //original idea from:
    //     https://duckduckgo.com/?q=java+html+get+web+page+code+how+to&t=seamonkey&ia=qa&iax=qa
    
    //https://stackoverflow.com/questions/8616781/how-to-get-a-web-pages-source-code-from-java#8616808
    
    //alterations by Klayder
    public static String getUrlSource(String url){
            
        StringBuilder a = new StringBuilder("could not retrieve html code from:  "+url);
        try{ 
            URL theURL = new URL(url);
            URLConnection connection = theURL.openConnection();
            
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                connection.getInputStream(), "UTF-8"))) 
            {
                String inputLine;
                a = new StringBuilder();
                while ((inputLine = in.readLine()) != null)
                    a.append(inputLine);
            }
          }catch (Exception ex)
        {
            System.out.println("ex:   "+ex.toString());
        }

        return a.toString();
    }
    
    /*
    in the example call:
        int num = getRandomFromTo(10,15);
    
    num should be set to 10, 11, 12, 13, 14, or 15
    
    */
    public static int getRandomFromTo(int from, int to)
    {
        if (from > to)
        {
            System.out.println("error in Utility.getRandomFromTo");
            System.out.println("can't have 'from' < 'to'");
            System.out.println("from:  "+from+"      to:  "+to);
            return -99999;
        }else
        {
            int range = to - from + 1;
            int answer = (int)(Math.random() * range);
            return from + answer;
        }
    }
    
    
    /*
     * Test Utility methods
     */
/*
    public static void main(String[] args) 
    {
        System.out.println("Test Utility methods");
        
        System.out.println("100 random numbers from 10 to 14  \n");
        for (int i = 0; i < 100; i++) 
        {
            if (i % 25 == 0)
            {
                System.out.println("");
            }
            
            int num = Utility.getRandomFromTo(10, 14);
            System.out.print("  "+num);
            
            
        }
        System.out.println("\n\n\n\n\n");
        
        System.out.println("web data from:   http://www.jimklayder.com/  \n");
        String webData = Utility.getUrlSource("http://www.jimklayder.com/");
        System.out.println(webData);
                
        
    }
*/
    
}
