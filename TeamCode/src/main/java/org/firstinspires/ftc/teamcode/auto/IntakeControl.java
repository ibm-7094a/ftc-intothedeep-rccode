package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.hardware.CRServo;

public class IntakeControl {
    private CRServo intake; // Assuming the intake is a Motor object

    // Constructor to initialize the intake motor
    public IntakeControl(CRServo intake) {
        this.intake = intake;
    }

    // Method to control intake direction and power
    public void controlIntake(String inOrOut, long timeMs) {
        double intakePower = 0.0;

        // Determine intake direction
        if (inOrOut.equals("out")) {
            intakePower = 0.3; // Set power to 0.3 for outtake
        } else if (inOrOut.equals("in")) {
            intakePower = -0.3; // Set power to -0.3 for intake
        }

        intake.setPower(intakePower);

        // Optionally, use sleep to run intake for the specified duration
        try {
            Thread.sleep(timeMs);  // Sleep for the specified time in milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stop the intake motor after the duration
        intake.setPower(0.0);
    }

    // Method to run the intake in (for external use)
    public void intakeIn(long timeMs) {
        controlIntake("in", timeMs);
    }

    // Method to run the intake out (for external use)
    public void intakeOut(long timeMs) {
        controlIntake("out", timeMs);
    }

    // Method to stop the intake (for external use)
    public void stopIntake() {
        intake.setPower(0.0);
    }
}
