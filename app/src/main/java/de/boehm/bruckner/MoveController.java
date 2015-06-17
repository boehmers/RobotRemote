package de.boehm.bruckner;

/**
 * Created by Manuel on 17.06.2015.
 */
public interface MoveController {

    public void startMovingBackward();

    public void startMovingForward();

    public void startTurnLeft();

    public void startTurnRight();

    public void stopMovingBackward();

    public void stopMovingForward();

    public void stopTurnLeft();

    public void stopTurnRight();

}