package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TeleOpMain extends LinearOpMode {

    final double ARM_TICKS_PER_DEGREE = 28 * 250047.0 / 4913.0 * 100.0 / 20.0 * 1 / 360.0;
    final double ARM_COLLAPSED_INTO_ROBOT = 0;
    double armPosition = ARM_COLLAPSED_INTO_ROBOT;
    
    final double SLIDE_COLLAPSED_INTO_ROBOT = 0;
    double slidePosition = SLIDE_COLLAPSED_INTO_ROBOT;

    // Motor and servo dependencies
    public DcMotor frontLeftMotor = null;
    public DcMotor backLeftMotor = null;
    public DcMotor frontRightMotor = null;
    public DcMotor backRightMotor = null;
    public DcMotor armMotor = null;
    public DcMotor viperSlideMotor = null;
    public CRServo intake = null;

    final int MAX_VIPER_SLIDE_POSITION = 2040;  // Max position for the viper slide
    final int MIN_VIPER_SLIDE_POSITION = 0;     // Min position for the viper slide

    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize hardware
        frontLeftMotor = hardwareMap.dcMotor.get("drive0");
        backLeftMotor = hardwareMap.dcMotor.get("drive1");
        frontRightMotor = hardwareMap.dcMotor.get("drive2");
        backRightMotor = hardwareMap.dcMotor.get("drive3");
        armMotor = hardwareMap.dcMotor.get("arm0");
        viperSlideMotor = hardwareMap.dcMotor.get("VSM0");
        intake = hardwareMap.get(CRServo.class, "serv1");

        // Set motor directions
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Set zero power behavior to brake
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Initialize motor encoders
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        viperSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperSlideMotor.setTargetPosition(0);
        viperSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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
            double dy = gamepad2.right_stick_y;
            if (Math.abs(dy) > 0.2) {
                armPosition += dy * 10;
                armPosition = Math.max(-2130, armPosition);
            }

            // Set arm's target position
            armMotor.setTargetPosition((int) armPosition);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armMotor.setPower(0.5); 

            // Intake control
            if (gamepad2.y) {
                intake.setPower(1.0);
            } else if (gamepad2.x) {
                intake.setPower(-1.0);
            } else {
                intake.setPower(0.0);
            }

            // ViperSlide control using right and left triggers
            if (gamepad2.right_trigger > 0) {
                slidePosition += 10; // Move upwards
            } else if (gamepad2.left_trigger > 0) {
                slidePosition -= 10; // Move downwards
            }

            // Ensure the slide position is within the allowed range
            slidePosition = Math.max(MIN_VIPER_SLIDE_POSITION, Math.min(MAX_VIPER_SLIDE_POSITION, slidePosition));

            // Set viperSlide's target position to hold it in place
            viperSlideMotor.setTargetPosition((int) slidePosition);
            viperSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            
            // Reduce power based on proximity to target position to avoid overdriving
            int distanceToTarget = Math.abs(viperSlideMotor.getTargetPosition() - viperSlideMotor.getCurrentPosition());
            double power = 0.5;
            if (distanceToTarget < 50) {
                power = 0.2;  // Reduce power as we approach the target
            }
            viperSlideMotor.setPower(power);

            // Telemetry feedback
            telemetry.addData("Arm Position", armMotor.getCurrentPosition());
            telemetry.addData("Arm Target Position", armMotor.getTargetPosition());
            telemetry.addData("ViperSlide Position", viperSlideMotor.getCurrentPosition());
            telemetry.addData("ViperSlide Target", viperSlideMotor.getTargetPosition());
            telemetry.update();
        }
    }
}
