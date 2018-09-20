package com.example.serega.readerrss_feed.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.serega.readerrss_feed.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final int MENU_CHANGE = 1;
    final int MENU_DELETE = 2;

    ArrayAdapter<String> adapter;
    ArrayList<String> selectedRss = new ArrayList();
    ListView rssList;
    String rss;
    AdapterView.AdapterContextMenuInfo acmi;
    int pos;
    boolean i;

    public static final String LOG_TAG = "rss-reader";
    public static final String ACTION = "com.example.serega.readerrss_feed.list.Message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        rssList = (ListView) findViewById(R.id.listView);
        registerForContextMenu(rssList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        rssList.setAdapter(adapter);

        adapter.add("http://lenta.ru");
        adapter.add("http://www.old-hard.ru");

        rssList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent;
            rss = adapter.getItem(position);
            intent = new Intent(ACTION);
            intent.putExtra("rssString", adapter.getItem(position));
            startActivity(intent);
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.listView:
                menu.add(0, MENU_CHANGE, 0, "Изменить");
                menu.add(0, MENU_DELETE, 0, "Удалить");
                break;
        }
            super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        rss = adapter.getItem(acmi.position);
        switch (item.getItemId()) {

            case MENU_CHANGE:
                Toast.makeText(getApplicationContext(), "Изменить", Toast.LENGTH_SHORT).show();
                FragmentManager manager = getSupportFragmentManager();
                MyDialogFragment myDialogFragment = new MyDialogFragment();
                Bundle args = new Bundle();
                args.putString("rssAddress", rss);
                myDialogFragment.setArguments(args);
                myDialogFragment.show(manager, "dialog");
                break;
            case MENU_DELETE:
                adapter.remove(rss);
                selectedRss.clear();
                adapter.notifyDataSetChanged();
                i = true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("настройки");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void add(View view) {

        EditText rssEditText = (EditText) findViewById(R.id.editText);
        String rss = "http://" + rssEditText.getText().toString();
        if (!rss.equals("http://")){
            adapter.add(rss);
            rssEditText.setText("");
            adapter.notifyDataSetChanged();
        }
    }

    public void onChange(String s){

        adapter.remove(rss);
        adapter.insert(s, acmi.position);
        adapter.getItem(pos);
        selectedRss.clear();
        adapter.notifyDataSetChanged();
        i = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
