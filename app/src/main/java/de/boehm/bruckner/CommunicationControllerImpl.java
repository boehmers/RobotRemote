package de.boehm.bruckner;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;

import de.boehm.bruckner.lib.RobotRemoteCommands;

/**
 * Created by Manuel on 17.06.2015.
 */
public class CommunicationControllerImpl implements CommunicationController {

    private OutputStreamWriter streamWriter;

    public CommunicationControllerImpl(OutputStreamWriter streamWriter){
        this.streamWriter = streamWriter;
    }

    private void write(byte x) {
        try {
            streamWriter.write(x);
            streamWriter.flush();
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), "Writing Error: ", e);
        }
    }

    private void writeMsg(String msg) {
        try {
            streamWriter.write(RobotRemoteCommands.ROBOT_MESSAGE);
            streamWriter.write(msg);
            streamWriter.write(RobotRemoteCommands.ROBOT_MESSAGE);
            streamWriter.flush();
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), "Writing Error: ", e);
        }
    }

    @Override
    public void beep(){
        write(RobotRemoteCommands.ROBOT_BEEP);
    }

    /**
     *
     * @param msg
     */
    @Override
    public void sendMessage(String msg){
        writeMsg(msg);
    }
}