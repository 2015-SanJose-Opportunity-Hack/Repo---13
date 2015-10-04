package pub;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

import util.Utils;

import org.eclipse.paho.client.mqttv3.*;

/**
 * @author Dominik Obermaier
 * @author Christian Götz
 */
public class Publisher {

    public static final String BROKER_URL = "tcp://52.10.221.227:1883";

    String TOPIC_BRIGHTNESS = "iot/bright";
    String TOPIC_TEMPERATURE = "iot/temp";

    private MqttClient client;


    public Publisher(String brightnessTopic, String temperatureTopic) {
    	
    	this.TOPIC_BRIGHTNESS = brightnessTopic;
    	this.TOPIC_TEMPERATURE = temperatureTopic;

        //We have to generate a unique Client id.
        String clientId = Utils.getMacAddress() + "-pub";


        try {

            client = new MqttClient(BROKER_URL, clientId);

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void start() {

        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setWill(client.getTopic("home/LWT"), "I'm gone :(".getBytes(), 0, false);

            client.connect(options);

            //Publish data forever
           // while (true) {

                publishBrightness();

                Thread.sleep(500);

                try {
					publishTemperature();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

                Thread.sleep(500);
            //}
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void publishTemperature() throws MqttException, FileNotFoundException {
        final MqttTopic temperatureTopic = client.getTopic(TOPIC_TEMPERATURE);

        final int temperatureNumber = Utils.createRandomNumberBetween(20, 30);
        final String temperature = temperatureNumber + "°C";

        File file = new File("/home/blackdragon/Radha .wav");
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                //System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            //Logger.getLogger(genJpeg.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] bytes = bos.toByteArray();
        
        
        MqttMessage m = new MqttMessage(bytes);
        m.setQos(2);
        //m.setRetained(true);
        
        temperatureTopic.publish(m);

        System.out.println("Published data. Topic: " + temperatureTopic.getName() + "  Message: " + temperature);
    }

    private void publishBrightness() throws MqttException {
        final MqttTopic brightnessTopic = client.getTopic(TOPIC_BRIGHTNESS);

        final int brightnessNumber = Utils.createRandomNumberBetween(0, 100);
        final String brigthness = brightnessNumber + "%";

        brightnessTopic.publish(new MqttMessage(brigthness.getBytes()));

        System.out.println("Published data. Topic: " + brightnessTopic.getName() + "   Message: " + brigthness);
    }

    public static void main(String... args) {
        final Publisher publisher = new Publisher("iot/bright", "iot/temp");
        publisher.start();
    }
}
