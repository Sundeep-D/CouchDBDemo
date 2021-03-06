package com.sundeep.rocksdb_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.MutableDocument;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    TextView text;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
        button = findViewById(R.id.button);

        // COUCH DATABASE LITE DEMO

        DatabaseManager dbMgr = DatabaseManager.getSharedInstance();
        dbMgr.initCouchbaseLite(getApplicationContext());
        dbMgr.openOrCreateDatabaseForUser(getApplicationContext(), "crado");

//        Manager manager = null;
//        try {
//            manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        DatabaseOptions options = new DatabaseOptions();
//        options.setCreate(true);
//        options.setStorageType(Manager.FORESTDB_STORAGE);
//        try {
//            JSONObject obj=new JSONObject(getDummyJson());
//            System.out.println("manager: "+manager);
//            Database database = manager.openDatabase("data", options);
//            Map<String, Object> properties=new HashMap<>();
//            properties.put("data-key",obj);
//            database.putLocalDocument("123",properties);
//
//
//            RevisionInternal received=database.getLocalDocument("123",null);
//            Map<String, Object> receivedMap=received.getProperties();
//            System.out.println("DATA:\n"+receivedMap.get("data-key"));
//        } catch (CouchbaseLiteException | JSONException e) {
//            e.printStackTrace();
//        }


                try {
            JSONObject obj=new JSONObject(getDummyJson());

            Map<String, Object> properties=new HashMap<>();
            properties.put("data",obj);
            //saveProfile(properties);
                    Database database = DatabaseManager.getDatabase();
            System.out.println("DATA SAVED:\n"+properties.get("data"));

        } catch ( JSONException e) {
            e.printStackTrace();
        }


    }

    public void saveProfile(Map<String,Object> profile) throws JSONException
    // end::saveProfile[]
    {
        Database database = DatabaseManager.getDatabase();

        String docId = DatabaseManager.getSharedInstance().getCurrentUserDocId();

        // tag::docset[]
        MutableDocument mutableDocument = new MutableDocument(docId, profile);
        // end::docset[]

        try {
            // tag::docsave[]
            database.save(mutableDocument);
            // end::docsave[]
        } catch (CouchbaseLiteException e ) {
            e.printStackTrace();
        }

    }

    public void fetchProfile()
    // end::fetchProfile[]
    {
        Database database = DatabaseManager.getDatabase();

        // tag::docfetch[]
        String docId = DatabaseManager.getSharedInstance().getCurrentUserDocId();

        if (database != null) {

            Map<String, Object> receivedData = new HashMap<>(); // <1>

            receivedData.put("id", DatabaseManager.getSharedInstance().currentUser); // <2>

            Document document = database.getDocument(docId); // <3>

            if (document != null) {
                receivedData.put("data", document.getValue("data")); // <4>
//                receivedData.put("address", document.getString("address")); // <4>
//                receivedData.put("imageData", document.getBlob("imageData")); // <4>
            }

            text.setText(receivedData.get("data")+"");
            System.out.println("ID: "+receivedData.get("id"));
            System.out.println("\nDATA: "+receivedData.get("data"));


        }
        // end::docfetch[]
    }

    public String getDummyJson(){
        return "{\n" +
                "  \"Generated by\": \"Crado\",\n" +
                "  \"RootFolderDriveId\": \"null\",\n" +
                "  \"Attention\": \"Note: Deleting or altering the data will result in unexpected behaviour of application.\",\n" +
                "  \"categories\": [\n" +
                "    \"HHH\"\n" +
                "  ],\n" +
                "  \"broadcasts\": [\n" +
                "    {\n" +
                "      \"id\": \"1604666998684\",\n" +
                "      \"name\": \"nznsn\",\n" +
                "      \"description\": \"\",\n" +
                "      \"category\": \"HHH\",\n" +
                "      \"isStatusBroadcaster\": true,\n" +
                "      \"workManagerID\": \"null\",\n" +
                "      \"remindMe\": false,\n" +
                "      \"reminderDetails\": {\n" +
                "        \"reminderMode\": \"Once\",\n" +
                "        \"reminderTime\": \"6:19:PM\",\n" +
                "        \"reminderDate\": \"06-11-2020\",\n" +
                "        \"reminderDays\": [\n" +
                "          \"Mon\",\n" +
                "          \"Tue\",\n" +
                "          \"Wed\",\n" +
                "          \"Thu\",\n" +
                "          \"Fri\"\n" +
                "        ]\n" +
                "      },\n" +
                "      \"isAutoBackupRequired\": false,\n" +
                "      \"isBackupUpToDate\": false,\n" +
                "      \"backupStatus\": \"yettobackup\",\n" +
                "      \"driveFolderID\": 12345,\n" +
                "      \"driveFolderPath\": \"null\",\n" +
                "      \"localFilePaths\": [],\n" +
                "      \"yetToBackupFilePaths\": []\n" +
                "    }\n" +
                "  ],\n" +
                "  \"FolderNames\": [],\n" +
                "  \"FolderDetailsHashmap\": {}\n" +
                "}";
    }

    public void getData(View view) {
        fetchProfile();
    }
}