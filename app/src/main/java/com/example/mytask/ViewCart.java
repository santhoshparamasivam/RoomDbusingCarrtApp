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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ViewCart extends AppCompatActivity {
    RecyclerView recyclerview;
    TextView cart_view;
//    Button view_cart;
    int totprice=0;
    int totcount=0;
    int finalcount=0;
    int finalprice=0;
    ViewListAdapter cartListAdapter;
    List<Productfile> productList=new ArrayList<>();
    SessionManager sessionManager;
    private List<Productfile> productfileList;
    RadioButton Drivein,Away;
    ImageView back;
    TextView showmore,cost,nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        getSupportActionBar().hide();
        cart_view=findViewById(R.id.cart_view);
        recyclerview=findViewById(R.id.recyclerview);
        Away=findViewById(R.id.away);
        Drivein=findViewById(R.id.drivein);
        showmore=findViewById(R.id.showmore);
        cost=findViewById(R.id.cost);
        back=findViewById(R.id.back);
        nodata=findViewById(R.id.nodata);
        getSupportActionBar().hide();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Away.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Away.setChecked(true);
                Drivein.setChecked(false);
            }
        });
        Drivein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drivein.setChecked(true);
                Away.setChecked(false);
            }
        });

    showmore.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int size=productList.size();
            Adaptermethod(productList,size);
            showmore.setVisibility(View.GONE);

        }
    });
    }



    private void Adaptermethod(List<Productfile> productList, int value) {
        totprice=0;
        finalprice=0;
        if (productList.size()<=2){
            showmore.setVisibility(View.GONE);
        }else if (productList.size()>2){
            showmore.setVisibility(View.VISIBLE);
        }
        List<Productfile>listofproducts=new ArrayList<>();

        for (int i=0;i<productList.size();i++){
            if (productList.get(i).getCount()!=0) {
                if (listofproducts.size()<value) {
                    Productfile product = new Productfile();
                    product.setPid(productList.get(i).getPid());
                    product.setCount(productList.get(i).getCount());
                    product.setDescription(productList.get(i).getDescription());
                    product.setName(productList.get(i).getName());
                    product.setRegid(productList.get(i).getRegid());
                    product.setQuanitiy(productList.get(i).getQuanitiy());
                    product.setPrice(productList.get(i).getPrice());
                    listofproducts.add(product);
                    int price=productList.get(i).getPrice()*Integer.parseInt(productList.get(i).getQuanitiy());
                    finalcount=finalcount+product.getCount();
                    finalprice=finalprice+price;
                    totprice=finalprice;
                    Log.e("final price",finalprice+" ");
                }
            }
        }
        cost.setText("Rs "+String.valueOf(finalprice));
            cartListAdapter = new ViewListAdapter(this, listofproducts);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerview.setLayoutManager(linearLayoutManager);
            recyclerview.setAdapter(cartListAdapter);
    }

    private void getProductdetails() {
        class GetTasks extends AsyncTask<Void, Void, List<Productfile>> {

            @Override
            protected List<Productfile> doInBackground(Void... voids) {
                productfileList = DatabaseClient
                        .getInstance(ViewCart.this)
                        .getAppDatabase()
                        .taskDao()
                        .getAllProducts();
                return productfileList;
            }

            @Override
            protected void onPostExecute(List<Productfile> tasks) {
                super.onPostExecute(tasks);
                Log.e("productfilelist", productfileList.size() + " ");
                if (productfileList != null && productfileList.size() > 0) {

                    for (int i = 0; i < productfileList.size(); i++) {
                        if (productfileList.get(i).getCount() != 0) {
                                        Productfile product = new Productfile();
                                        product.setPid(productfileList.get(i).getPid());
                                        product.setCount(productfileList.get(i).getCount());
                                        product.setDescription(productfileList.get(i).getDescription());
                                        product.setName(productfileList.get(i).getName());
                                        product.setRegid(productfileList.get(i).getRegid());
                                        product.setQuanitiy(productfileList.get(i).getQuanitiy());
                                        product.setPrice(productfileList.get(i).getPrice());
                                        productList.add(product);
                                    }


                        }

                    Adaptermethod(productList,2);
                }
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }



    public void AddcartValue(String count, String action, Productfile productfile) {

        if (action.equalsIgnoreCase("add")){
           int price=Integer.parseInt("1")*productfile.getPrice();
           totprice=totprice+price;
            cost.setText("Rs "+String.valueOf(totprice));
            StoredinLocal(action,productfile,totcount,count);
        }else if (action.equalsIgnoreCase("minus")) {
            if (count.equals("0")) {
                totcount=0;
              int  price=Integer.parseInt("1")*productfile.getPrice();
                totprice=totprice-price;
                cost.setText("Rs "+String.valueOf(totprice));
                StoredinLocal(action, productfile, totcount, count);
            } else {
                int price=Integer.parseInt("1")*productfile.getPrice();
                totprice=totprice-price ;
                cost.setText("Rs "+String.valueOf(totprice));
                StoredinLocal(action, productfile, totcount, count);
            }
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


