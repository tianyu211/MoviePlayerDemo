package com.example.movieplayer;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class MovieModel {
	public static ArrayList<MovieInfo>getMovieInfos(Context context){
		ArrayList<MovieInfo>list = new ArrayList<MovieInfo>();
		 ContentResolver resolver = context.getContentResolver();//���ʱ��˵����ݿ⣬ʹ���Ĵ����provider
		 //���� Video����Ƶ        Audio������
		 Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		 String []projection = {MediaStore.Video.Media.TITLE,MediaStore.Video.Media.DURATION,MediaStore.Video.Media.DATA};
		 //
		 Cursor cursor = resolver.query(uri, projection, null, null,null);
		 while(cursor.moveToNext()){
			 MovieInfo movieInfo = new MovieInfo();
			 movieInfo.setDuration(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)));
			 movieInfo.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE)));
			 movieInfo.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
			 list.add(movieInfo);
		 }
		 cursor.close();
		 return list;
	}

}
