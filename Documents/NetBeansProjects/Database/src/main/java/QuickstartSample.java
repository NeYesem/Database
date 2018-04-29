// Imports the Google Cloud client library

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.StructuredQuery;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Iterator;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class QuickstartSample { 

    public static void main(String[] args) throws Exception {
        JSONParser jsonParser = new JSONParser();
        /*String[] files = {"C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\ozel.json",
    "C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\balik.json",
    "C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\eturunu.json",
    "C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\geleneksel.json",
    "C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\icecek.json",
    "C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\meyve.json",
    "C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\muhtelif.json",
    "C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\sebze.json",
    "C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\suturunleri.json",
    "C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\sekerli.json",
    "C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\tahil.json",
    "C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\yaglar.json",
    "C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\yaglitohum.json",
    "C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\yumurta.json",
    };
    String[] type = {"özel","balık","et ürünü", "geleneksel", "içecek", "meyve", "muhtelif", "sebze", "süt ürünleri", "şekerli"
            , "tahıl","yağ","yağlı tohum","yumurta"};*/

        // Instantiates a client/
        IngrChecker ingChecker = new IngrChecker();
        DatastoreOptions options = DatastoreOptions.newBuilder()
                .setProjectId("ergen-project-200519")
                .setCredentials(GoogleCredentials.fromStream(
                        new FileInputStream("C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\ergen-project-5a6b27297e8e.json"))).build();
        Datastore datastore = options.getService();
        
        int[] check = new int[ingChecker.ingList.size()];
        Arrays.fill(check, 0);
        Object jsonObject = (Object) jsonParser.parse(new FileReader("C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\all.json"));

        // The kind for the new entity
        String kind = "Ingredients";
        // The name/ID for the new entity

        JSONArray a = (JSONArray) jsonObject;
        for (int i = 0; i < a.size(); i++) {
            JSONObject o = (JSONObject) a.get(i);
            String name = (String) o.get("name");
            // The Cloud Datastore key for the new entity
            KeyFactory keyFactory = datastore.newKeyFactory().setKind(kind);
            Key taskKey = datastore.allocateId(keyFactory.newKey());
            Object n = (Object) (o.get("nutritions"));
            JSONArray ar = (JSONArray) n;
            System.out.println(name);
            /*if(!exists(name, datastore)){*/
                Entity task = Entity.newBuilder(taskKey)
                        .set("name", charChange(name))
                        .set("id", i + 1)
                        .build();
                datastore.put(task);
            //}
            for (int j = 0; j < ar.size(); j++) {
                JSONObject o2 = (JSONObject) ar.get(j);
                check[ingChecker.indexOf((String) o2.get("name"))] = 1;
                Entity t = Entity.newBuilder(datastore.get(taskKey))
                        .set(charChange((String) o2.get("name")), (Double) o2.get("value"))
                        .build();
                datastore.update(t);
            }
            for (int j = 0; j < check.length; j++) {
                if (check[j] == 0) {
                    Entity t = Entity.newBuilder(datastore.get(taskKey))
                            .set(charChange((String) ingChecker.get(j)), 0.0)
                            .build();
                    datastore.update(t);
                }
            }
            Arrays.fill(check, 0);
        }
    }

    private static String charChange(String s) {
        String result = "";
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            switch (c) {
                case 'ş':{
                    result += "_s";
                    break;
                }
                case 'ü':{
                    result += "_u";
                    break;
                }
                case 'ö':{
                    result += "_o";
                    break;
                }
                case 'İ':{
                    result += "_I";
                    break;
                }
                case 'ğ':{
                    result += "_g";
                    break;
                }
                case 'ı':{
                    result += "_i";
                    break;
                }
                case 'ç':{
                    result += "_c";
                    break;
                }
                case 'Ş':{
                    result += "_S";
                    break;
                }
                case 'Ü':{
                    result += "_U";
                    break;
                }
                case 'Ö':{
                    result += "_O";
                    break;
                }
                case 'Ğ':{
                    result += "_G";
                    break;
                }
                case 'Ç':{
                    result += "_C";
                    break;
                }
                default:
                    result += c + "";
            }
        }
        return result;
    }
    
    private static boolean exists(String s, Datastore d){
        Query<Entity> query = Query.newEntityQueryBuilder()
                    .setKind("Ingredients")
                    .setFilter(StructuredQuery.CompositeFilter.and(
                            //PropertyFilter.eq("__key__", datastore.newKeyFactory().setKind("Ingredient").newKey(1))))
                            StructuredQuery.PropertyFilter.eq("name", s)))
                    .build();
            Iterator<Entity> ents = d.run(query);   
        return ents.hasNext();
    }
}
