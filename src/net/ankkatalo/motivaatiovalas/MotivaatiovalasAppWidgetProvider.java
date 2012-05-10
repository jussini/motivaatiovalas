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
			R.drawable.motivaatiovalas14,
			R.drawable.motivaatiovalas15,
			R.drawable.motivaatiovalas16};

	private static Set<Integer> m_activeAppWidgetIds = new HashSet<Integer>();


	public void onDeleted(Context context, int[] appWidgetIds) {
		// remove all deleted widget ids from the list of known active ids
		super.onDeleted(context, appWidgetIds);
		for (int appWidgetId : appWidgetIds) {
			m_activeAppWidgetIds.remove(Integer.valueOf(appWidgetId));
		}
	}
		
	public void onUpdate(Context context, AppWidgetManager manager, int[] appWidgetIds) {
		
		// number of different messages, needed for pre api11
		final int numMessages = messages.length;
		// random generator, also needed for pre api11
		Random r = new Random();
		
		// api levels, we have different behavior for pre api 11 widgets
		int sdk_int = android.os.Build.VERSION.SDK_INT ;
		int threshold_int = android.os.Build.VERSION_CODES.HONEYCOMB;
		
		// remote views for this provider
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
		
		// ensure list of active widgets contain all viewids
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
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds);				            
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
				0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// set the intent to correct ui component, depending on sdk version
		if (sdk_int >= threshold_int) {
			views.setOnClickPendingIntent(R.id.viewFlipper, pendingIntent);
		} else {
			views.setOnClickPendingIntent(R.id.imageView, pendingIntent);
		}
						
		// set a new whale image to every widget
		for(int appWidgetId : m_activeAppWidgetIds) {
			int i = r.nextInt(numMessages);

			// on honeycomb and later we have a flipper, earlier versions have just an 
			// image view on which we should change the resource
			if (sdk_int >= threshold_int) {
				views.showNext(R.id.viewFlipper);				
			} else {				
				views.setImageViewResource(R.id.imageView, messages[i]);
			}

			manager.updateAppWidget(appWidgetId, views);			
		}
		
		super.onUpdate(context, manager, appWidgetIds);		
	}	
}