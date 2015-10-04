package sub;

import util.Utils;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author Dominik Obermaier
 * @author Christian Götz
 */
public class Subscriber {

    public static final String BROKER_URL = "tcp://52.10.221.227:1883";

    //We have to generate a unique Client id.
    String clientId = Utils.getMacAddress() + "-sub";
    private MqttClient mqttClient;
    private String topic = null;

    public Subscriber(String topic) {
    	
    	this.topic = topic;

        try {
            mqttClient = new MqttClient(BROKER_URL, clientId);


        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void start() {
        try {

            mqttClient.setCallback(new SubscribeCallback());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            mqttClient.connect(options);

            //Subscribe to all subtopics of home
            final String topic = this.topic;
            mqttClient.subscribe(topic);

            System.out.println("Subscriber is now listening to "+topic);

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String... args) {
        final Subscriber subscriber = new Subscriber("iot/#");
        subscriber.start();
    }

}
