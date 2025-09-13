package org.firstinspires.ftc.teamcode.auto;

// Import necessary hardware libraries
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class ComplexMovement {

    private DcMotor frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor;
    private LinearOpMode opMode; // Reference to the OpMode

    // Constructor to initialize the motors and OpMode reference
    public ComplexMovement(DcMotor frontLeftMotor, DcMotor backLeftMotor, DcMotor frontRightMotor, DcMotor backRightMotor, LinearOpMode opMode) {
        this.frontLeftMotor = frontLeftMotor;
        this.backLeftMotor = backLeftMotor;
        this.frontRightMotor = frontRightMotor;
        this.backRightMotor = backRightMotor;
        this.opMode = opMode; // Store OpMode reference
    }

    // Function to strafe left for a given time in milliseconds
    // This and the code below won't make sense unless you've studied the mecanum dt
    public void strafeLeft(long timeMs) {
        frontLeftMotor.setPower(-0.5);
        backLeftMotor.setPower(0.5);
        frontRightMotor.setPower(0.5);
        backRightMotor.setPower(-0.5);
        opMode.sleep(timeMs);  // Use the sleep method from opMode
        stopMotors();
    }

    // Function to strafe right for a given time in milliseconds
    public void strafeRight(long timeMs) {
        frontLeftMotor.setPower(0.5);
        backLeftMotor.setPower(-0.5);
        frontRightMotor.setPower(-0.5);
        backRightMotor.setPower(0.5);
        opMode.sleep(timeMs);  // Use the sleep method from opMode
        stopMotors();
    }

    // Function to turn left for a given time in milliseconds
    public void turnLeft(long timeMs) {
        frontLeftMotor.setPower(-0.5);
        backLeftMotor.setPower(-0.5);
        frontRightMotor.setPower(0.5);
        backRightMotor.setPower(0.5);
        opMode.sleep(timeMs);  // Use the sleep method from opMode
        stopMotors();
    }

    // Function to turn right for a given time in milliseconds
    public void turnRight(long timeMs) {
        frontLeftMotor.setPower(0.5);
        backLeftMotor.setPower(0.5);
        frontRightMotor.setPower(-0.5);
        backRightMotor.setPower(-0.5);
        opMode.sleep(timeMs);  // Use the sleep method from opMode
        stopMotors();
    }

    // Stop all motors
    public void stopMotors() {
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }
}
