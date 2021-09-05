package com.foodtogo.user.mqtt;

import android.util.Log;

import com.foodtogo.user.base.AppConstants;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MQTTServerCallBack implements MqttCallbackExtended, AppConstants {

    private MqttAndroidClient mqttAndroidClient;
    private MQTTCallBack MQTTCallBack;

    MQTTServerCallBack(MqttAndroidClient mqttAndroidClient, MQTTCallBack MQTTCallBack) {
        this.mqttAndroidClient = mqttAndroidClient;
        this.MQTTCallBack = MQTTCallBack;
    }

    public void connectionLost(Throwable throwable) {
        Log.e(MQTT_EXCEPTION, throwable.toString());
        MQTTCallBack.connectionLost(throwable.toString());

    }

    public void messageArrived(String topic, MqttMessage mqttMessage) {
        Log.i(MQTT_MSG, new String(mqttMessage.getPayload()));
        MQTTCallBack.onMessageReceived(topic, mqttMessage);

    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        Log.i(MQTT_DELIVERY, iMqttDeliveryToken.toString());
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        if (reconnect) {
            MQTTCallBack.reConnected(mqttAndroidClient);
            Log.i(MQTT_CONNECTION, MQTT_CONNECTION_RECONNECT);
        } else {
            Log.i(MQTT_CONNECTION, MQTT_CONNECTION_SUCCESS);
        }

    }
}
