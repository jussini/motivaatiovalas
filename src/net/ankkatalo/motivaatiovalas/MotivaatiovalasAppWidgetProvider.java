package net.ankkatalo.motivaatiovalas;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
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
			R.drawable.motivaatiovalas9,
			R.drawable.motivaatiovalas10,
			R.drawable.motivaatiovalas11,
			R.drawable.motivaatiovalas12,
			R.drawable.motivaatiovalas13,
			R.drawable.motivaatiovalas14};

	private static Set<Integer> m_activeAppWidgetIds = new HashSet<Integer>();

	public MotivaatiovalasAppWidgetProvider() {
		super();		
		Log.v(TAG, "Constructor");
	}
	
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		Log.v(TAG, "onDeleted");
		for (int appWidgetId : appWidgetIds) {
			Log.v(TAG, String.format("onDeleted %d", appWidgetId));
			m_activeAppWidgetIds.remove(new Integer(appWidgetId));
		}

	}
	
	public void onUpdate(Context context, AppWidgetManager manager, int[] appWidgetIds) {
		
		Log.v(TAG, "onUpdate");
		
		final int numMessages = messages.length;
		
		Random r = new Random();
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
		
		for(int appWidgetId : appWidgetIds) {
			
			m_activeAppWidgetIds.add(new Integer(appWidgetId));
		}
				
		int[] widgetIds = new int[m_activeAppWidgetIds.size()];
		for (int i = 0; i < m_activeAppWidgetIds.size(); ++i) {
			widgetIds[i] = ((Integer)m_activeAppWidgetIds.toArray()[i]).intValue(); // pretty...			
		}

		for(int appWidgetId : appWidgetIds) {
//		for(int appWidgetId : m_activeAppWidgetIds) {
			
			Log.v(TAG, String.format("onUpdate, inLoop: %d, %d", appWidgetId, m_activeAppWidgetIds.size()));
						
			// set a random whale image
			int i = r.nextInt(numMessages);		
			views.setImageViewResource(R.id.imageView1, messages[i]);
			
			// register an onClick listener to update view
			Intent intent = new Intent(context, MotivaatiovalasAppWidgetProvider.class);

			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			views.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
			
			manager.updateAppWidget(appWidgetId, views);			
		}
	}
	
}