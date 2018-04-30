
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Güneş Batmaz
 */
public class IngrChecker {
    public ArrayList<String> ingList;
    public IngrChecker() throws FileNotFoundException, IOException, ParseException{
        check();
    }
    private void check() throws FileNotFoundException, IOException, ParseException{
        ingList = new ArrayList<String>();
        JSONParser jsonParser = new JSONParser();
        Object jsonObject = (Object) jsonParser.parse(new FileReader(
                "C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\all.json"));

        JSONArray a = (JSONArray) jsonObject;
        for(int i = 0 ; i< a.size(); i++)
        {
            JSONObject o = (JSONObject) a.get(i);
            String name = (String) o.get("name");
            Object n = (Object) (o.get("nutritions"));
            JSONArray ar = (JSONArray) n;
            for(int j = 0; j <ar.size(); j++){
                if(!ingList.contains(((JSONObject)ar.get(j)).get("name")))
                    ingList.add((String) ((JSONObject)ar.get(j)).get("name"));
            }
        }
        Collections.sort(ingList);
    }
    
    public int indexOf(String s){
        return ingList.indexOf(s);
    }
    
    public String get(int i){
        return ingList.get(i);
    }    
}
