package org.firstinspires.ftc.teamcode.misc;
//this isn't *really* necessary, but specifies the directory that you'll
//find this class in, omitting the root(base) directory and the name of the class.
//It's useful when you try to segment code.

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
/* These are the dependencies needed to run this code, it might look complex
but not to worry, it's easily explained as import <--grab this piece of code 
grab it FROM: FTC SDK (It's a jar file included somewhere in the SDK) 
and it's not accessible from the robotcontroller package (semicolon is
the equivalent of period, hopefully you know that though!)*/

public class RobotHardware {
    //initialize hardware variables, no exceptions here
    public DcMotor frontLeftMotor = null;
    public DcMotor backLeftMotor = null;
    public DcMotor frontRightMotor = null;
    public DcMotor backRightMotor = null;
    public DcMotor armMotor = null;
    public DcMotor viperSlideMotor = null;
    public CRServo intake = null;

    //initialize the hardware itself
    public void init(HardwareMap hardwareMap) {
        //with hardwaremap function, thanks FIRST
        frontLeftMotor = hardwareMap.get(DcMotor.class, "drive0"); //if names like drive0 confuse you, its numbered after the ports,
        backLeftMotor = hardwareMap.get(DcMotor.class, "drive1"); //starting from zero since we're master hackers or something lol.
        frontRightMotor = hardwareMap.get(DcMotor.class, "drive2");
        backRightMotor = hardwareMap.get(DcMotor.class, "drive3");
        armMotor = hardwareMap.get(DcMotor.class, "arm0");
        viperSlideMotor = hardwareMap.get(DcMotor.class, "VSM0");
        intake = hardwareMap.get(CRServo.class, "serv1"); //using port 1 since the original bot had another servo in port 0, but this can be changed.
                                                          //if for some reason the servo is not working, check its cable and extension cables for breaks, and
                                                          //ensure that the motor is plugged into the correct port.
        //note, if this breaks code build, undo these comments and make it multiline: '/*(START) and */ (STOP)'

        //set motor directions, for anybody who's confused, all these motors spin the same way unless I tell them otherwise

        //frontLeftMotor.setDirection(DcMotor.Direction.FORWARD); //As such, this line and the one below it are redundant
        //backLeftMotor.setDirection(DcMotor.Direction.FORWARD);

        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        //set direction for continuous rotation servo.

        /* explanation: for servos who rotate continuously (achieved via the servo programmer),
        they'll operate similarly to motors, with the exception that there is no BRAKE mode.
         Powers are speeds on intervals of .1, and their
        directions can be manipulated via software. */

        intake.setDirection(CRServo.Direction.FORWARD);

        //set brake mode on motors who might need it.

        /*explanation: we want the motor to hold its position, especially on the arm. 
        If we set it to BRAKE mode (not the default btw), then the motors will do their best
        to actively resist external movement, good to prevent accidental movement in auto*/

        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);//see above :)
        viperSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
}
