package de.boehm.bruckner;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;

import de.boehm.bruckner.lib.RobotRemoteCommands;

/**
 * Created by Manuel on 17.06.2015.
 */
public class GrabControllerImpl implements GrabController {

    private OutputStreamWriter streamWriter;

    public GrabControllerImpl(OutputStreamWriter streamWriter){
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

    @Override
    public void startGrab(){
        write(RobotRemoteCommands.ROBOT_START_GRAB);
    }

    @Override
    public void startGrabRelease(){
        write(RobotRemoteCommands.ROBOT_START_GRAB_RELEASE);
    }

    @Override
    public void stopGrab(){
        write(RobotRemoteCommands.ROBOT_STOP_GRAB);
    }

    @Override
    public void stopGrabRelease(){
        write(RobotRemoteCommands.ROBOT_STOP_GRAB_RELEASE);
    }
}