package com.example.ex01_lab05;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    RecyclerView view;

    ArrayList<String> data = new ArrayList<>();

    MyAdapter adapter;

    boolean isSelectedAll = false;

    private boolean[] checkStates ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.view);

        int n = new Random().nextInt(13);

        for (int i = 0; i < n ; i++) {
            data.add("Item " + i);

        }
        adapter = new MyAdapter();

        view.setAdapter(adapter);

        view.setLayoutManager(new LinearLayoutManager(this));

        checkStates = new boolean[data.size()];



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.DAll:
                removeAll();
                break;
            case R.id.DChecked:
                deleteChecked();
                break;
            case R.id.SAll:
                selectAll();
            default:
                break;
        }
        return true;
    }

    private void selectAll() {
        isSelectedAll = true;

        adapter.notifyDataSetChanged();
    }

    private void deleteChecked() {
        ArrayList items_to_delete = new ArrayList<>();
        for (int i = 0; i < MainActivity.this.checkStates.length; ++i) {
            if (checkStates[i]){

                items_to_delete.add(data.get(i));
            }
        }

        data.removeAll(items_to_delete);

        adapter.notifyDataSetChanged();

        checkStates = new boolean[data.size()];


    }

    private void removeAll() {
        data.clear();
        adapter.notifyDataSetChanged();
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View childView = inflater.inflate(R.layout.item, parent, false);
            return new MyViewHolder(childView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            String item = data.get(holder.getAdapterPosition());
            holder.checkedTextView.setText(item);

            //check-all
            if (!isSelectedAll) {
                holder.checkedTextView.setChecked(false);
            }
            else{
                holder.checkedTextView.setChecked(true);

                holder.checkedTextView.setBackgroundColor(Color.RED);

                checkStates[holder.getAdapterPosition()] = true;
            }
            ///
              holder.checkedTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean value = holder.checkedTextView.isChecked();
                    if (value) {
                        // set check mark drawable and set checked property to false
                        holder.checkedTextView.setChecked(false);

                        Toast.makeText(MainActivity.this, "un-Checked", Toast.LENGTH_SHORT).show();

                        holder.checkedTextView.setBackgroundColor(Color.WHITE);

                       checkStates[holder.getAdapterPosition()] = false;

                    } else {
                        // set check mark drawable and set checked property to true
                        holder.checkedTextView.setChecked(true);

                        //Toast.makeText(MainActivity.this, "Checked", Toast.LENGTH_SHORT).show();

                        Toast.makeText(MainActivity.this, " "+holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();

                        holder.checkedTextView.setBackgroundColor(Color.RED);

                       checkStates[holder.getAdapterPosition()] = true;

                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
        public void selectAll(){
            Log.e("onClickSelectAll","yes");

            isSelectedAll = true;

            notifyDataSetChanged();
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckedTextView checkedTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkedTextView = itemView.findViewById(R.id.checkedTextView);
        }
    }
}