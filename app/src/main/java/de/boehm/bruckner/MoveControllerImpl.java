package de.boehm.bruckner;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;

import de.boehm.bruckner.lib.RobotRemoteCommands;

/**
 * Created by Manuel on 17.06.2015.
 */
public class MoveControllerImpl implements MoveController {

    private OutputStreamWriter streamWriter;

    public MoveControllerImpl(OutputStreamWriter streamWriter){
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
    public void startMovingBackward(){
        write(RobotRemoteCommands.ROBOT_START_MOVING_BACKWARD);
    }

    @Override
    public void startMovingForward(){
        write(RobotRemoteCommands.ROBOT_START_MOVING_FORWARD);
    }

    @Override
    public void startTurnLeft(){
        write(RobotRemoteCommands.ROBOT_START_TURN_LEFT);
    }

    @Override
    public void startTurnRight(){
        write(RobotRemoteCommands.ROBOT_START_TURN_RIGHT);
    }

    @Override
    public void stopMovingBackward(){
        write(RobotRemoteCommands.ROBOT_STOP_MOVING_BACKWARD);
    }

    @Override
    public void stopMovingForward(){
        write(RobotRemoteCommands.ROBOT_STOP_MOVING_FORWARD);
    }

    @Override
    public void stopTurnLeft(){
        write(RobotRemoteCommands.ROBOT_STOP_TURN_LEFT);
    }

    @Override
    public void stopTurnRight(){
        write(RobotRemoteCommands.ROBOT_STOP_TURN_RIGHT);
    }
}