package com.monkey.music.by.hk;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.*;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.media.MediaPlayer;
import android.net.*;
import android.os.*;
import android.os.Vibrator;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class MainActivity extends Activity {
	
	private Timer _timer = new Timer();
	
	private String list_json = "";
	private double song = 0;
	private String filepath = "";
	private boolean onSeek = false;
	private String song_duration = "";
	private double a = 0;
	private double n = 0;
	private String ed = "";
	private String item_json = "";
	private HashMap<String, Object> map_test = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> All_Song_Data = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> background_list = new ArrayList<>();
	private ArrayList<String> ImageMap = new ArrayList<>();
	
	private LinearLayout linear2;
	private LinearLayout linear7;
	private LinearLayout bg;
	private GridView listview1;
	private LinearLayout linear3;
	private ImageView imageview4;
	private LinearLayout linear27;
	private ImageView imageview6;
	private ImageView imageview5;
	private HorizontalScrollView bgs;
	private LinearLayout bg2;
	private LinearLayout i1;
	private LinearLayout i2;
	private LinearLayout i3;
	private LinearLayout i4;
	private LinearLayout linear26;
	private LinearLayout linear20;
	private LinearLayout linear24;
	private LinearLayout linear22;
	private TextView it1;
	private LinearLayout s1;
	private TextView it2;
	private LinearLayout s2;
	private TextView it3;
	private LinearLayout s3;
	private TextView it4;
	private LinearLayout s4;
	private TextView it5;
	private LinearLayout s5;
	private TextView it6;
	private LinearLayout s6;
	private TextView it7;
	private LinearLayout s7;
	private TextView it8;
	private LinearLayout s8;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private ImageView imageview7;
	private TextView time_current;
	private SeekBar seekbar1;
	private TextView time_duration;
	private ImageView imageview8;
	private ImageView imageview1;
	private ImageView imageview2;
	private ImageView imageview3;
	
	private MediaPlayer mp;
	private TimerTask timer;
	private Calendar calendar = Calendar.getInstance();
	private SharedPreferences sp;
	private Vibrator v;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= 23) {
			if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
			||checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
				requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
			} else {
				initializeLogic();
			}
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear2 = findViewById(R.id.linear2);
		linear7 = findViewById(R.id.linear7);
		bg = findViewById(R.id.bg);
		listview1 = findViewById(R.id.listview1);
		linear3 = findViewById(R.id.linear3);
		imageview4 = findViewById(R.id.imageview4);
		linear27 = findViewById(R.id.linear27);
		imageview6 = findViewById(R.id.imageview6);
		imageview5 = findViewById(R.id.imageview5);
		bgs = findViewById(R.id.bgs);
		bg2 = findViewById(R.id.bg2);
		i1 = findViewById(R.id.i1);
		i2 = findViewById(R.id.i2);
		i3 = findViewById(R.id.i3);
		i4 = findViewById(R.id.i4);
		linear26 = findViewById(R.id.linear26);
		linear20 = findViewById(R.id.linear20);
		linear24 = findViewById(R.id.linear24);
		linear22 = findViewById(R.id.linear22);
		it1 = findViewById(R.id.it1);
		s1 = findViewById(R.id.s1);
		it2 = findViewById(R.id.it2);
		s2 = findViewById(R.id.s2);
		it3 = findViewById(R.id.it3);
		s3 = findViewById(R.id.s3);
		it4 = findViewById(R.id.it4);
		s4 = findViewById(R.id.s4);
		it5 = findViewById(R.id.it5);
		s5 = findViewById(R.id.s5);
		it6 = findViewById(R.id.it6);
		s6 = findViewById(R.id.s6);
		it7 = findViewById(R.id.it7);
		s7 = findViewById(R.id.s7);
		it8 = findViewById(R.id.it8);
		s8 = findViewById(R.id.s8);
		linear5 = findViewById(R.id.linear5);
		linear6 = findViewById(R.id.linear6);
		imageview7 = findViewById(R.id.imageview7);
		time_current = findViewById(R.id.time_current);
		seekbar1 = findViewById(R.id.seekbar1);
		time_duration = findViewById(R.id.time_duration);
		imageview8 = findViewById(R.id.imageview8);
		imageview1 = findViewById(R.id.imageview1);
		imageview2 = findViewById(R.id.imageview2);
		imageview3 = findViewById(R.id.imageview3);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				v.vibrate((long)(30));
				song = _position;
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				if(mp == null){
					mp=new MediaPlayer();
				}else{
					mp.pause();
					mp.reset();
				}
				filepath = All_Song_Data.get((int)_position).get("data").toString();
				try {
					if (mp.isPlaying()) {
						mp.reset();
						//mp.prepare();
						mp.prepareAsync();
					}else{
						mp.setDataSource(filepath);
						mp.prepare();
						mp.start();
						_extra();
					}
				} catch (java.io.IOException e) { }
				imageview2.setImageResource(R.drawable.ic_pause_white);
			}
		});
		
		i1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				s1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFF000000));
				s2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, Color.TRANSPARENT));
				s3.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, Color.TRANSPARENT));
				s4.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, Color.TRANSPARENT));
				s5.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, Color.TRANSPARENT));
				s6.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, Color.TRANSPARENT));
				s7.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, Color.TRANSPARENT));
				s8.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, Color.TRANSPARENT));
				it1.setTextColor(0xFF000000);
				it2.setTextColor(0xFF407B48);
				it3.setTextColor(0xFF407B48);
				it4.setTextColor(0xFF407B48);
				it5.setTextColor(0xFF407B48);
				it6.setTextColor(0xFF407B48);
				it7.setTextColor(0xFF407B48);
				it8.setTextColor(0xFF407B48);
			}
		});
		
		i2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				s1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, Color.TRANSPARENT));
				s2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFF000000));
				s3.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, Color.TRANSPARENT));
				s4.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, Color.TRANSPARENT));
				s5.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, Color.TRANSPARENT));
				s6.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, Color.TRANSPARENT));
				s7.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, Color.TRANSPARENT));
				s8.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, Color.TRANSPARENT));
				it1.setTextColor(0xFF407B48);
				it2.setTextColor(0xFF000000);
				it3.setTextColor(0xFF407B48);
				it4.setTextColor(0xFF407B48);
				it5.setTextColor(0xFF407B48);
				it6.setTextColor(0xFF407B48);
				it7.setTextColor(0xFF407B48);
				it8.setTextColor(0xFF407B48);
			}
		});
		
		seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;
				if (mp != null){
					if (onSeek) {
						if (mp.isPlaying()) {
							mp.pause();
							mp.seekTo((int)(_progressValue));
							imageview2.setImageResource(R.drawable.ic_pause_white);
						} else {
							mp.pause();
							mp.seekTo((int)(_progressValue));
							imageview2.setImageResource(R.drawable.ic_pause_white);
						}
					}
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar _param1) {
				if (mp != null){
					onSeek = true;
				}
				v.vibrate((long)(30));
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar _param2) {
				if (mp != null){
					onSeek = false;
					if (mp.isPlaying()) {
						
					} else {
						mp.start();
					}
				}
			}
		});
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				v.vibrate((long)(30));
				if(mp == null){
					mp=new MediaPlayer();
				}else{
					mp.pause();
					mp.reset();
					if (All_Song_Data.size() == 0) {
						
					} else {
						song--;
						if (song > -1) {
							filepath = All_Song_Data.get((int)song).get("data").toString();
							try {
								if (mp.isPlaying()) {
									mp.reset();
									mp.prepare();
								}else{
									mp.setDataSource(filepath);
									mp.prepare();
									mp.start();
								}
							} catch (Exception e) {
							}
							((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
							_extra();
						} else {
							song = 0;
						}
					}
				}
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				v.vibrate((long)(30));
				if(mp != null){
					if (mp.isPlaying()) {
						mp.pause();
						imageview2.setImageResource(R.drawable.icon_play_arrow_round);
					} else {
						mp.start();
						imageview2.setImageResource(R.drawable.ic_pause_white);
					}
				}
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				v.vibrate((long)(30));
				if(mp == null){
					mp=new MediaPlayer();
				}else{
					mp.pause();
					mp.reset();
					if (All_Song_Data.size() == 0) {
						
					} else {
						song++;
						if (song < All_Song_Data.size()) {
							filepath = All_Song_Data.get((int)song).get("data").toString();
							try {
								if (mp.isPlaying()) {
									mp.reset();
									mp.prepare();
								}else{
									mp.setDataSource(filepath);
									mp.prepare();
									mp.start();
								}
							} catch (Exception e) {
							}
							((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
							_extra();
						} else {
							song = All_Song_Data.size();
						}
					}
				}
			}
		});
	}
	
	private void initializeLogic() {
		_access_files();
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS); getWindow().setStatusBarColor(Color.TRANSPARENT);
		AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(
		AudioManager.STREAM_MUSIC,
		audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
		0
		);
		imageview1.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()){
					case MotionEvent.ACTION_DOWN:{
						ObjectAnimator scaleX = new ObjectAnimator();
						scaleX.setTarget(imageview1);
						scaleX.setPropertyName("scaleX");
						scaleX.setFloatValues(0.9f);
						scaleX.setDuration(100);
						scaleX.start();
						
						ObjectAnimator scaleY = new ObjectAnimator();
						scaleY.setTarget(imageview1);
						scaleY.setPropertyName("scaleY");
						scaleY.setFloatValues(0.9f);
						scaleY.setDuration(100);
						scaleY.start();
						break;
					}
					case MotionEvent.ACTION_UP:{
						
						ObjectAnimator scaleX = new ObjectAnimator();
						scaleX.setTarget(imageview1);
						scaleX.setPropertyName("scaleX");
						scaleX.setFloatValues((float)1);
						scaleX.setDuration(100);
						scaleX.start();
						
						ObjectAnimator scaleY = new ObjectAnimator();
						scaleY.setTarget(imageview1);
						scaleY.setPropertyName("scaleY");
						scaleY.setFloatValues((float)1);
						scaleY.setDuration(100);
						scaleY.start();
						
						break;
					}
				}
				return false;
			}
		});
		imageview2.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()){
					case MotionEvent.ACTION_DOWN:{
						ObjectAnimator scaleX = new ObjectAnimator();
						scaleX.setTarget(imageview2);
						scaleX.setPropertyName("scaleX");
						scaleX.setFloatValues(0.9f);
						scaleX.setDuration(100);
						scaleX.start();
						
						ObjectAnimator scaleY = new ObjectAnimator();
						scaleY.setTarget(imageview2);
						scaleY.setPropertyName("scaleY");
						scaleY.setFloatValues(0.9f);
						scaleY.setDuration(100);
						scaleY.start();
						break;
					}
					case MotionEvent.ACTION_UP:{
						
						ObjectAnimator scaleX = new ObjectAnimator();
						scaleX.setTarget(imageview2);
						scaleX.setPropertyName("scaleX");
						scaleX.setFloatValues((float)1);
						scaleX.setDuration(100);
						scaleX.start();
						
						ObjectAnimator scaleY = new ObjectAnimator();
						scaleY.setTarget(imageview2);
						scaleY.setPropertyName("scaleY");
						scaleY.setFloatValues((float)1);
						scaleY.setDuration(100);
						scaleY.start();
						
						break;
					}
				}
				return false;
			}
		});
		imageview3.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()){
					case MotionEvent.ACTION_DOWN:{
						ObjectAnimator scaleX = new ObjectAnimator();
						scaleX.setTarget(imageview3);
						scaleX.setPropertyName("scaleX");
						scaleX.setFloatValues(0.9f);
						scaleX.setDuration(100);
						scaleX.start();
						
						ObjectAnimator scaleY = new ObjectAnimator();
						scaleY.setTarget(imageview3);
						scaleY.setPropertyName("scaleY");
						scaleY.setFloatValues(0.9f);
						scaleY.setDuration(100);
						scaleY.start();
						break;
					}
					case MotionEvent.ACTION_UP:{
						
						ObjectAnimator scaleX = new ObjectAnimator();
						scaleX.setTarget(imageview3);
						scaleX.setPropertyName("scaleX");
						scaleX.setFloatValues((float)1);
						scaleX.setDuration(100);
						scaleX.start();
						
						ObjectAnimator scaleY = new ObjectAnimator();
						scaleY.setTarget(imageview3);
						scaleY.setPropertyName("scaleY");
						scaleY.setFloatValues((float)1);
						scaleY.setDuration(100);
						scaleY.start();
						
						break;
					}
				}
				return false;
			}
		});
		getAllSongData();
		_removeScollBar(bgs);
		s2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFF407B48));
		it2.setTextColor(0xFF407B48);
		seekbar1.getProgressDrawable().setColorFilter(Color.parseColor("#FF407B48"), PorterDuff.Mode.SRC_IN);
	}
	
	public void _access_files() {
		list_json = new Gson().toJson(All_Song_Data);
		background_list = new Gson().fromJson(list_json, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		/*
FileUtil.writeFile(FileUtil.getExternalStorageDir(), FileUtil.getExternalStorageDir());
*/
		listview1.setAdapter(new Listview1Adapter(All_Song_Data));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
	}
	
	
	public void _getFileList() {
	}
	public void getAllSongData() { 
		
		String[] projection = {
			
			android.provider.MediaStore.Audio.Media._ID, 
			android.provider.MediaStore.Audio.Media.ALBUM,
			android.provider.MediaStore.Audio.Media.ALBUM_KEY, 
			android.provider.MediaStore.Audio.Media.ARTIST,
			android.provider.MediaStore.Audio.Media.DATA,
			android.provider.MediaStore.Audio.Media.TITLE,
			android.provider.MediaStore.Audio.Media.DURATION,
			
			android.provider.MediaStore.Audio.Media.ALBUM_ID,
			/*
android.provider.MediaStore.Audio.Albums.ALBUM_ART;
*/
		};
		
		String orderBy = " " + android.provider.MediaStore.MediaColumns.DISPLAY_NAME;
		
		android.net.Uri uri = android.provider.MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI; 
		
		cursor = getApplicationContext().getContentResolver().query(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, orderBy);
		getAlbumColumnData(cursor);
	} 
	{
	}
	public static android.database.Cursor cursor;
	public static int music_column_index;
	
	private void getAlbumColumnData(android.database.Cursor cur) {
		try {
			if (cur.moveToFirst()) {
				String id;
				String name;
				String data;
				String artist;
				String album;
				String songs_duration;
				
				do {
					
					id = cur.getString(cur.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.ALBUM_ID));
					
					name = cur.getString(cur.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.TITLE));
					data = cur.getString(cur.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.DATA));
					
					artist = cur.getString(cur.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.ARTIST));
					
					album = cur.getString(cur.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.ALBUM));
					
					{
						HashMap<String, Object> _item = new HashMap<>();
						_item.put("album", album);
						_item.put("name", name);
						_item.put("data", data);
						_item.put("artist", artist);
						_item.put ("id",id);
						All_Song_Data.add( _item);
					}
				} while (cur.moveToNext());}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	{
	}
	private String setCorrectDuration(String songs_duration) {
		// TODO Auto-generated method stub
		
		if(Integer.valueOf(songs_duration) != null) {
			int time = Integer.valueOf(songs_duration);
			
			int seconds = time/1000;
			int minutes = seconds/60;
			seconds = seconds % 60;
			
			if(seconds<10) {
				songs_duration = String.valueOf(minutes) + ":0" + String.valueOf(seconds);
				song_duration = songs_duration;
			} else {
				songs_duration = String.valueOf(minutes) + ":" + String.valueOf(seconds);
				song_duration = songs_duration;
			}
			return songs_duration;
		}
		return null;
	}
	{
	}
	
	
	public void _extra() {
		System.runFinalization();
		Runtime.getRuntime().gc();
		System.gc();
		if (mp != null){
			calendar.setTimeInMillis((long)(mp.getDuration()));
			time_duration.setText(new SimpleDateFormat("mm:ss").format(calendar.getTime()));
			seekbar1.setMax((int)mp.getDuration());
			timer = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							calendar.setTimeInMillis((long)(mp.getCurrentPosition()));
							time_current.setText(new SimpleDateFormat("mm:ss").format(calendar.getTime()));
							seekbar1.setProgress((int)mp.getCurrentPosition());
						}
					});
				}
			};
			_timer.scheduleAtFixedRate(timer, (int)(400), (int)(400));
		}else {
			SketchwareUtil.showMessage(getApplicationContext(), "Not: Media is not found expention");
		}
		mp.setOnCompletionListener (new MediaPlayer.OnCompletionListener (
		) {
			public void
			onCompletion (MediaPlayer theMediaPlayer) {
				if(mp == null){
					mp=new MediaPlayer();
				}else{
					mp.pause();
					mp.reset();
					if (!(All_Song_Data.size() == 0)) {
						song++;
						if (song < All_Song_Data.size()) {
							filepath = All_Song_Data.get((int)song).get("data").toString();
							try {
								if (mp.isPlaying()) {
									mp.reset();
									mp.prepare();
								}else{
									mp.setDataSource(filepath);
									mp.prepare();
									mp.start();
								}
							} catch (Exception e) {
							}
							((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
							_extra();
						} else {
							song = All_Song_Data.size();
						}
					}
				}
			}});
		mp.setOnErrorListener(new MediaPlayer.OnErrorListener(){
			
			@Override
			public boolean onError(MediaPlayer p1, int p2, int p3)
			{
				if (!(All_Song_Data.size() == 0)) {
					if (!((song == All_Song_Data.size()) || (song == 0))) {
						SketchwareUtil.showMessage(getApplicationContext(), "Error");
					} else {
						
					}
				}
				mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
					@Override
					public void onPrepared(MediaPlayer mp){
					}
				});
				return true;
			}
		});
	}
	
	
	public void _removeScollBar(final View _view) {
		try {
			_view.setVerticalScrollBarEnabled(false); _view.setHorizontalScrollBarEnabled(false);
		} catch (Throwable e) {
			
		}
	}
	
	public class Listview1Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.muse, null);
			}
			
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final TextView song_name = _view.findViewById(R.id.song_name);
			final TextView song_artist = _view.findViewById(R.id.song_artist);
			
			if (_data.size() > 0) {
				if (_data.get((int)_position).containsKey("name")) {
					song_name.setText(_data.get((int)_position).get("name").toString());
				}
				if (_data.get((int)_position).containsKey("artist")) {
					song_artist.setText(_data.get((int)_position).get("artist").toString());
				}
				if (song == _position) {
					song_name.setTextColor(0xFF000000);
				} else {
					song_name.setTextColor(0xFF000000);
				}
			}
			
			return _view;
		}
	}
}