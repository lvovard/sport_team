package com.sportteam.lavovard.sportteam;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.content.Context;
import android.content.ContextWrapper;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

@TargetApi(26)
public class NotificationHelper extends ContextWrapper {
    private NotificationManager notifManager;

    private static final String CHANNEL_HIGH_ID = "com.sportteam.lavovard.sportteam.HIGH_CHANNEL";
    private static final String CHANNEL_HIGH_NAME = "High Channel";

    private static final String CHANNEL_DEFAULT_ID = "com.sportteam.lavovard.sportteam.DEFAULT_CHANNEL";
    private static final String CHANNEL_DEFAUL_NAME = "Default Channel";


    public NotificationHelper( Context base ) {
        super( base );

        notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        long [] swPattern = new long[] { 0, 500, 110, 500, 110, 450, 110, 200, 110,
                170, 40, 450, 110, 200, 110, 170, 40, 500 };

        NotificationChannel notificationChannelHigh = new NotificationChannel(
                CHANNEL_HIGH_ID, CHANNEL_HIGH_NAME, notifManager.IMPORTANCE_HIGH );
        notificationChannelHigh.enableLights( true );
        notificationChannelHigh.setLightColor( Color.RED );
        notificationChannelHigh.setShowBadge( true );
        notificationChannelHigh.enableVibration( true );
        notificationChannelHigh.setVibrationPattern( swPattern );
        notificationChannelHigh.setLockscreenVisibility( Notification.VISIBILITY_PUBLIC );
        notifManager.createNotificationChannel( notificationChannelHigh );

        NotificationChannel notificationChannelDefault = new NotificationChannel(
                CHANNEL_DEFAULT_ID, CHANNEL_DEFAUL_NAME, notifManager.IMPORTANCE_DEFAULT );
        notificationChannelDefault.enableLights( true );
        notificationChannelDefault.setLightColor( Color.WHITE );
        notificationChannelDefault.enableVibration( true );
        notificationChannelDefault.setShowBadge( false );
        notifManager.createNotificationChannel( notificationChannelDefault );
    }


    public void notify(int id, boolean prioritary, String title, String message, PendingIntent intent, String tickertxt) {
        String channelId = prioritary ? CHANNEL_HIGH_ID : CHANNEL_DEFAULT_ID;
        Resources res = getApplicationContext().getResources();

        Notification notification = new Notification.Builder( getApplicationContext(), channelId )
                .setContentIntent( intent )      // On injecte le contentIntent
                .setContentTitle( title )
                .setTicker(tickertxt)
                .setContentText( message )
                .setSmallIcon( R.drawable.ic_launcher )
                .setLargeIcon( BitmapFactory.decodeResource(res, R.drawable.ic_launcher) )
                .setAutoCancel( true )
                .build();

        notifManager.notify( id, notification );
    }

}
