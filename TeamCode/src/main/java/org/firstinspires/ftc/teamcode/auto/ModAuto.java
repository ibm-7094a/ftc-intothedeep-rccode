package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.misc.RobotHardware;
import com.qualcomm.robotcore.hardware.CRServo;

@Autonomous(name = "Modular Auto", group = "Autonomous")
public class ModAuto extends LinearOpMode {

    private RobotHardware robotHardware;
    private ArmSlideMovement armSlideMovement;
    private LinearMovement linearMovement;
    private ComplexMovement complexMovement;
    private IntakeControl intakeControl;  // Declare IntakeControl

    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize hardware
        robotHardware = new RobotHardware();
        robotHardware.init(hardwareMap);

        // Initialize IntakeControl with the CRServo for the intake motor
        CRServo intakeMotor = robotHardware.intake; // Assuming you have an intakeMotor in RobotHardware
        intakeControl = new IntakeControl(intakeMotor); // Initialize intake control

        // Wait for the start signal
        telemetry.addLine("AutoInsurance Initialized!");
        telemetry.update();
        waitForStart();

// In runOpMode() method:

// Initialize movement classes with hardware from RobotHardware
armSlideMovement = new ArmSlideMovement(robotHardware.armMotor, robotHardware.viperSlideMotor);
linearMovement = new LinearMovement(robotHardware.frontLeftMotor, robotHardware.backLeftMotor, 
                                    robotHardware.frontRightMotor, robotHardware.backRightMotor);
complexMovement = new ComplexMovement(robotHardware.frontLeftMotor, robotHardware.backLeftMotor, 
                                      robotHardware.frontRightMotor, robotHardware.backRightMotor, this);

armSlideMovement.resetSlide(); // Call it on the instance of ArmSlideMovement
linearMovement.moveForward(300);
sleep(200); // softer on the brake mode
complexMovement.strafeLeft(650);
sleep(200);
complexMovement.turnLeft(1050);
sleep(200);
// linearMovement.moveForward(50);
telemetry.addLine("NAV to basket OK");

// Functional scoring below, DO NOT EDIT
armSlideMovement.moveArmToPosition(2100);
sleep(100);
armSlideMovement.moveVSToPosition(1850);
sleep(100);
intakeControl.intakeOut(2000);
sleep(100);
telemetry.addLine("SCORE 1 in basket OK");

// Clear memory (experimental: remove if test goes bad)
System.gc(); 
telemetry.addLine("Memory Cleared.");
// EDIT: no, the flipper does NOT do sh!t.

armSlideMovement.moveArmToPosition(2200);
sleep(100);
armSlideMovement.runVSToZero(); // timeout if necessary
sleep(100);
armSlideMovement.moveArmToPosition(550);
sleep(100);
telemetry.addLine("SCORE cleanup OK");

// Clear memory again (experimental: remove if test goes bad)
//System.gc();
//telemetry.addLine("Memory Cleared.");
//this code was all for the sake of solving a hardware problem, it did not work. ignore.

complexMovement.turnRight(400);
sleep(100);
linearMovement.moveBackward(500);
sleep(100);
complexMovement.strafeRight(925);
telemetry.addLine("NAV to sample2 OK");

// Prevent literal crashing
sleep(30000);

telemetry.addLine("Autonomous complete.");
telemetry.update();

    }
}
