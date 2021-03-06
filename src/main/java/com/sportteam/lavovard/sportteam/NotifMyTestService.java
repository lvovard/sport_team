package com.sportteam.lavovard.sportteam;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.GregorianCalendar;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotifMyTestService extends IntentService 
{
  public NotifMyTestService()
  {
    super("MyTestService");
  }

  // ...
  @Override
  protected void onHandleIntent(Intent intent) {
    /////
    OutputStreamWriter out;
    try
    {
      out = new OutputStreamWriter(openFileOutput("alarm.txt",Context.MODE_PRIVATE));
      String line = "ALARM=STARTED\n\r";
      out.write(line);
      out.close();
    } catch (FileNotFoundException e)
    {
      e.printStackTrace();
    } catch (IOException e)
    {
      e.printStackTrace();
    }

    
    //start alarm
    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    Intent intent1 = new Intent(this,NotifMyBroadcastReceiver.class); 
    PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent1, 0);
    //alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, pendingIntent);
    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,GregorianCalendar.getInstance().getTimeInMillis()+5000, Global.TIME_MS_FOR_NOTIF_CHECK, pendingIntent);
    //Toast.makeText(this, "ALARM started here", Toast.LENGTH_LONG).show();
    /////
      // Release the wake lock provided by the WakefulBroadcastReceiver.
      //WakefulBroadcastReceiver.completeWakefulIntent(intent);
  }
}