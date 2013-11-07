package com.example.mytodolist;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button btnEnter;
	ListView listToDo;
	EditText editTextNewItem;
	ArrayList<String> list = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnEnter = (Button) findViewById(R.id.btnEnter);
		listToDo = (ListView) findViewById(R.id.listView1);
		editTextNewItem = (EditText) findViewById(R.id.editTextNewItem);
		final ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
		
		
		listToDo.setAdapter(adp);
		
		btnEnter.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String newItem = editTextNewItem.getText().toString();
				adp.add(newItem);
				adp.notifyDataSetChanged();	
				editTextNewItem.setText("");
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
