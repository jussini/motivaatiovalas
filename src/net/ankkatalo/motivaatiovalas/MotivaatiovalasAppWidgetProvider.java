package net.ankkatalo.motivaatiovalas;

import java.util.Random;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

public class MotivaatiovalasAppWidgetProvider extends AppWidgetProvider {

	private static final String TAG = "MotivaatiovalasAppWidgetProvider";
	
	private int[] messages = {
			R.drawable.motivaatiovalas1,
			R.drawable.motivaatiovalas2,
			R.drawable.motivaatiovalas3,
			R.drawable.motivaatiovalas4,
			R.drawable.motivaatiovalas5,
			R.drawable.motivaatiovalas6,
			R.drawable.motivaatiovalas7,
			R.drawable.motivaatiovalas8,
			R.drawable.motivaatiovalas9};
	
	public void onUpdate(Context context, AppWidgetManager manager, int[] appWidgetIds) {
		
		Log.v(TAG, "onUpdate");
		
		final int numMessages = messages.length;
		
		Random r = new Random();
		
		for(int appWidgetId : appWidgetIds) {
			
			Log.v(TAG, "onUpdate loop");
			
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
			
			int i = r.nextInt(numMessages);
			views.setImageViewResource(R.id.imageView1, messages[i]);
						
			manager.updateAppWidget(appWidgetId, views);			
		}
	}
	
}