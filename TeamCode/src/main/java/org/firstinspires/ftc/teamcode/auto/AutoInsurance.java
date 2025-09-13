package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "AutoInsurance", group = "Autonomous")
public class AutoInsurance extends LinearOpMode {
    
    final double ARM_TICKS_PER_DEGREE = 28 * 250047.0 / 4913.0 * 100.0 / 20.0 * 1 / 360.0;
    final double ARM_COLLAPSED_INTO_ROBOT = 0;
    double armPosition = ARM_COLLAPSED_INTO_ROBOT;
    //behold! armhold dependencies! (NO TOUCHIE!)

    // Declare hardware variables
    private DcMotor frontLeftMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backRightMotor = null;
    private DcMotor armMotor = null;
    private DcMotor viperSlideMotor = null;
    private CRServo intake = null;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize motors
        frontLeftMotor = hardwareMap.get(DcMotor.class, "drive0");
        backLeftMotor = hardwareMap.get(DcMotor.class, "drive1");
        frontRightMotor = hardwareMap.get(DcMotor.class, "drive2");
        backRightMotor = hardwareMap.get(DcMotor.class, "drive3");
        armMotor = hardwareMap.get(DcMotor.class, "arm0");
        viperSlideMotor = hardwareMap.dcMotor.get("VSM0");
        intake = hardwareMap.get(CRServo.class, "serv1");

        // Set motor directions
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        viperSlideMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Wait for the start signal
        telemetry.addLine("AutoInsurance Initialized!");
        telemetry.update();
        waitForStart();
        
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armPosition = Math.max(-2200, armPosition); // Limit arm to a max raised value
        telemetry.addLine("ArmHold OK");
        telemetry.addLine("ArmLimit OK");
        //HEY! NO TOUCHIE!
        
        viperSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperSlideMotor.setTargetPosition(0);
        viperSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armPosition = Math.max(-1870, armPosition); // Limit vs to a maximum extension value
        telemetry.addLine("VSM OK");
        telemetry.addLine("VSMLimit OK");
        if (isStopRequested()) return;
        
        //instructionset currently fine for 13.10-15V

        moveForward(0300);
        strafeLeft(1000);
        turnLeft(1100);
        moveBackward(0100);
        moveArmToPosition(-2100);
        moveVSToPosition(-1850);
        controlIntake("out", 2500);
        sleep(2000);
        intake.setPower(0.0);
        moveArmToPosition(-2190);
        moveBackward(0500);//adjust with voltage. Higher volt=increase this, lower volt=decrease this
        sleep(0500);//safety to keep commands from running at same time, uncomment as needed. dont get your hand bit.
        moveVSToPosition(0);
        moveArmToPosition(0);
        turnRight(0475);
        moveBackward(1200);
        strafeRight(625);
        
                
    }

    // Function to move forward for a given time in milliseconds
    private void moveForward(long timeMs) {
        frontLeftMotor.setPower(0.5);
        backLeftMotor.setPower(0.5);
        frontRightMotor.setPower(0.5);
        backRightMotor.setPower(0.5);
        sleep(timeMs);
        stopMotors();
    }

    // Function to move backward for a given time in milliseconds
    private void moveBackward(long timeMs) {
        frontLeftMotor.setPower(-0.5);
        backLeftMotor.setPower(-0.5);
        frontRightMotor.setPower(-0.5);
        backRightMotor.setPower(-0.5);
        sleep(timeMs);
        stopMotors();
    }

    // Function to strafe left for a given time in milliseconds
    private void strafeLeft(long timeMs) {
        frontLeftMotor.setPower(-0.5);
        backLeftMotor.setPower(0.5);
        frontRightMotor.setPower(0.5);
        backRightMotor.setPower(-0.5);
        sleep(timeMs);
        stopMotors();
    }

    // Function to strafe right for a given time in milliseconds
    private void strafeRight(long timeMs) {
        frontLeftMotor.setPower(0.5);
        backLeftMotor.setPower(-0.5);
        frontRightMotor.setPower(-0.5);
        backRightMotor.setPower(0.5);
        sleep(timeMs);
        stopMotors();
    }

    // Function to turn left for a given time in milliseconds
    private void turnLeft(long timeMs) {
        frontLeftMotor.setPower(-0.5);
        backLeftMotor.setPower(-0.5);
        frontRightMotor.setPower(0.5);
        backRightMotor.setPower(0.5);
        sleep(timeMs);
        stopMotors();
    }

    // Function to turn right for a given time in milliseconds
    private void turnRight(long timeMs) {
        frontLeftMotor.setPower(0.5);
        backLeftMotor.setPower(0.5);
        frontRightMotor.setPower(-0.5);
        backRightMotor.setPower(-0.5);
        sleep(timeMs);
        stopMotors();
    }

    // Helper function to stop all motors
    private void stopMotors() {
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }
    
    private void moveArmToPosition(int targetPosition) {
    armMotor.setTargetPosition(targetPosition);
    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    armMotor.setPower(0.5); // Adjust the power as needed

    // Wait until the arm reaches the target position
    while (opModeIsActive() && armMotor.isBusy()) {
        telemetry.addData("Arm Position", armMotor.getCurrentPosition());
        telemetry.addData("Target Position", targetPosition);
        telemetry.update();
    }
}
    private void moveVSToPosition(int targetPosition) {
    // Set target position for the viper slide motor
    viperSlideMotor.setTargetPosition(targetPosition);
    viperSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    viperSlideMotor.setPower(0.5); // Adjust the power as needed

    // Wait until the viper slide motor reaches the target position
    while (opModeIsActive() && viperSlideMotor.isBusy()) {
        telemetry.addData("Viper Slide Position", viperSlideMotor.getCurrentPosition());
        telemetry.addData("Target Position", targetPosition);
        telemetry.update();
    }
}
    private void controlIntake(String inOrOut, long timeMs) {
    double intakePower = 0.0;

    // Determine intake direction
    if (inOrOut.equals("out")) {
        intakePower = 0.3; // Set power to 1 for intake
    } else if (inOrOut.equals("in")) {
        intakePower = -0.3; // Set power to -1 for outtake
    }

    intake.setPower(intakePower);

    // Run the intake for the specified duration
    //sleep(timeMs);

    // Stop the intake motor after the duration
    //intake.setPower(0.0);
}



}
