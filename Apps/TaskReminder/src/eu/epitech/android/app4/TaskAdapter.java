package eu.epitech.android.app4;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TaskAdapter extends BaseAdapter {
	ArrayList<Task> array = new ArrayList<Task>();
	Context context = null;
	private static final String FILENAME = "data.json";
	
	public TaskAdapter(Context context) {
		this.context = context;
	}
	
	public void load() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput(FILENAME)));
			String res = "";
			String line = null;
			try {
				while (( line = br.readLine()) != null) {
					res += line;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				JSONArray jarray = new JSONArray(res);
				array = new ArrayList<Task>();
				for (int i = 0; i < jarray.length(); i++) {
					JSONObject o = jarray.getJSONObject(i);
					Task t = new Task(o.getString("Name"));
					array.add(t);
				}
				notifyDataSetChanged();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {
			
			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			JSONArray jarray = new JSONArray();
			for (Task t : array) {
				jarray.put(t.getJSONObject());
			}
			fos.write(jarray.toString().getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int getCount() {
		return array.size();
	}

	@Override
	public Object getItem(int arg0) {
		return array.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		LayoutInflater li  = LayoutInflater.from(context);
		View v = li.inflate(android.R.layout.simple_list_item_1, null);
		TextView tv = (TextView)v.findViewById(android.R.id.text1);
		
		Task task = (Task) getItem(arg0);
		tv.setText(task.getName());
		return v;
	}
	
	public void remove(int pos) {
		array.remove(pos);
		notifyDataSetChanged();
	}
	
	public void add(Task t) {
		array.add(t);
		notifyDataSetChanged();
	}

}
