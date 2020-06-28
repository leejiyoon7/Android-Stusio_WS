package com.example.basicexam2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ProductAdapter extends ArrayAdapter {
    Context context;
    int resId;
    List<Product> products;

    public ProductAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resId = resource;
        this.products = objects;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final int pos = position;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resId, null);
        }

        final Product product = products.get(position);
        TextView productName = convertView.findViewById(R.id.productName);
        TextView productMaker = convertView.findViewById(R.id.productMaker);
        TextView productPrice = convertView.findViewById(R.id.productPrice);
        ImageView menu = convertView.findViewById(R.id.menu);

        productName.setText(product.getName());
        productMaker.setText(product.getMaker());
        productPrice.setText(String.valueOf(product.getPrice()));

        menu.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        PopupMenu popup = new PopupMenu(context.getApplicationContext(), v);
                                        popup.getMenuInflater().inflate(R.menu.item_menu,
                                                popup.getMenu());
                                        popup.show();
                                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                            @Override
                                            public boolean onMenuItemClick(MenuItem item) {

                                                switch (item.getItemId()) {
                                                    case R.id.edit:

                                                        //Or Some other code you want to put here.. This is just an example.
                                                        Toast.makeText(context.getApplicationContext(), " Edit Clicked at position " + " : " + pos, Toast.LENGTH_LONG).show();

                                                        break;
                                                    case R.id.delete:

                                                        Toast.makeText(context.getApplicationContext(), " Delete Clicked at position " + " : " + pos, Toast.LENGTH_LONG).show();

                                                        break;

                                                    default:
                                                        break;
                                                }

                                                return true;
                                            }
                                        });
                                    }
                                }
        );
        return convertView;
    }
}
