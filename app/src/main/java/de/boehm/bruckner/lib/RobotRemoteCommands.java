package de.boehm.bruckner.lib;


/**
 * @author manuboeh
 * @version 1.0
 * @created 17-Jun-2015 15:23:13
 */
public class RobotRemoteCommands {

	public static final byte ROBOT_BEEP = 13;
	public static final byte ROBOT_MESSAGE = 14;
	public static final byte ROBOT_START_GRAB = 9;
	public static final byte ROBOT_START_GRAB_RELEASE = 11;
	public static final byte ROBOT_START_MOVING_BACKWARD = 7;
	public static final byte ROBOT_START_MOVING_FORWARD = 1;
	public static final byte ROBOT_START_TURN_LEFT = 3;
	public static final byte ROBOT_START_TURN_RIGHT = 5;
	public static final byte ROBOT_STOP_GRAB = 10;
	public static final byte ROBOT_STOP_GRAB_RELEASE = 12;
	public static final byte ROBOT_STOP_MOVING_BACKWARD = 8;
	public static final byte ROBOT_STOP_MOVING_FORWARD = 2;
	public static final byte ROBOT_STOP_TURN_LEFT = 4;
	public static final byte ROBOT_STOP_TURN_RIGHT = 6;

	private RobotRemoteCommands(){

	}
}