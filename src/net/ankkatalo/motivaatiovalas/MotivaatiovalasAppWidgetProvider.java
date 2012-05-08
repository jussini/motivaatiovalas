package net.ankkatalo.motivaatiovalas;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

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


	public void onDeleted(Context context, int[] appWidgetIds) {
		// remove all deleted widget ids from the list of known active ids
		super.onDeleted(context, appWidgetIds);
		for (int appWidgetId : appWidgetIds) {
			m_activeAppWidgetIds.remove(Integer.valueOf(appWidgetId));
		}
	}
	
    public void onReceive(Context context, Intent intent) {
    	Log.d(TAG, String.format("onReceive, intent action: %s", intent.getAction()));
    	
    	if (intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE")) {
    		int appWidgetId = intent.getIntExtra(
    				AppWidgetManager.EXTRA_APPWIDGET_ID,
    		        AppWidgetManager.INVALID_APPWIDGET_ID);
        	Log.d(TAG, String.format("onReceive, appWidgetId: %d", appWidgetId));    		
    	}
    	
    	//AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        //if (intent.getAction().equals(TOAST_ACTION)) {
        //    int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
        //            AppWidgetManager.INVALID_APPWIDGET_ID);
        //    int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
        //    Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
        //}
        super.onReceive(context, intent);
    }
	
	public void onUpdate(Context context, AppWidgetManager manager, int[] appWidgetIds) {
		
		
		final int numMessages = messages.length;
		
		Random r = new Random();
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
		
		for(int appWidgetId : appWidgetIds) {			
			m_activeAppWidgetIds.add(Integer.valueOf(appWidgetId));
		}

		// create primitive integer array to use instead of appWidgetIds, because
		// appWidgetIds may not always contain ids of all "active" widgets, thus not all of
		// them will get updated
		int[] widgetIds = new int[m_activeAppWidgetIds.size()];
		for (int i = 0; i < m_activeAppWidgetIds.size(); ++i) {
			widgetIds[i] = ((Integer)m_activeAppWidgetIds.toArray()[i]).intValue(); // pretty...			
		}
	

		// register an onClick listener to update view
		Intent intent = new Intent(context, MotivaatiovalasAppWidgetProvider.class);
	
		intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
		
		            
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
				0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		views.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
		
		
		// set a new random whale image to every widget
//		for(int appWidgetId : m_activeAppWidgetIds) {
		for(int appWidgetId : appWidgetIds) {
			int i = r.nextInt(numMessages);
					
			//views.setImageViewResource(R.id.imageView1, messages[i]);						
			views.showNext(R.id.viewFlipper);
			manager.updateAppWidget(appWidgetId, views);			
		}
		
		super.onUpdate(context, manager, appWidgetIds);
		
	}	
}