package de.boehm.bruckner;

/**
 * Created by Manuel on 17.06.2015.
 */
public interface CommunicationController {

    public void beep();

    /**
     *
     * @param msg
     */
    public void sendMessage(String msg);

}