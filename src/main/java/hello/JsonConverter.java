package hello;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.io.*;
import java.util.Map;

public class JsonConverter
{
    static String createJsonStringFromMap(Map<String, Integer> map, int n)
    {
        String result = "";
        int objectsToReturn = 0;

        result += "[";

        for(String key : map.keySet())
        {
            objectsToReturn++;
            result += "{ \"" + "word" + "\" : \"" + key + "\" ,";
            result += "\"Freq\"" + " : "  + map.get(key) +  "}";
            if(objectsToReturn == n) break;
            result += ",";
        }

        result += "]";

        return result;
    }

    static String[] getStringUrlArrayFromNYT(String str){
        if(str == null){
            return null;
        }
        JsonReader reader = Json.createReader(new StringReader(str));
        JsonObject jsonObject = reader.readObject();
        JsonArray jsonArray = jsonObject.getJsonObject("response").getJsonArray("docs");
        String[] urls = new String[jsonArray.size()];
        for(int i = 0; i < jsonArray.size(); i++)
        {
            urls[i] = jsonArray.getJsonObject(i).getString("web_url");
        }
        return urls;
    }

    // change back to return textArray
    static String[] getStringUrlArrayFromGuardian(String str)
    {
        JsonReader reader = Json.createReader(new StringReader(str));
        JsonObject jsonObject = reader.readObject();
        JsonArray results = jsonObject.getJsonObject("response").getJsonArray("results");
        String[] urls = new String[results.size()];
        int index = 0;
        for(JsonValue jsonValue : results) {
            JsonReader objReader = Json.createReader(new StringReader(jsonValue.toString()));
            JsonObject urlObject = objReader.readObject();
            String urlString = urlObject.get("webUrl").toString();
            urlString = urlString.replaceAll("\"", "");
            urls[index] = urlString;
            index++;
        }
        return urls;
    }

    static String[] getStringUrlArrayFromGuardianLoop(String str)
    {
        JsonReader reader = Json.createReader(new StringReader(str));
        JsonArray responsesArray = reader.readObject().getJsonArray("responses");
        String[] urls = new String[100];
        int urlsIndex = 0;

        for(JsonValue response : responsesArray)
        {
            String[] responseUrls = getStringUrlArrayFromGuardian(response.toString());

            for(int i = 0; i < responseUrls.length; i++)
            {
                if(urlsIndex < 100)
                {
                    urls[urlsIndex] = responseUrls[i];
                    urlsIndex++;
                }
            }
        }

        return urls;
    }



    // TODO what do I pass in here lol. Txt? Json? string?
    // right now it calls getJsonUrlArrayFromNYT which finds the 'Docs' array... Then in another method I pass
    // the returned array to getStringUrlArrayFromJsonUrlArray(JsonArray j). Which does as it says.

    static String[] readJsonFileToStringArrayNYT() throws IOException {
        String jsonString = readFileToString("nyt.json");
        return getStringUrlArrayFromNYT(jsonString);
    }

    static String[] readJsonFileToStringArrayGuardian() throws IOException {
        String jsonString = readFileToString("guardian.json");
        return getStringUrlArrayFromGuardian(jsonString);
    }

    static String readFileToString(String fileName) throws IOException
    {
        String fileToString = "";
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        String line = "";
        while((line = reader.readLine()) != null)
        {
            fileToString += line + " ";
        }
        reader.close();
        return fileToString;
    }
}
