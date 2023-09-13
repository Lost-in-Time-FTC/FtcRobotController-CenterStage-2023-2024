package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@SuppressWarnings("unused")
@TeleOp(name = "LiT Drive Program 2023-2024", group = "Linear OpMode")

public class LiTDrive extends LinearOpMode {
    private Hardware hardware;
    // Declare OpMode members
    private final ElapsedTime runtime = new ElapsedTime();
    // TouchSensor touchSensor;
//    private DcMotor frontLeftMotor = null;
//    private DcMotor frontRightMotor = null;
//    private DcMotor backLeftMotor = null;
//    private DcMotor backRightMotor = null;

    public void runOpMode() {
        Gamepad currentGamepad2 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        hardware = new Hardware(hardwareMap);

//        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
//        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
//        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
//        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");

        hardware.backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        hardware.backRightMotor.setDirection(DcMotor.Direction.REVERSE);
        hardware.frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        hardware.frontRightMotor.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            drive();
        }

    }

    public void drive() {
        // Mecanum
        double drive = gamepad1.left_stick_y;
        double turn = gamepad1.left_stick_x;
        double strafe = gamepad1.right_stick_x;

        // Strafing
        double FL = Range.clip(drive + strafe + turn, -0.5, 0.5);
        double FR = Range.clip(drive - strafe - turn, -0.5, 0.5);
        double BL = Range.clip(drive - strafe + turn, -0.5, 0.5);
        double BR = Range.clip(drive + strafe - turn, -0.5, 0.5);

        double QJSpeed = 1.75;
        double sniperPercent = 0.25;

        // Sniper mode
        if (gamepad1.left_trigger > 0) {
            hardware.frontLeftMotor.setPower(FL * QJSpeed * sniperPercent);
            hardware.frontRightMotor.setPower(FR * QJSpeed * sniperPercent);
            hardware.backLeftMotor.setPower(BL * QJSpeed * sniperPercent);
            hardware.backRightMotor.setPower(BR * QJSpeed * sniperPercent);
        }

        // Brakes
        else if (gamepad1.right_trigger > 0) {
            hardware.frontLeftMotor.setPower(FL * 0);
            hardware.frontRightMotor.setPower(FR * 0);
            hardware.backLeftMotor.setPower(BL * 0);
            hardware.backRightMotor.setPower(BR * 0);

        }
        // Normal drive
        else {
            hardware.frontLeftMotor.setPower(FL * QJSpeed);
            hardware.frontRightMotor.setPower(FR * QJSpeed);
            hardware.backLeftMotor.setPower(BL * QJSpeed);
            hardware.backRightMotor.setPower(BR * QJSpeed);
        }
    }
}