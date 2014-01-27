package com.example.calculator;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener,
		OnLongClickListener {
	private StringBuffer text;
	private TextView textInput;
	private TextView textAnswer;
	ArrayList<Item> items = new ArrayList<Item>();
	List<Button> buttonList = new ArrayList<Button>();
	Button d0, d1, d2, d3, d4, d5, d6, d7, d8, d9;
	Button buttonDiv, buttonMply, buttonMinus, OP, dDecPoint, buttonEq, CP,
			buttonPlus, buttonCancel, buttonTan, buttonHistory;

	@Override
	protected final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textInput = (TextView) findViewById(R.id.inputText);
		textAnswer = (TextView) findViewById(R.id.answerText);
		text = new StringBuffer();
		// for (int i = 0; i < 40; i++) {
		// Button btn = new Button(this);
		// btn.setTag(i);
		//
		// btn.setOnClickListener((OnClickListener) this);
		// buttonList.add(btn);
		// }
		d0 = (Button) findViewById(R.id.button0);
		d1 = (Button) findViewById(R.id.button1);
		d2 = (Button) findViewById(R.id.button2);
		d3 = (Button) findViewById(R.id.button3);
		d4 = (Button) findViewById(R.id.button4);
		d5 = (Button) findViewById(R.id.button5);
		d6 = (Button) findViewById(R.id.button6);
		d7 = (Button) findViewById(R.id.button7);
		d8 = (Button) findViewById(R.id.button8);
		d9 = (Button) findViewById(R.id.button9);
		buttonDiv = (Button) findViewById(R.id.buttonDiv);
		buttonMply = (Button) findViewById(R.id.buttonMply);
		buttonMinus = (Button) findViewById(R.id.buttonMinus);
		OP = (Button) findViewById(R.id.buttonOP);
		dDecPoint = (Button) findViewById(R.id.buttonPt);
		buttonEq = (Button) findViewById(R.id.buttonEq);
		CP = (Button) findViewById(R.id.buttonCP);
		buttonPlus = (Button) findViewById(R.id.buttonPlus);
		buttonCancel = (Button) findViewById(R.id.buttonCancel);
		buttonTan = (Button) findViewById(R.id.buttonTan);
		buttonHistory = (Button) findViewById(R.id.buttonHistory);
		d0.setOnClickListener(this);
		d1.setOnClickListener(this);
		d2.setOnClickListener(this);
		d3.setOnClickListener(this);
		d4.setOnClickListener(this);
		d5.setOnClickListener(this);
		d6.setOnClickListener(this);
		d7.setOnClickListener(this);
		d8.setOnClickListener(this);
		d9.setOnClickListener(this);
		buttonDiv.setOnClickListener(this);
		buttonMply.setOnClickListener(this);
		buttonMinus.setOnClickListener(this);
		OP.setOnClickListener(this);
		dDecPoint.setOnClickListener(this);
		buttonEq.setOnClickListener(this);
		CP.setOnClickListener(this);
		buttonPlus.setOnClickListener(this);
		buttonCancel.setOnClickListener(this);
		buttonTan.setOnClickListener(this);
		buttonCancel.setOnLongClickListener(this);
		buttonHistory.setOnClickListener(this);
	}

	@Override
	public final void onClick(final View v) {
		// input operation
		if (text.length() > 0
				&& !isOperation(text.substring(text.length() - 1))
				&& !text.substring(text.length() - 1).equals(
						buttonTan.getText())) {
			switch (v.getId()) {
			case R.id.buttonPt:
				text.append(dDecPoint.getText());
				break;
			case R.id.buttonDiv:
				text.append(buttonDiv.getText());
				break;
			case R.id.buttonMply:
				text.append(buttonMply.getText());
				break;
			case R.id.buttonMinus:
				text.append(buttonMinus.getText());
				break;
			case R.id.buttonPlus:
				text.append(buttonPlus.getText());
				break;
			default:
				break;
			}
		}
		// input number
		if (text.length() <= 20) {
			switch (v.getId()) {
			case R.id.button0:
				text.append(d0.getText());
				break;
			case R.id.button1:
				text.append(d1.getText());
				break;
			case R.id.button2:
				text.append(d2.getText());
				break;
			case R.id.button3:
				text.append(d3.getText());
				break;
			case R.id.button4:
				text.append(d4.getText());
				break;
			case R.id.button5:
				text.append(d5.getText());
				break;
			case R.id.button6:
				text.append(d6.getText());
				break;
			case R.id.button7:
				text.append(d7.getText());
				break;
			case R.id.button8:
				text.append(d8.getText());
				break;
			case R.id.button9:
				text.append(d9.getText());
				break;
			case R.id.buttonOP:
				if (text.length() == 0) {
					text.append(OP.getText());
				} else if (isOperation(text.substring(text.length() - 1))
						& !text.substring(text.length() - 1).equals(
								CP.getText())) {
					text.append(OP.getText());
				}
				break;
			case R.id.buttonCP:
				if (text.length() >= 2) {
					if (!isOperation(text.substring(text.length() - 1))
							& !text.substring(text.length() - 1).equals(
									OP.getText())
							& !text.substring(text.length() - 1).equals(
									buttonTan.getText())) {
						text.append(CP.getText());
					}
				}
				break;
			case R.id.buttonTan:
				if (text.length() == 0) {
					text.append(buttonTan.getText());
				} else if (isOperation(text.substring(text.length() - 1))
						&& !text.substring(text.length() - 1).equals(
								buttonTan.getText())) {
					text.append(buttonTan.getText());
				}
				break;
			default:
				break;
			}
		}

		textInput.setText(text);
		// calculate or clear
		if (v.getId() == R.id.buttonEq) {
			textAnswer.setText(Calculator.calculate(text.toString()));

			Item item = new Item();

			item.setEquation(text.toString());
			item.setResult(Double.valueOf(textAnswer.getText().toString()));

			items.add(item);

			text = new StringBuffer();
			textInput.setText(text);

		} else if (v.getId() == R.id.buttonHistory) {
			Intent intent = new Intent(this, History.class);

			intent.putExtra("items", items);
			this.startActivity(intent);
		} else if (v.getId() == R.id.buttonCancel) {
			if (text.length() > 0) {
				text.deleteCharAt(text.length() - 1);
			}
		}
		textInput.setText(text);
	}

	@Override
	public final boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public final boolean onLongClick(final View v) {
		if (v.getId() == R.id.buttonCancel) {
			text = new StringBuffer();
			textInput.setText(text);
			textAnswer.setText(text);
			return true;
		}
		return false;
	}

	private boolean isOperation(final String symdol) {
		return (symdol.equals("/") || symdol.equals("+") || symdol.equals("-") || symdol
				.equals("*"));
	}
}
