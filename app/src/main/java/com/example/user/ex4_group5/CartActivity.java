package com.example.user.ex4_group5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CartActivity extends AppCompatActivity {
    private Order ord;
    ListView cartList;
    TextView price;
    CategoryItemsAdapter cartAdapter;
    private Button finishOrder;
    private Model DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        int rest_id = getIntent().getIntExtra("rest_id",-14);
        int tbl_id = getIntent().getIntExtra("tbl_id",-14);
        ord=Order.getInstance(rest_id , tbl_id);
        finishOrder=(Button) findViewById(R.id.orderButton);
        cartList = ((ListView) findViewById(R.id.cart_list));
        price = (TextView) findViewById(R.id.textViewTotalPrice);
        price.setText(Integer.toString(ord.sumItems(ord)));
        cartAdapter = new CategoryItemsAdapter(CartActivity.this, ord.getDesc());
        cartList.setAdapter(cartAdapter);
        DB=Model.getInstance(this);


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        cartList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemDescription item = cartAdapter.getItem(position);
                //ord = Order.getInstance(rest_id, tbl_id);
                ord.removeDesc(item);
                price.setText(" " +Integer.toString(ord.sumItems(ord)));
                cartAdapter.notifyDataSetChanged();
            }
        });

        finishOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Toast toast = Toast.makeText(getApplicationContext(), "Order as send to the restaurant", Toast.LENGTH_SHORT);
                toast.show();
                DB.addOrder(ord);
                ord.removeAllDesc();
                finish();
            }
        });


    }

}
