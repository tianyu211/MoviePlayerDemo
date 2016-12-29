package com.example.movieplayer;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.drm.DrmStore.Playback;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends Activity {

	private VideoView vv;
	
	private SeekBar sBar;
	private ImageButton img;
	private ListView lv;
	private List<MovieInfo>list;
	int position=0;
	//计时器
	private Timer timer;
	private TimerTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageButton) findViewById(R.id.bt_img);
        sBar = (SeekBar) findViewById(R.id.sBar);
        lv = (ListView) findViewById(R.id.lv);
        vv = (VideoView) findViewById(R.id.vv);
        //计时
        timer = new Timer();
        task = new TimerTask() {
			//设置seekBar
			@Override
			public void run() {
				//如果在播放
				if(vv.isPlaying()){
					int progress = vv.getCurrentPosition();//得到当前进度
					int total = vv.getDuration();
					sBar.setMax(total);
					sBar.setProgress(progress);//设置bar为当前进度
				}
			}
		};
		timer.schedule(task,500,500);
		 /**
         * 拖动进度条的事件监听需要实现SeekBar.OnSeekBarChangeListener接口
         * 调用SeekBar的setOnSeekBarChangeListener把该事件监听对象传递进去进行事件监听
         */
		sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			//  onProgressChanged
            // 该方法拖动进度条进度改变的时候调用
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				int position = sBar.getProgress();
				if(vv.isPlaying()){
					vv.seekTo(position);
				}
			}
			//  onStopTrackingTouch
            // 该方法拖动进度条停止拖动的时候调用
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				
			}

			//  onStartTrackingTouch方法
            // 该方法拖动进度条开始拖动的时候调用。
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				
			}			
		});
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	//groupId	分组id
    	//itemId	菜单项id
    	//orderId	菜单顺序
    	//title		文本
    	menu.add(0, 1, 1, "扫描");
    	menu.add(0, 2, 1, "退出");
    	return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	//获取当前点击的菜单项id
    	switch (item.getItemId()) {
		case 1:
			Toast.makeText(this, "正在扫描本地视频", Toast.LENGTH_SHORT).show();
			search();
			break;
		case 2:
			System.exit(0);
		}
    	
    	return super.onMenuItemSelected(featureId, item);
    }

    //初始化播放
    public void play(int position){
    	vv.setVideoPath(list.get(position).getPath());
		vv.start();
		img.setImageResource(R.drawable.btn_playstate_pause_normal);
    }

	  public void click(View v){  
	   if(vv.isPlaying()){
	   		vv.pause();
	   		img.setImageResource(R.drawable.btn_playstate_play_normal);
  		}else if(sBar.getProgress()==0){
   			Toast.makeText(this, "请先选择一部电影！", Toast.LENGTH_SHORT).show();
   		}else{
   			vv.start();
   			img.setImageResource(R.drawable.btn_playstate_pause_normal);
   		}
   }
	   /**
	    * 扫描本地视频
	    */
   public void search(){
	   list = MovieModel.getMovieInfos(this);//指定当前文件加载的布局文件
	   //设置数据适配器
	   lv.setAdapter(new MyAdapter());
	   lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			//点击listView的item之后设置视频路径并播放
			if(arg2>=0)
				position = arg2;
			play(position);
		}
	});
   }
   
   
   
   /**
    * 设置数据适配器控制listView视图
    */
   class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			View view = View.inflate(MainActivity.this, R.layout.movie_item, null);
			TextView moviename = (TextView) view.findViewById(R.id.moviename);
			TextView movieduration = (TextView) view.findViewById(R.id.movieduration);
			moviename.setText(list.get(arg0).getName());
			//设置时间
			int time = list.get(arg0).getDuration();//歌曲时长，毫秒
			time = time/1000;
			int minute = time/60;
			int second = time%60;
			String duration = String.format("%02d:%02d", minute,second);
			movieduration.setText(duration);
			return view;
		}
		   
	}
}

