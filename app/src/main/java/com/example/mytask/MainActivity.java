package com.example.mytask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerview;
TextView cart_view;
//    Button view_cart;

    int totcount=0;
    CartListAdapter cartListAdapter;
    List<Productfile> productList=new ArrayList<>();
    SessionManager sessionManager;
    private List<Productfile> productfileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cart_view=findViewById(R.id.cart_view);
        recyclerview=findViewById(R.id.recyclerview);

        cart_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ViewCart.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().hide();

    }
    private void Productlistmethod() {
        if (productfileList!=null && productfileList.size()>0){
            cartListAdapter=new CartListAdapter(this,productfileList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerview.setLayoutManager(linearLayoutManager);
            recyclerview.setAdapter(cartListAdapter);
        }else {
            try {
                JSONObject obj = new JSONObject(loadJSONFromAsset(this));
                JSONArray jsonArray = obj.getJSONArray("users");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String pId = jsonObject.getString("PId");
                    String firstName = jsonObject.getString("firstName");
                    String lastName = jsonObject.getString("lastName");
                    String quantity = jsonObject.getString("quantity");
                    Integer price=jsonObject.getInt("price");
                    Productfile productfile = new Productfile();
                    productfile.setCount(0);
                    productfile.setDescription(lastName);
                    productfile.setName(firstName);
                    productfile.setQuanitiy(quantity);
                    productfile.setPid(pId);
                    productfile.setPrice(price);
                    Insertintolocal(productfile);
                    productList.add(productfile);
                  Log.e("productsize",productList.size()+"  ");

                }
                cartListAdapter = new CartListAdapter(this, productList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                recyclerview.setLayoutManager(linearLayoutManager);
                recyclerview.setAdapter(cartListAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getProductdetails() {
        class GetTasks extends AsyncTask<Void, Void, List<Productfile>> {

            @Override
            protected List<Productfile> doInBackground(Void... voids) {
               productfileList = DatabaseClient
                        .getInstance(MainActivity.this)
                        .getAppDatabase()
                        .taskDao()
                        .getAllProducts();
                return productfileList;
            }

            @Override
            protected void onPostExecute(List<Productfile> tasks) {
                super.onPostExecute(tasks);
                Productlistmethod();

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }


    private void Insertintolocal(final Productfile productfile) {
            class SaveTask extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {

                    DatabaseClient.getInstance(MainActivity.this).getAppDatabase()
                            .taskDao()
                            .insert(productfile);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                }
            }

            SaveTask st = new SaveTask();
            st.execute();

    }

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("sample_json.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void AddcartValue(String count, String action, Productfile productfile) {
        if (action.equalsIgnoreCase("add")){
            totcount= totcount+1;
            cart_view.setText("View Items("+String.valueOf(totcount)+")");

            StoredinLocal(action,productfile,totcount,count);

        }else if (action.equalsIgnoreCase("minus")) {
            if(count.equals("0")){
                totcount=0;
                StoredinLocal(action,productfile,totcount, count);
            }else

                totcount = totcount - 1;
                cart_view.setText("View Items("+String.valueOf(totcount)+")");
                StoredinLocal(action,productfile,totcount, count);

        }

    }

    private void StoredinLocal(String action, final Productfile productfile, final int totcount, final String count) {
        Log.e("StoredinLocal","Working ");
        Log.e("StoredinLocal",count+"  Working ");

        class UpdateTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .updateProduct(Integer.parseInt(count), productfile.getName(), productfile.getPid(),Integer.parseInt(count));
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

            }
        }
        UpdateTask st = new UpdateTask();
        st.execute();
    }


    @Override
    protected void onResume() {
        super.onResume();
        getProductdetails();



    }
}

