package com.example.anothertry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ScorePage extends Activity {
	/*====================================================
	 * GLOBAL VARIABLES *
	 ===================================================*/
	int score = 0;
	int gamemode = 0;
	String [] negatives = 
		{
			"Even my Grandma got higher score.",
			"It's the best you can accomplish?",
			"Well. This is awkward.",
			"Are you serious?",
			"Are you even trying?",
			"Still better than disabled 10-year-old though.",
			"You are not leaving it this low, are you?",
			"You could have done better.",
			"Don't show it to anyone. Ok?",
			"You are a disgrace to mankind.",
			"What a shame.",
			"I can forgive you that.",
			"Few more points and you would beat my dog's score."
		};
	String [] positives = 
		{
			"I am proud of you.",
			"You showed who's boss!",
			"You beat it, mate!",
			"Keep up the good work.",
			"That is impressing.",
			"What a score! Wow!",
			"You did great! C'mon now, stop blushing.",
			"I didn't know you can get score THIS high.",
			"You are a very gifted and promising child.",
			"You were born to play this game.",
			"Truly remarkable!",
			"This score made me speechless.",
			"You keep surprising me."
		};
	/*====================================================
	 * MAIN METHODS *
	 ===================================================*/
	public void GetHome(View view)
	{
		Intent intent = new Intent(getApplicationContext(), HomePage.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	public void PlayAgain(View view)
	{
		Intent intent = new Intent(getApplicationContext(), Main.class);
		intent.putExtra("gamemode", gamemode);
		startActivity(intent);
	}
	/*====================================================
	 * SYSTEM METHODS *
	 ===================================================*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scorepage);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		TextView text = (TextView) findViewById(R.id.textView3);
		score = getIntent().getIntExtra("score",0);
		gamemode = getIntent().getIntExtra("gamemode",0);
		if(gamemode == 2)
		{
			text.setText(negatives[(int)(Math.random()*13)]);
		}
		else
		{
			text.setText(positives[(int)(Math.random()*13)]);
		}
		text = (TextView) findViewById(R.id.textView2);
		text.setText(Integer.toString(score));
		return true;
	}
}
