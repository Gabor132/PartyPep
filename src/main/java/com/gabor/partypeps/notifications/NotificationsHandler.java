package com.gabor.partypeps.notifications;

import com.gabor.partypeps.models.dao.NotificationAuthorization;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.apache.http.HttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import java.security.Security;

@Service
public class NotificationsHandler {

    public static boolean doNotificationsToUser(NotificationAuthorization notificationAuthorization) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            String payload = "This is a notification!";
            PushService pushService = new PushService("550062029840");
            Notification notification = new Notification(notificationAuthorization.getEndpointUrl(),
                    notificationAuthorization.getP256dh(), notificationAuthorization.getAuth(), payload.getBytes());
            HttpResponse response = pushService.send(notification);
            return response.getStatusLine().getStatusCode() == 201;
        }catch(Exception ex){
            System.out.println("Something fucked up");
            ex.printStackTrace();
        }
        return false;
    }

}
