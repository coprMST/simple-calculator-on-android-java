package com.example.newcalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {

    TextView operationField;
    TextView operationField2;
    String Operation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        operationField = findViewById(R.id.operationField);

    }

    public void goToAdd(View view) {

        Button button = (Button)view;
        String bt = button.getText().toString();

        if (Operation.equals("Ошибка")) {
            Operation = "";
        }

        switch (bt) {

            case "D":
                Operation = "";
                break;
            case "C":
                if (!Operation.equals(""))
                    Operation = Operation.substring(0, Operation.length() - 1);
                break;
            case "×":
                UpdateAction("*");
                break;
            case "÷":
                UpdateAction("/");
                break;
            case ",":
                UpdateAction(".");
                break;
            default:
                UpdateAction(bt);
                break;
        }

        operationField.setText(Operation);
    }

    public void UpdateAction (String act) {
        if (act.equals("+") || act.equals("-") || act.equals("*") || act.equals("/") || act.equals("%")) {
            if (!Operation.endsWith("-") && !Operation.endsWith("+") && !Operation.endsWith(".") && !Operation.endsWith("*") && !Operation.endsWith("/") && !Operation.endsWith("%") && !Operation.equals("")) {
                Operation += act;
            }
        }
        else if (act.equals("(")) {
            if (Operation.equals("") || Operation.endsWith("-") || Operation.endsWith("+") || Operation.endsWith("*") || Operation.endsWith("%") || Operation.endsWith("/") || Operation.endsWith("(")){
                Operation += act;
            }
        }
        else if (act.equals(".")) {
            char[] strToArray = Operation.toCharArray();
            for (int i = strToArray.length - 1; i >= 0; i--) {
                String bob = Character.toString(strToArray[i]);
                if (bob.equals(".")) {
                    return;
                }
                else if (i == 0 || bob.equals("+") || bob.equals("-") || bob.equals("*") || bob.equals("%") || bob.equals("/") || bob.equals("(")) {
                    Operation += act;
                    return;
                }
            }
        }
        else if (act.equals(")")) {

            int countRight = 0;
            int countLeft = 0;
            char[] strToArray = Operation.toCharArray();

            for (char sym : strToArray) {
                String bob = Character.toString(sym);
                if (bob.equals(")"))
                    countRight++;
            }

            for (char sym : strToArray) {
                String bob = Character.toString(sym);
                if (bob.equals("("))
                    countLeft++;
            }

            if (countLeft > countRight)
                if (Operation.endsWith("1") || Operation.endsWith("2") || Operation.endsWith("3") || Operation.endsWith("4") || Operation.endsWith("5") || Operation.endsWith("6") || Operation.endsWith("7") || Operation.endsWith("8") || Operation.endsWith("9") || Operation.endsWith("0") || Operation.endsWith(")"))
                    Operation += act;
        }
        else if (act.equals("1") || act.equals("2") || act.equals("3") || act.equals("4") || act.equals("5") || act.equals("6") || act.equals("7") || act.equals("8") || act.equals("9") || act.equals("0")){
            if (Operation.endsWith("1") || Operation.endsWith("2") || Operation.endsWith("3") || Operation.endsWith("4") || Operation.endsWith("5") || Operation.endsWith("6") || Operation.endsWith("7") || Operation.endsWith("8") || Operation.endsWith("9") || Operation.endsWith("0") || Operation.endsWith("-") || Operation.endsWith("+") || Operation.endsWith(".") || Operation.endsWith("*") || Operation.endsWith("/") || Operation.endsWith("%") || Operation.equals("") || Operation.equals("("))
                Operation += act;
        }
        else if (act.equals("P") || act.equals("G") || act.equals("e")) {
            if (Operation.equals("") || Operation.endsWith("-") || Operation.endsWith("+") || Operation.endsWith("*") || Operation.endsWith("/") || Operation.endsWith("("))
                Operation += act;
        }
    }

    @SuppressLint("SetTextI18n")
    public void goToResult(View view) {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");

        if (Operation.equals(""))
            return;

        try {
            if (Operation.endsWith("-") || Operation.endsWith("+") || Operation.endsWith(".") || Operation.endsWith("*") || Operation.endsWith("/") || Operation.endsWith("%") || Operation.equals("")) {
                Operation = Operation.substring(0, Operation.length() - 1);
            }

            Operation = Operation.replace("P", "3.14");

            Operation = Operation.replace("G", "0.91");

            Operation = Operation.replace("e", "2.71");

            int countRight = 0;
            int countLeft = 0;
            char[] strToArray = Operation.toCharArray();

            for (char sym : strToArray) {
                String bob = Character.toString(sym);
                if (bob.equals(")"))
                    countRight++;
            }

            for (char sym : strToArray) {
                String bob = Character.toString(sym);
                if (bob.equals("("))
                    countLeft++;
            }

            int diff = countLeft - countRight;

            for (int i = 0; i < diff; i++)
                Operation += ")";


            Object result = engine.eval(Operation);
            Operation = result.toString();

            if (Operation.equals("Infinity") || Operation.equals("NaN"))
                Operation = "Ошибка";

            if (Operation.endsWith(".0")) {
                Operation = Operation.substring(0, Operation.length() - 2);
            }
        }
        catch (ScriptException e) {
            Operation = "Ошибка";
        }

        operationField.setText(Operation);
    }
}