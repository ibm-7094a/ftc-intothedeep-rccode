package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "MecTeleOp", group = "TeleOp")
public class MecTeleOp extends LinearOpMode {

    final double ARM_TICKS_PER_DEGREE = 28 * 250047.0 / 4913.0 * 100.0 / 20.0 * 1 / 360.0;
    final double ARM_COLLAPSED_INTO_ROBOT = 0;
    double armPosition = ARM_COLLAPSED_INTO_ROBOT;
    final double SLIDE_COLLAPSED_INTO_ROBOT = 0;
    double slidePosition = SLIDE_COLLAPSED_INTO_ROBOT;

    // New variable for viper slide position
    double viperSlidePosition = 0;

    public CRServo intake = null;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize motors and servos
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("drive0");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("drive1");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("drive2");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("drive3");
        DcMotor armMotor = hardwareMap.dcMotor.get("arm0");
        DcMotor viperSlideMotor = hardwareMap.dcMotor.get("VSM0");
        intake = hardwareMap.get(CRServo.class, "serv1");

        // Set motor directions
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        viperSlideMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Set all motors to brake when power is zero
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Initialize arm motor encoder
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Wait for the start signal
        telemetry.addLine("Sigmus Prime Rollout!");
        telemetry.update();
        waitForStart();
        
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            // Drive control
            double y = gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x * 1.1;
            double rx = -gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            frontLeftMotor.setPower((y + x + rx) / denominator);
            backLeftMotor.setPower((y - x + rx) / denominator);
            frontRightMotor.setPower((y - x - rx) / denominator);
            backRightMotor.setPower((y + x - rx) / denominator);

            // Arm control using gamepad2.right_stick_y
            double dy = gamepad2.right_stick_y; // Use right stick Y for arm movement
            if (Math.abs(dy) > 0.2) {
                armPosition += dy * 10; // Adjust speed (number) for arm movement
                armPosition = Math.max(-2130, armPosition); // Limit to a minimum value
                
            }

            // Set the arm's target position and run it to that position
            armMotor.setTargetPosition((int) armPosition);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(0.5); // Adjust power number (speed value) based on needs

            // Intake control
            if (gamepad2.y) {
                intake.setPower(1.0);
            } else if (gamepad2.x) {
                intake.setPower(-1.0);
            } else {
                intake.setPower(0.0);
            }

            // Adjust the position with left_trigger (up) and right_trigger (down)
            double triggerInput = gamepad2.left_trigger - gamepad2.right_trigger; // Right trigger for up, Left trigger for down

            if (Math.abs(triggerInput) > 0.2) {
                slidePosition += triggerInput * 10; // Adjust speed (number) for slide movement
                slidePosition = Math.max(-2000, slidePosition); // Limit to a minimum value
                }

            // Set the slide's target position and run it to that position
            viperSlideMotor.setTargetPosition((int) slidePosition);
            viperSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            viperSlideMotor.setPower(0.5); // Adjust power number (speed value) based on needs



            // Telemetry feedback
            telemetry.addData("Arm Position", armMotor.getCurrentPosition());
            telemetry.addData("Arm Target Position", armMotor.getTargetPosition());
            telemetry.addData("Viper Slide Position", viperSlidePosition);
            telemetry.update();
        }
    }
}
