package org.firstinspires.ftc.teamcode.auto;  //auto path

import com.qualcomm.robotcore.hardware.DcMotor;
//only dependency

public class LinearMovement {

    private DcMotor frontLeftMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backRightMotor = null;
    //initializing motor variables

    //designing the function :O
    public LinearMovement(DcMotor frontLeftMotor, DcMotor backLeftMotor, DcMotor frontRightMotor, DcMotor backRightMotor) {
        this.frontLeftMotor = frontLeftMotor;
        this.backLeftMotor = backLeftMotor;
        this.frontRightMotor = frontRightMotor;
        this.backRightMotor = backRightMotor;
    }

    //method to reset encoders
    public void resetEncoders() {
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //setting the motors to encoder mode
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //averaging encoder ticks
    public int getAverageTicks() {
        int totalTicks = frontLeftMotor.getCurrentPosition() +
                         backLeftMotor.getCurrentPosition() +
                         frontRightMotor.getCurrentPosition() +
                         backRightMotor.getCurrentPosition();
        return totalTicks / 4; //return the tick number avg
    }

    //method allowing us to move foward by x ticks
    public void moveForward(int targetTicks) {
        resetEncoders();  //resetting encoders before starting (keeps running distance in check)

        //stemtrons, roll out
        frontLeftMotor.setPower(0.5);
        backLeftMotor.setPower(0.5);
        frontRightMotor.setPower(0.5);
        backRightMotor.setPower(0.5);

        //push until the finish line, or the set number of ticks to run
        while (Math.abs(getAverageTicks()) < targetTicks) {
            //Note to Self: ENCODER DRIFT CHECKER HERE
        }

        //stop dt when we get to the final destination
        stopMotors();
    }

    //same as above, moves us backwards
    public void moveBackward(int targetTicks) {
        resetEncoders(); 

        //negative power = negative movement on y axis
        frontLeftMotor.setPower(-0.5);
        backLeftMotor.setPower(-0.5);
        frontRightMotor.setPower(-0.5);
        backRightMotor.setPower(-0.5);

        while (Math.abs(getAverageTicks()) < targetTicks) {
        }

        stopMotors();
    }

    //stops all motors, doing our best not to overcalibrate.
    public void stopMotors() {
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }
}
