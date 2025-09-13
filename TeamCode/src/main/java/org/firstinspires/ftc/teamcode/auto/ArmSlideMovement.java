package org.firstinspires.ftc.teamcode.auto;

// Import necessary hardware libraries
import com.qualcomm.robotcore.hardware.DcMotor;

public class ArmSlideMovement {

    // Declare hardware variables
    private DcMotor armMotor;
    private DcMotor viperSlideMotor;

    final double ARM_TICKS_PER_DEGREE = 28 * 250047.0 / 4913.0 * 100.0 / 20.0 * 1 / 360.0;
    final double ARM_COLLAPSED_INTO_ROBOT = 0;
    double armPosition = ARM_COLLAPSED_INTO_ROBOT;

    // Constructor to initialize the motors
    public ArmSlideMovement(DcMotor armMotor, DcMotor viperSlideMotor) {
        this.armMotor = armMotor;
        this.viperSlideMotor = viperSlideMotor;

        // Initialize motor behaviors
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // Function to move the arm to a specific target position
    public void moveArmToPosition(int targetPosition) {
        armMotor.setTargetPosition(targetPosition);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(0.5); // Adjust the power as needed

        // Wait until the arm reaches the target position
        while (armMotor.isBusy()) {
            // Wait until arm reaches target
        }
    }

    // Function to move the viper slide to a specific target position
    // Function to move the viper slide to a specific target position
public void moveVSToPosition(int targetPosition) {
    viperSlideMotor.setTargetPosition(targetPosition);
    viperSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    viperSlideMotor.setPower(0.5); // Adjust the power as needed

    long startTime = System.currentTimeMillis();  // Record the start time

    // Wait until the viper slide motor reaches the target position or timeout occurs
    while (viperSlideMotor.isBusy()) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        
        // Check if 2100 ms have passed
        if (elapsedTime > 2100) {
            viperSlideMotor.setPower(0); // Stop the motor after timeout
            break; // Exit the loop after timeout
        }
    }
}

public void resetSlide() {
        viperSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

public void runVSToZero() {
    viperSlideMotor.setTargetPosition(0);
    viperSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    viperSlideMotor.setPower(0.7); // Adjust the power as needed

    long startTime = System.currentTimeMillis();  // Record the start time

    // Wait until the viper slide motor reaches the target position or timeout occurs
    while (viperSlideMotor.isBusy()) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        
        // Check for timeout
        if (elapsedTime > 2000) {
            viperSlideMotor.setPower(0); // Stop the motor after timeout
            viperSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            viperSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            break; // Exit the loop after timeout
        }
    }
}


    // Function to reset the encoders of arm and viper slide motors//we basically don't use this

}
