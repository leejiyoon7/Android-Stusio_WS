package com.example.exam3_1;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.TextView;

public class SubActivity extends AppCompatActivity {
    TextView subTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        subTxt = findViewById(R.id.subTxt);

        Intent intent = getIntent();
        subTxt.setText(intent.getStringExtra(intent.EXTRA_TEXT));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Back");
    }

    public void closeBtnClick(View v) {
        finish();
    }
}

