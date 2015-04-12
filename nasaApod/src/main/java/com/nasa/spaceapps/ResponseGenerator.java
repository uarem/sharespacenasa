package com.nasa.spaceapps;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.xml.ws.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by uarem on 4/12/2015.
 */
public class ResponseGenerator {
    private Logger logger = Logger.getLogger(ResponseGenerator.class.getName());

    public JSONObject makeRestCalls(String datePicked) {
    	JSONObject responseMessage = null;
        try {
            String[] dates = datePicked.split("-");
            JSONArray responses = new JSONArray();

            for (int i = Integer.parseInt(dates[0]); i>=1996; i--) {
                String dateToLookup = i + "-" + dates[1] + "-" + dates[2];
                URL url = new URL("https://api.data.gov/nasa/planetary/apod?date=" + dateToLookup + "&concept_tags=true&api_key=KSNdGp8Qw1hjKDtieERNYoKJH464iiSC8Aopq96o");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    logger.info("No respose for "+dateToLookup+ "... moving on!");
                    continue;
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;
                while ((output = br.readLine()) != null) {
                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(output);

                    // get a String from the JSON object
                    String date = (String) jsonObject.get("date");
                    String title = (String) jsonObject.get("title");
                    String explanation = (String) jsonObject.get("explanation");
                    String imageUrl = (String) jsonObject.get("url");

                    System.out.println("date: " + date + " title: " + title + " explanation: " + explanation + " url: " + url);

                    JSONObject response = new JSONObject();
                    response.put("date", date);
                    response.put("title", title);
                    response.put("explanation", explanation);
                    response.put("url", imageUrl);

                    responses.add(response);

                }
                conn.disconnect();
            }

            responseMessage = new JSONObject();
            responseMessage.put("posts", responses);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return responseMessage;
    }
}
