package com.example.user.ex4_group5;

        import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

        import org.json.JSONException;

        import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 8/22/2015.
 */

public class Model {

    DatabaseHelper myDb;

    // class member
    private static Model instance;

    // private constructor

    private Model(Context context) {
        init(context);
    }

    //public accessor
    public static Model getInstance(Context context){
        if (instance == null) {
            instance = new Model(context);
        }
        return instance;
    }

    private void init(Context context) {

        // Enable Local Datastore.
       // Parse.enableLocalDatastore(context);
//
//       // Parse.initialize(context, "H4UDwOOjUqSpioDUv5Jl6fEZa7Qu3X2HRcarwoSi", "KPAMLvFbeDmmd5UuoSmTWCw9y7wihnfLpw6fa2MS");
//        Log.d("Model", "Initializing DB ");
//
//      //  Parse.initialize(context, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
//        Parse.initialize(context, "Nw3fc9Vo3qrPzZhxWs36wtDi0kdF5XzvJMJtpfaj", "NyKv7Q44TMXTDcQaO97cK3qYXz9C4k98zPyap6Fw");
    }

    public void addOrder(Order ord) {

        Log.d("HY", "addOrderLog" + ord);

        ParseObject  newOrder = OrderToJson(ord);
      //  newOrder.saveInBackground();

        try {
            newOrder.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public  Restaurant jsonToRestaurant(ParseObject p){
        Log.d("HY", "Model - jsonTo" + r);
        Restaurant r= new  Restaurant(p.getInt("rest_id"),p.getString("rest_name"),p.getJSONArray("category"),p.getString("description"));
        return r;
    }
    public ParseObject OrderToJson(Order ord){
        ParseObject values = new ParseObject("Order");
        ArrayList<String> itemsDesc = new ArrayList<String>();
        List<ItemDescription> products=ord.getDesc();
        for (int i = 0; i < products.size(); i++) {
            itemsDesc.add(products.get(i).getItemName());
        }
        values.put("rest_id", ord.getRestId());
        values.put("table_num", ord.getTblID());
        values.put("items_order", itemsDesc);

        return values;
    }


    public ArrayList<Restaurant> getAllRestaurants(){
        Log.d("HY", "Model - Getting all Restaurant");
        ArrayList<Restaurant> Restaurant = new ArrayList<Restaurant>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Restaurant");
        try{
            List<ParseObject> objects=query.find() ;
            if(objects!=null){
                Log.d("HY", "Model - Getting all Restaurant - done (), objects.size()=" +objects.size() );

                for(ParseObject o: objects){
                    Restaurant.add(jsonToRestaurant(o));
                }
                Log.d("HY", "Model - after coversion Restaurant.size()=" +Restaurant.size());
            }
        }
        catch(ParseException e){
            e.printStackTrace();
            Log.e("HY", "Model - query.find() exeption"+e.toString());
        }
        Log.d("HY", "Model - Getting all Restaurant finished");
        // This delay is added to demonstrate how the UI looks in cases of slow download
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Restaurant;
    }

    /////////////CallBack Interface //////////////////
   // interface GetAllClbck{
 //       public void done(List<Restaurant> Restaurant);
  //  }

    /////////////Find In Background Operation with CallBack//////////////////

    public void getAllRestaurants(GetAllClbck clbck){
        final GetAllClbck getAllListener=clbck;

        Log.d("HY", "Model - Getting all Restaurant");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Restaurant");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Log.d("HY", "Model - Getting all Restaurant - done ()");
                List<Restaurant> res = new ArrayList<Restaurant>();
                for (ParseObject o : objects) {

                    res.add(jsonToRestaurant(o));
                }
                getAllListener.done(res);
                Log.d("HY", "Model after getAllListener.done()");
            }

        });
        Log.d("HY", "Model Getting all Restaurant -after findInBackground ()");

    }
    Restaurant r;
    public void getRestaurantById(int id, final RestByIdCallback callback){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurant");
        query.whereEqualTo("rest_id", id);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> RestaurantList, ParseException e) {
                if (e == null) {
                    Log.d("HY", "Model.getRestaurantById Retrieved " + RestaurantList.size() + " Restaurant");
                    r = jsonToRestaurant(RestaurantList.get(0));
                    callback.doneById(r);
                } else {
                    Log.d("HY", "Model.getRestaurantById Error: " + e.getMessage());
                }
            }
        });
    }


    public void deleteRestaurant(int id){
        Log.d("HY", "Model.deleteRestaurant index= " + id);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurant");
        query.whereEqualTo("index", id);
        try{
            List<ParseObject> RestaurantsList=query.find();
            if (RestaurantsList.size()>0) {
                Log.d("HY", "Model.deleteRestaurant Retrieved " + RestaurantsList.size() + " students");
                RestaurantsList.get(0).deleteInBackground();

            }
        }
        catch (ParseException e1) {
            e1.printStackTrace();
            Log.e("HY", "Model.deleteRestaurant Error: " + e1.getMessage());
        }


    }



    final int version = 1;

    class DatabaseHelper extends SQLiteOpenHelper {


        public DatabaseHelper(Context context){

            super(context, "datamodel.db", null , version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table Restaurant (_id INTEGER PRIMARY KEY, name TEXT,phone TEXT, Address TEXT, Description TEXT, veg INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
            db.execSQL("DROP TABLE IF EXISTS Restaurant ;");
            db.execSQL("create table Restaurant (_id INTEGER PRIMARY KEY, name TEXT,phone TEXT,Address TEXT, Description TEXT, veg INTEGER);");
        }
    }

    public void editRestaurant(Restaurant r) {
        final Restaurant res = r;
        ParseObject RestaurantToEdit = null;
        Log.d("HY", "Model.editRestaurant rest_id= " + res.getRestId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurant");
        query.whereEqualTo("rest_id", r.getRestId());
        try {
            List<ParseObject> RestaurantsList = query.find();
            if (RestaurantsList.size() > 0) {
                Log.d("HY", "Model.editRestaurant Retrieved " + RestaurantsList.size() + " students"); //remember to chance the word students
                RestaurantToEdit = RestaurantsList.get(0);
                RestaurantToEdit.put("rest_id", res.getRestId());
                RestaurantToEdit.put("rest_name", res.getRestName());
                RestaurantToEdit.put("category", res.getCategory());
                RestaurantToEdit.put("description", res.getDescription());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        RestaurantToEdit.saveInBackground();
    }

        public static int getSize(){
            ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>("Restaurant");
            int x=0;
            try{
                List<ParseObject> objects=query1.find() ;
                x=objects.size();
            }
            catch(ParseException e){
                e.printStackTrace();
                Log.e("HY", "Model - query.find() exeption" + e.toString());
            }
           return x+1;
        }

    }
