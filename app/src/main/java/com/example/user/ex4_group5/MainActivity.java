package com.example.user.ex4_group5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements RestByIdCallback {

    private int tbl_id;
    private Button cart;
    TextView restName;
    private ListView lv;
    public static ArrayList<Restaurant> RestaurantList = new ArrayList<Restaurant>();
    //private CustomAdapter adapter;
    private Model DB;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO remember to put back the intent
        Intent i = getIntent();
        String rest_tbl = getIntent().getStringExtra("rest_table");
        //String rest_tbl = "3_10";
        final int rest_id = Integer.parseInt(rest_tbl.substring(0, rest_tbl.indexOf('_')));
        tbl_id = Integer.parseInt(rest_tbl.substring(rest_tbl.indexOf('_') + 1));
        Toast.makeText(this,Integer.toString(rest_id),Toast.LENGTH_SHORT).show();
        Toast.makeText(this,Integer.toString(tbl_id),Toast.LENGTH_SHORT).show();
        //int tbl_id = 18;
      //  int rest_id = 2;
        DB = Model.getInstance(this);
        DB.getRestaurantById(rest_id, this);
        cart=(Button) findViewById(R.id.cart);
        restName = (TextView) findViewById(R.id.restName);
        ArrayList<Restaurant> rest =  DB.getAllRestaurants();
        for (Restaurant r : rest){          //maybe can be more efficient
            if (r.getRestId() == rest_id)
                restName.setText(r.getRestName());
        }



        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,CartActivity.class);
                i.putExtra("rest_id" , rest_id);
                i.putExtra("tbl_id" , tbl_id);
                startActivity(i);
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("HY", " onActivityResult back from edit ");
        super.onActivityResult(requestCode, resultCode, data);
        int position = requestCode;
        if (resultCode == RESULT_OK) {
            Bundle results = data.getExtras();
            Log.d("HY", " results ID = " + results.getInt("ID"));

            String aggg = results.getString("category");
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(aggg);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Restaurant res = new Restaurant(results.getInt("rest_id"), results.getString("rest_name"),
                    jsonArray, results.getString("description"));
            RestaurantList.set(position, res);
            //  adapter.notifyDataSetChanged();;
        } else {
            //Write your code if there's no result
            Log.d("HY", " no results from edit ");

        }

    }//onActivityResult

    private void refreshData() {
        //		///without callback - may take a long time and stop the UI thread
        //		studentList=DB.getAllStudents();
        //		adapter.notifyDataSetChanged();

        //with callback - background operation doesn't stop the UI thread


        DB.getAllRestaurants(new GetAllClbck() {
            @Override
            public void done(List<Restaurant> Restaurants) {
                RestaurantList = (ArrayList<Restaurant>) Restaurants;
                //  adapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("HY", "MainActivity  onResume");
        refreshData();

    }

    @Override
    public void doneById(final Restaurant rest) {

        JSONArray js = rest.getCategory();

        // getLocalData();

        //   RestaurantList= DB.getAllRestaurants();

        String[] categories = new String[js.length()];
        for (int i = 0; i < js.length(); i++) {
            try {
                categories[i] = String.valueOf(js.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        lv = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, categories);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String categoryName = adapter.getItem(position);
                int restId = rest.getRestId();
                Intent intent = new Intent(MainActivity.this,CategoryActivity.class);
                intent.putExtra("rest_id",restId);
                intent.putExtra("category_name",categoryName);
                intent.putExtra("tbl_id" , tbl_id);
                startActivity(intent);
            }
        });
    }


//    class CustomAdapter extends BaseAdapter {
//
//        private LayoutInflater inflater;
//
//        public CustomAdapter(){
//            inflater = LayoutInflater.from(getApplicationContext());
//        }
//
//        @Override
//        public int getCount() {
//            return RestaurantList.size();
//        }
//
//        @Override
//        public Object getItem(int arg0) {
//            return RestaurantList.get(arg0);
//        }
//
//        @Override
//        public long getItemId(int arg0) {
//            return arg0;
//        }
//
//    }


}
