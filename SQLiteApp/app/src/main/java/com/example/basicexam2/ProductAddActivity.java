package com.example.basicexam2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ProductAddActivity extends AppCompatActivity {

    ProductDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);

        dao = ProductDAO.open(this);

        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("Add New Product") ;

        final EditText pname = findViewById(R.id.add_name);
        final TextView pprice = findViewById(R.id.add_price);

        final Spinner pmaker = findViewById((R.id.add_maker));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.makerlist, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pmaker.setAdapter(adapter);


        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product p = new Product();
                p.setName(pname.getText().toString());
                p.setMaker(pmaker.getSelectedItem().toString());
                p.setPrice(Integer.parseInt(pprice.getText().toString()));

                dao.addProduct(p);
                ((MainActivity)MainActivity.CONTEXT).onResume();
                finish();
            }
        });
    }
}
