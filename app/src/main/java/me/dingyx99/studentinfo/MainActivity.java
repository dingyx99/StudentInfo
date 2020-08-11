package me.dingyx99.studentinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText et_class,et_id,et_name;
    ListView lv;
    Button bt_add;
    TextView textView;
    DBAdapter db = new DBAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        init();
        List<String> list= new ArrayList<String>();

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adp);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student stu = new Student();
                stu.Class = et_class.getText().toString();
                stu.Name = et_name.getText().toString();
                stu.Num = et_id.getText().toString();
                db.insert(stu);
                display();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,
                                           View view,
                                           final int position,
                                           long id) {
                PopupMenu menu = new PopupMenu(MainActivity.this,view);
                MenuInflater inflater = menu.getMenuInflater();
                inflater.inflate(R.menu.delete,menu.getMenu());
                if(position != 0) {
                    menu.show();
                }
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        ArrayAdapter<String> temp_adp = (ArrayAdapter<String>) lv.getAdapter();
                        String temp_s = temp_adp.getItem(position);
                        String[] temp_stu = temp_s.split(" ");
                        db.deleteOneData(Integer.valueOf(temp_stu[0]));
                        display();
                        return false;
                    }
                });
                return false;
            }
        });
        db.open();
        display();
    }

    void display() {
        Student[] stu;
        if(db != null) {
            stu = db.queryAllData();
            if(stu != null) {
                ArrayAdapter<String> temp_adp = (ArrayAdapter<String >) lv.getAdapter();
                temp_adp.clear();
                temp_adp.add("ID                      班级                      学号                      姓名");
                for(int i=0;i<stu.length;i++) {
                    temp_adp.add(stu[i].toString());
                }
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        db.close();
    }

    protected void init() {
        et_class=(EditText)findViewById(R.id.et_class);
        et_id=(EditText)findViewById(R.id.et_id);
        et_name=(EditText)findViewById(R.id.et_name);
        lv=(ListView)findViewById(R.id.lv);
        bt_add=(Button)findViewById(R.id.addButton);
        textView = new TextView(MainActivity.this);
    }

}