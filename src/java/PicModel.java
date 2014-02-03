/**
 *
 * @author siddarthr
 * Description: Acts as the Model in the MVC framework. Does the actual image fetching from the given website
 * Date created: 30th Jan 2014
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class PicModel {
    private String pictureTag;
    private String pictureURL;
    private String pictureTitle;
    private String num;
    private String assetId;
    private Random r;
    int rand;
    boolean noImg=false;
    
    /**
     * Performs image search in the given URL
     * @param searchTag artist name to be searched
     */
    public void doImgSearch(String searchTag) {
         pictureTag = searchTag;
         r= new Random();
         String response = "";
        try {
            // Create a URL for the desired page            
            URL url = new URL("https://images.nga.gov/en/search/show_advanced_search_page/?service=search&action=do_advanced_search&language=en&form_name=default&all_words=&exact_phrase=&exclude_words=&artist_last_name="+searchTag+"&keywords_in_title=&accession_number=&school=&Classification=Painting&medium=&year=&year2=&open_access=1");
            // Create an HttpURLConnection.  This is useful for setting headers
            // and for getting the path of the resource that is returned (which 
            // may be different than the URL above if redirected).
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader
                    (new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            
            //scrape number of total images
            int pic_num = response.lastIndexOf("images found");
            num = response.substring(pic_num-4, pic_num-1);
            num = num.replaceAll("[^0-9]","");
            System.out.println("Sid "+num);
            int size = Integer.parseInt(num);
        
            //if no images, then return
            if(size == 0) {
                noImg = true;
                return;
            }
            //generate random number between 1 and max_num
            noImg = false;
            rand = r.nextInt(size-1)+1;
            in.close();
        } catch (IOException e) {
            noImg = true;
            return;
        }
        
        //Scrape title and asset id from source page
        int startIndex = 0, startTitle = 0, endTitle = 0, startURL = 0, endURL = 0;
        int startAsset = 0, endAsset = 0;
        for (int i = 0 ; i <= rand; i++) {
        //int startTitle = response.indexOf("alt=\"\" title=",);
        startTitle = response.indexOf("alt=\"\" title=", startIndex);
        endTitle= response.indexOf("\"", startTitle+14);
        startAsset = response.indexOf("assetid=\"", startIndex);
        endAsset = response.indexOf("\"", startAsset+9);
        startAsset = response.indexOf("assetid=\"", startAsset);
        endAsset = response.indexOf("\"", startAsset+9);
        // find the picture URL to scrape
        startURL = response.indexOf("src=\"/assets", endTitle);

        // only start looking after the quote before http
        endURL = response.indexOf("\"", startURL+5);   
        startIndex = endURL;
        }
        assetId = response.substring(startAsset+9,endAsset);
        pictureTag=response.substring(startTitle+14, endTitle);
        pictureURL = "src=\"https://images.nga.gov"+response.substring(startURL+5, endURL+1);
    }
    
    /**
     * 
     * @return true if no images present
     */
    public boolean isEmptyImg(){
        return noImg;
    }
    
    /**
     * 
     * @param picsize denotes if client is mobile or desktop
     * @return url to be embedded in HTML
     */
    public String interestingPictureSize(String picsize) {
        if(picsize.equals("mobile"))
            return pictureURL;
        else
            return "src=\"http://images.nga.gov/?service=asset&amp;action=show_preview&amp;asset="+assetId+"\"";
    }
    
    /**
     * Returns title of painting
     * @return name of painting
     */
    public String getPictureTag() {
        return (pictureTag);
    }
}
