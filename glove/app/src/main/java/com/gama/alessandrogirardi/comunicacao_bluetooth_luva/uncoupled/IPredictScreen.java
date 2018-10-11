package com.gama.alessandrogirardi.comunicacao_bluetooth_luva.uncoupled;

public interface IPredictScreen {

    void getReadyToSpeak();
    void speak();
    void appendCharaterToScreen(char character);
    void closeTheSpeaker();

}
