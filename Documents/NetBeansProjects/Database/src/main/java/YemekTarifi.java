// Imports the Google Cloud client library
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class YemekTarifi {
  public static void main(String[] args) throws Exception {
    Scanner scan = new Scanner(new File("C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\MainSub.txt"));
      
    JSONParser jsonParser = new JSONParser();
    Object jsonObject = (Object) jsonParser.parse(new FileReader("C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\nefisyemekler.json"));
    
    // Instantiates a client/
    DatastoreOptions options = DatastoreOptions.newBuilder()
        .setProjectId("ergen-project-200519")
        .setCredentials(GoogleCredentials.fromStream(
          new FileInputStream("C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\ergen-project-5a6b27297e8e.json"))).build();
   Datastore datastore = options.getService();
   
    // The kind for the new entity
    String kind = "Yemek Tarifleri";
    // The name/ID for the new entity
        
    JSONArray a = (JSONArray) jsonObject;
    for(int i = 0 ; i< a.size(); i++)
    {
        JSONObject o = (JSONObject) a.get(i);
        String name = (String) o.get("name");
        System.out.println(name);
        // The Cloud Datastore key for the new entity
        KeyFactory keyFactory = datastore.newKeyFactory().setKind(kind);
        Key taskKey = datastore.allocateId(keyFactory.newKey());
        Object n = (Object) (o.get("ingredients"));
        JSONArray ar = (JSONArray) n;
        Entity task = Entity.newBuilder(taskKey)
            .set("name", name)
            .set("img", (String) o.get("img"))
            .build();
        datastore.put(task);
        String ing = "";
        for(int j = 1; j < ar.size(); j++){
            ing += (String) ar.get(j) + "\n";
        }
        Entity t = Entity.newBuilder(datastore.get(taskKey))
                .set("İçerik", ing)
                .build();
        datastore.update(t);
        /*
        n = (Object) (o.get("recipe"));
        ar = (JSONArray) n;
        String tarif = "";
        for(int j = 1; j < ar.size(); j++){
            tarif += (String) ar.get(j) + "\n";
        }
        t = Entity.newBuilder(datastore.get(taskKey))
                .set("Tarif", tarif)
                .build();
        datastore.update(t);*/
    }
  }
}