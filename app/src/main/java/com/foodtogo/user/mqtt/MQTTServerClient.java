package com.foodtogo.user.mqtt;

import android.content.Context;
import android.util.Log;

import com.foodtogo.user.base.AppConstants;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class MQTTServerClient implements AppConstants {

    private int TIME_OUT = 10;

    private MQTTCallBack MQTTCallBack;
    private MqttAndroidClient mqttAndroidClient;
    private IMqttToken token;

    public void mqttCallBack(MQTTCallBack callback) {
        MQTTCallBack = callback;
    }


    public void clientConnect(Context activity) {
        if(activity!=null) {
            mqttAndroidClient = new MqttAndroidClient(activity, AppConstants.MQTT_SERVER_ENDPOINT,
                    MqttAsyncClient.generateClientId());
            try {
                MqttConnectOptions options = new MqttConnectOptions();
                options.setCleanSession(true);
                options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
                options.setConnectionTimeout(TIME_OUT);
                options.setAutomaticReconnect(false);
                token = mqttAndroidClient.connect(options);
                token.setActionCallback(new MQTTActionListener(mqttAndroidClient, MQTTCallBack));
            } catch (NullPointerException e) {
                closeConnection();
                Log.d(MQTT_CONNECTION, e.toString());
                MQTTCallBack.connectionLost(e.toString());
            } catch (MqttException e) {
                closeConnection();
                Log.d(MQTT_CONNECTION, e.toString());
                MQTTCallBack.connectionLost(e.toString());
            } catch (Exception e) {
                closeConnection();
                Log.d(MQTT_CONNECTION, e.toString());
                MQTTCallBack.connectionLost(e.toString());
            }
        }
    }


    public void closeConnection(){
        try {
            if (mqttAndroidClient != null) {
                mqttAndroidClient.unregisterResources();
                mqttAndroidClient.close();
            }
        } catch (Exception e) {
            Log.e(EXCEPTION, e.toString());
        }
    }


    public void subscribe(MqttAndroidClient mqttAndroidClient, final String topic) {
        Log.i("SUBSCRIBE", topic);
        int qos = 1;
        try {
            mqttAndroidClient.subscribe(topic, qos);
            mqttAndroidClient.setCallback(new MQTTServerCallBack(mqttAndroidClient, MQTTCallBack));
        } catch (MqttException e) {
            MQTTCallBack.connectionLost(e.toString());
        } catch (Exception e) {
            MQTTCallBack.connectionLost(e.toString());
        }
    }

    public void unsubscribe(MqttAndroidClient mqttAndroidClient, final String topic) {
        Log.i("SUBSCRIBE", topic);
        try {
            mqttAndroidClient.unsubscribe(topic);
            mqttAndroidClient.setCallback(new MQTTServerCallBack(mqttAndroidClient, MQTTCallBack));
        } catch (MqttException e) {
            MQTTCallBack.connectionLost(e.toString());
        } catch (Exception e) {
            MQTTCallBack.connectionLost(e.toString());
        }
    }

    public void publish(MqttAndroidClient mqttAndroidClient, final String topic, String body) {
        Log.i("PUBLISH", topic);
        Log.i("PAYLOAD", body);
        int qos = 1;
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = body.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            message.setRetained(false);
            mqttAndroidClient.publish(topic, message);
            mqttAndroidClient.setCallback(new MQTTServerCallBack(mqttAndroidClient, MQTTCallBack));
        } catch (MqttException e) {
            MQTTCallBack.connectionLost(e.toString());
        } catch (UnsupportedEncodingException e) {
            MQTTCallBack.connectionLost(e.toString());
        }
    }

}
