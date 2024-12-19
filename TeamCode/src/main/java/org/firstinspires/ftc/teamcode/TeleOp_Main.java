package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "TeleOp_Main", group = "Robot")
public class TeleOp_Main extends OpMode {
    public Servo servo_wrist, servo_claw;
    public DcMotor BL, BR, FR, FL, SL, SR;
    public boolean Is_Claw_Down, Is_Claw_Clamp = false;
    public boolean wasAButtonPressed, wasBButtonPressed;

    @Override
    public void init() {
        // Initialize motors
        BL = hardwareMap.get(DcMotor.class, "BL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        FR = hardwareMap.get(DcMotor.class, "FR");
        FL = hardwareMap.get(DcMotor.class, "FL");
        SL = hardwareMap.get(DcMotor.class, "slide_left_motor");
        SR = hardwareMap.get(DcMotor.class, "slide_right_motor");
        servo_claw = hardwareMap.get(Servo.class, "Uhm idk");
        servo_wrist = hardwareMap.get(Servo.class, "Uhm idk");

        BL.setDirection(DcMotor.Direction.FORWARD);
        BR.setDirection(DcMotor.Direction.FORWARD);
        FR.setDirection(DcMotor.Direction.FORWARD);
        FL.setDirection(DcMotor.Direction.FORWARD);
        SL.setDirection(DcMotor.Direction.FORWARD);
        SR.setDirection(DcMotor.Direction.FORWARD);
        servo_wrist.setDirection(Servo.Direction.FORWARD);
        servo_claw.setDirection(Servo.Direction.FORWARD);

        telemetry.addData("Status", "Initialized Yupi");
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

        //Movement and Wheels
        if (gamepad1.dpad_up) {
            setMotors(1, 1, 1, 1);  // forward
        } else if (gamepad1.dpad_down) {
            setMotors(-1, -1, -1, -1);  // down
        } else if (gamepad1.dpad_left) {
            setMotors(-1, 1, -1, 1);  // left
        } else if (gamepad1.dpad_right) {
            setMotors(1, -1, 1, -1);  // right
        } else {
            setMotors(0, 0, 0, 0);
        }

        if(gamepad1.left_bumper) {
            setMotors(-1,1,1,-1);
        }

        if(gamepad1.right_bumper) {
            setMotors(1,-1,-1,1);
        }


        //Claw and Wrist
        if (gamepad1.a && !wasAButtonPressed) {
            Is_Claw_Down = !Is_Claw_Down;
            wasAButtonPressed = true;
        } else if (!gamepad1.a) {
            wasAButtonPressed = false;
        }


        if (gamepad1.b && !wasBButtonPressed) {
            Is_Claw_Clamp = !Is_Claw_Clamp;
            wasBButtonPressed = true;
        } else if (!gamepad1.b) {
            wasBButtonPressed = false;
        }

        // Check claw
        if (Is_Claw_Down) {
            servo_wrist.setPosition(0.5);
        } else {
            servo_wrist.setPosition(0.0);
        }

        if (Is_Claw_Clamp) {
            servo_claw.setPosition(1.0);
        } else {
            servo_claw.setPosition(0.0);
        }

        //Slides
        SL.setPower(gamepad1.right_stick_y);
        SR.setPower(gamepad1.right_stick_y);

    }

    @Override
    public void stop() {
        setMotors(0,0,0,0);
        SL.setPower(0);
        SR.setPower(0);

    }

    public void setMotors(int fl, int fr, int br, int bl) {
        FL.setPower(fl);
        FR.setPower(fr);
        BR.setPower(br);
        BL.setPower(bl);
    }
}
