package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name = "Test_Motors", group = "Utilities")
public class Test_Motors extends LinearOpMode {

    public DcMotor BL, BR, FR, FL, SL, SR;
    public Servo servo_wrist_l,servo_wrist_r, servo_claw;


    @Override
    public void runOpMode() {


        BL = hardwareMap.get(DcMotor.class, "BL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        FR = hardwareMap.get(DcMotor.class, "FR");
        FL = hardwareMap.get(DcMotor.class, "FL");
        SR = hardwareMap.get(DcMotor.class, "slide_right");
        SL = hardwareMap.get(DcMotor.class, "slide_left");
        servo_claw = hardwareMap.get(Servo.class, "Uhm idk");
        servo_wrist_l = hardwareMap.get(Servo.class, "dsad");
        servo_wrist_r = hardwareMap.get(Servo.class, "Uhm idk");

        BL.setDirection(DcMotor.Direction.FORWARD);
        BR.setDirection(DcMotor.Direction.FORWARD);
        FR.setDirection(DcMotor.Direction.FORWARD);
        FL.setDirection(DcMotor.Direction.FORWARD);
        SL.setDirection(DcMotor.Direction.FORWARD);
        SR.setDirection(DcMotor.Direction.FORWARD);
        servo_wrist_l.setDirection(Servo.Direction.FORWARD);
        servo_wrist_r.setDirection(Servo.Direction.REVERSE);
        servo_claw.setDirection(Servo.Direction.FORWARD);


        FL.setPower(1.0);
        sleep(2000);
        FL.setPower(0);
        FR.setPower(1.0);
        sleep(2000);
        FR.setPower(0);
        BR.setPower(1.0);
        sleep(2000);
        BR.setPower(0);
        BL.setPower(1.0);
        sleep(2000);
        BL.setPower(0);
        SL.setPower(.1);
        SR.setPower(.1);
        sleep(1000);
        SL.setPower(0);
        SR.setPower(0);
        servo_wrist_l.setPosition(.2);
        servo_wrist_r.setPosition(.2);
        sleep(4000);
        servo_wrist_l.setPosition(0);
        servo_wrist_r.setPosition(0);
        servo_claw.setPosition(.2); // POSITIVE CLOSE
        sleep(3000);
        servo_claw.setPosition(0);


    }
}

