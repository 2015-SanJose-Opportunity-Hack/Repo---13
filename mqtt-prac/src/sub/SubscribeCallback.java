package sub;

import java.io.File;
import java.io.FileOutputStream;

import org.eclipse.paho.client.mqttv3.*;

/**
 * @author Dominik Obermaier
 * @author Christian Götz
 */
public class SubscribeCallback implements MqttCallback {

    @Override
    public void connectionLost(Throwable cause) {
        //This is called when the connection is lost. We could reconnect here.
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
    	
    	File someFile = new File("/home/blackdragon/Desktop/output.wav");
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(message.getPayload());
        fos.flush();
        fos.close();
        
        System.out.println("Message arrived. Topic: " + topic);

        if ("home/LWT".equals(topic)) {
            System.err.println("Sensor gone!");
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //no-op
    }
}
