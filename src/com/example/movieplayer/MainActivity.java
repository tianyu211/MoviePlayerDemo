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
	//��ʱ��
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
        //��ʱ
        timer = new Timer();
        task = new TimerTask() {
			//����seekBar
			@Override
			public void run() {
				//����ڲ���
				if(vv.isPlaying()){
					int progress = vv.getCurrentPosition();//�õ���ǰ����
					int total = vv.getDuration();
					sBar.setMax(total);
					sBar.setProgress(progress);//����barΪ��ǰ����
				}
			}
		};
		timer.schedule(task,500,500);
		 /**
         * �϶����������¼�������Ҫʵ��SeekBar.OnSeekBarChangeListener�ӿ�
         * ����SeekBar��setOnSeekBarChangeListener�Ѹ��¼��������󴫵ݽ�ȥ�����¼�����
         */
		sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			//  onProgressChanged
            // �÷����϶����������ȸı��ʱ�����
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				int position = sBar.getProgress();
				if(vv.isPlaying()){
					vv.seekTo(position);
				}
			}
			//  onStopTrackingTouch
            // �÷����϶�������ֹͣ�϶���ʱ�����
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				
			}

			//  onStartTrackingTouch����
            // �÷����϶���������ʼ�϶���ʱ����á�
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				
			}			
		});
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	//groupId	����id
    	//itemId	�˵���id
    	//orderId	�˵�˳��
    	//title		�ı�
    	menu.add(0, 1, 1, "ɨ��");
    	menu.add(0, 2, 1, "�˳�");
    	return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	//��ȡ��ǰ����Ĳ˵���id
    	switch (item.getItemId()) {
		case 1:
			Toast.makeText(this, "����ɨ�豾����Ƶ", Toast.LENGTH_SHORT).show();
			search();
			break;
		case 2:
			System.exit(0);
		}
    	
    	return super.onMenuItemSelected(featureId, item);
    }

    //��ʼ������
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
   			Toast.makeText(this, "����ѡ��һ����Ӱ��", Toast.LENGTH_SHORT).show();
   		}else{
   			vv.start();
   			img.setImageResource(R.drawable.btn_playstate_pause_normal);
   		}
   }
	   /**
	    * ɨ�豾����Ƶ
	    */
   public void search(){
	   list = MovieModel.getMovieInfos(this);//ָ����ǰ�ļ����صĲ����ļ�
	   //��������������
	   lv.setAdapter(new MyAdapter());
	   lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			//���listView��item֮��������Ƶ·��������
			if(arg2>=0)
				position = arg2;
			play(position);
		}
	});
   }
   
   
   
   /**
    * ������������������listView��ͼ
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
			//����ʱ��
			int time = list.get(arg0).getDuration();//����ʱ��������
			time = time/1000;
			int minute = time/60;
			int second = time%60;
			String duration = String.format("%02d:%02d", minute,second);
			movieduration.setText(duration);
			return view;
		}
		   
	}
}

