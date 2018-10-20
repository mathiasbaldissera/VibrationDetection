package org.gama.applications.vibrationdetectorapp.uncoupled;

public interface IPredictScreen {

    void getReadyToSpeak();
    void speak();
    void appendCharaterToScreen(char character);
    void closeTheSpeaker();

}
