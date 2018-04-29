
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

//Auto indent alt+shift+f
public class DBQuery {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner scan = new Scanner(new FileReader("C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\MainSub.txt"));
        DatastoreOptions options = DatastoreOptions.newBuilder()
                .setProjectId("ergen-project-200519")
                .setCredentials(GoogleCredentials.fromStream(
                        new FileInputStream("C:\\Users\\Güneş Batmaz\\Desktop\\içindekiler\\ergen-project-5a6b27297e8e.json"))).build();
        Datastore datastore = options.getService();
        String ing ="";
        int j = 7;
        ArrayList<String> ings = new ArrayList<String>();
        while (scan.hasNext() && j < 14) {
            ing = scan.nextLine();
            if(ing.equals("Name:") || ing.equals("Main:") || ing.equals("Sub:"))
                ing = scan.nextLine();
            ings.add(ing);
            j++;
        }
        j = 0;
        for(int i = 0; i < ings.size(); i++){
            System.out.println("anan"+i);
            Query<Entity> query = Query.newEntityQueryBuilder()
                    .setKind("Ingredient")
                    .setFilter(CompositeFilter.and(
                            //PropertyFilter.eq("__key__", datastore.newKeyFactory().setKind("Ingredient").newKey(1))))
                            PropertyFilter.eq("name", ings.get(i))))
                    .build();
            Iterator<Entity> ents = datastore.run(query);
            while (ents.hasNext()) {
                j++;
            }
            System.out.println(j);
        }
        System.out.println(j);
    }
}
