package com.foodtogo.user.mqtt;

import android.util.Log;

import com.foodtogo.user.base.AppConstants;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;


public class MQTTActionListener implements IMqttActionListener, AppConstants {

    MqttAndroidClient mqttAndroidClient;
    private MQTTCallBack MQTTCallBack;

    public MQTTActionListener(MqttAndroidClient client, MQTTCallBack callback) {
        this.mqttAndroidClient = client;
        this.MQTTCallBack = callback;
    }

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        Log.i(MQTT_CONNECTION, MQTT_CONNECTION_SUCCESS);
        MQTTCallBack.onSuccess(mqttAndroidClient);
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        closeConnection();
        Log.e(MQTT_CONNECTION, MQTT_CONNECTION_FAILED);
        MQTTCallBack.onFailure(exception.toString());
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
}
