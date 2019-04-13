package com.example.user.ex4_group5;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends Activity {
    ListView categoryList;
    CategoryItemsAdapter adapter;
    TextView price;
    private Order ord;
    TextView categoryNametxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        categoryList = ((ListView) findViewById(R.id.category_list));
        price = (TextView) findViewById(R.id.textViewTotalPrice);
        final int restId = getIntent().getIntExtra("rest_id", -14);
        final int tblId = getIntent().getIntExtra("tbl_id" , -14);
        String categoryName = getIntent().getStringExtra("category_name");
        categoryNametxt = (TextView) findViewById(R.id.categoryName);
        categoryNametxt.setText(categoryName);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Description");
        query.whereEqualTo("rest_id", restId);
        query.whereEqualTo("category", categoryName);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        List<ItemDescription> items = new ArrayList<ItemDescription>();
                        for (int i = 0; i < list.size(); i++) {
                            String itemName = list.get(i).getString("item");
                            String itemCategory = list.get(i).getString("category");
                            int price = list.get(i).getInt("price");
                            String desc = list.get(i).getString("desc");
                            items.add(new ItemDescription(itemName, desc, itemCategory, restId, price));
                        }

                        adapter = new CategoryItemsAdapter(CategoryActivity.this, items);
                        categoryList.setAdapter(adapter);
                    } else {
                        Toast.makeText(CategoryActivity.this, "No matching items were found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CategoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                price = (TextView) findViewById(R.id.textViewTotalPrice);
                ItemDescription item = adapter.getItem(position);
                ord = Order.getInstance(restId, tblId);
                ord.addDesc(item);
                final Toast toast = Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_SHORT);
                toast.show();
                Handler handler = new Handler();        //How set time for Toast to show
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 350);
            }
        });;
    }
}
