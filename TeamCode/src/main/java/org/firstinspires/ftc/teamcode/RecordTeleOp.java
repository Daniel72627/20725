package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "RecordTeleOp", group = "Robot")
public class RecordTeleOp extends OpMode {

    public DcMotor BL, BR, FR, FL, SL, SR;
    public boolean d_up, d_down, d_left, d_right, a_pressed, last_a_state;
    public Servo SWL,SWR,SC;
    boolean a_pressed = false;  // Keep track of whether the button was toggled or not
    boolean last_a_state = false;
    private List<InputLog> logData;
    private long startTime;

    @Override
    public void init() {
        // Initialize motors
        BL = hardwareMap.get(DcMotor.class, "BL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        FR = hardwareMap.get(DcMotor.class, "FR");
        FL = hardwareMap.get(DcMotor.class, "FL");

        SL = hardwareMap.get(DcMotor.class, "slide_left_motor");
        SR = hardwareMap.get(DcMotor.class, "slide_right_motor");

        SWL = hardwareMap.get(Servo.class, "dsad");
        SWR = hardwareMap.get(Servo.class, "Uhm idk");
        SC = hardwareMap.get(Servo.class, "Uhm idk");

        BL.setDirection(DcMotor.Direction.FORWARD);
        BR.setDirection(DcMotor.Direction.FORWARD);
        FR.setDirection(DcMotor.Direction.FORWARD);
        FL.setDirection(DcMotor.Direction.FORWARD);

        SL.setDirection(DcMotor.Direction.FORWARD);
        SR.setDirection(DcMotor.Direction.FORWARD);

        SWL.setDirection(Servo.Direction.FORWARD);
        SWR.setDirection(Servo.Direction.REVERSE);
        SC.setDirection(Servo.Direction.FORWARD);


        logData = new ArrayList<>();
        telemetry.addData("Status", "Initialized Yupi");
    }

    @Override
    public void start() {
        // Record start time for timestamp calculation
        startTime = System.currentTimeMillis();
    }

    @Override
    public void loop() {

        d_up = gamepad1.dpad_up;
        d_down = gamepad1.dpad_down;
        d_right = gamepad1.dpad_right;
        d_left = gamepad1.dpad_left;
        boolean current_a_state = gamepad1.a;


        if (d_up) {
            setMotors(1, 1, 1, 1);  // forward
        } else if (d_down) {
            setMotors(-1, -1, -1, -1);  // down
        } else if (d_left) {
            setMotors(-1, 1, -1, 1);  // left
        } else if (d_right) {
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

        SL.setPower(gamepad1.right_stick_y);
        SR.setPower(gamepad1.right_stick_y);

        if(gamepad1.right_trigger > .3) {
            SWL.setPosition(.2);
            SWR.setPosition(.2);
        }

        if(gamepad1.left_trigger > .3) {
            SWL.setPosition(-.2);
            SWR.setPosition(-.2);
        }

        if (current_a_state && !last_a_state) {  // If the A button was just pressed
            a_pressed = !a_pressed;  // Toggle the arm state

            // Toggle the arm based on the value of a_pressed
            if (a_pressed) {
                SC.setPosition(0.2);  // Open the arm
            } else {
                SC.setPosition(-0.2);  // Close the arm
            }
        }

        // Update last_a_state to the current state of the A button for next loop
        last_a_state = current_a_state;

        //---------------------------------------------------------LEAVE THIS ALONE
        double BLPower = BL.getPower();
        double BRPower = BR.getPower();
        double FRPower = FR.getPower();
        double FLPower = FL.getPower();

        double SLPower = SL.getPower();
        double SRPower = SR.getPower();

        double SWLPos = SWL.getPosition();
        double SWRPos = SWR.getPosition();
        double SCPos = SC.getPosition();

        InputLog log = new InputLog();
        log.timestamp = System.currentTimeMillis() - startTime;

        log.BLPower = BLPower;
        log.BRPower = BRPower;
        log.FRPower = FRPower;
        log.FLPower = FLPower;

        log.SLPower = SLPower;
        log.SRPower = SRPower;

        log.SWLPos = SWLPos;
        log.SWRPos = SWRPos;
        log.SCPos = SCPos;

        logData.add(log);

        telemetry.addData("Recording", "Timestamp: %d ms", log.timestamp);
        telemetry.addData("BL Power", BLPower);
        telemetry.addData("BR Power", BRPower);
        telemetry.addData("FR Power", FRPower);
        telemetry.addData("FL Power", FLPower);
        telemetry.addData("SL Power", SLPower);
        telemetry.addData("SR Power", SRPower);
        telemetry.addData("SWL Power", SWLPos);
        telemetry.addData("SWR Power", SWRPos);
        telemetry.addData("SC", SCPos);
        telemetry.update();
    }

    @Override
    public void stop() {
        // Write the logs to a plain text file when recording stops
        File directory = new File("/sdcard/FIRST"); // "/sdcard/FIRST" maybe idk

        if(!directory.exists()) {
            directory.mkdirs();
        }

        File filePath = new File(directory, "log_file.txt");


        try (FileWriter writer = new FileWriter(filePath)) {
            for (InputLog log : logData) {
                writer.write(String.format("Timestamp: %d, BLPower: %.2f, BRPower: %.2f, FRPower: %.2f, FLPower: %.2f, SLPower: %.2f, SRPower: %.2f\n, SWLPos: %.2f\n, SWRPos: %.2f\n, SCPos: %.2f\n",
                        log.timestamp, log.BLPower, log.BRPower, log.FRPower, log.FLPower, log.SLPower, log.SRPower, log.SWLPos, log.SWRPos, log.SCPos));
            }
            telemetry.addData("Status: ", "Recording saved to %s", filePath);
        } catch (IOException e) {
            telemetry.addData("Error: ", "Failed Recording" + e.getMessage());
        }
        telemetry.update();
    }

    // Helper class to store input log entries
    private static class InputLog {
        long timestamp;
        double BLPower;
        double BRPower;
        double FRPower;
        double FLPower;
        double SLPower;
        double SRPower;
        double SWLPos;
        double SWRPos;
        double SCPos;
    }

    public void setMotors(int fl, int fr, int br, int bl) {
        FL.setPower(fl);
        FR.setPower(fr);
        BR.setPower(br);
        BL.setPower(bl);
    }
}
