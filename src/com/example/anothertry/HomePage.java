package com.example.anothertry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class HomePage extends Activity{
	/*====================================================
	 * DECLARATION OF ON_CLICK EVENT *
	 ===================================================*/
	OnClickListener click = new OnClickListener()
	{
		@Override
		public void onClick(View arg0) 
		{
			Intent intent = new Intent(HomePage.this,ExplainPage.class);
			switch(arg0.getId())
			{
			case R.id.option1:
				intent.putExtra("gamemode", 1);
				startActivity(intent);
				break;
			case R.id.option2:
				intent.putExtra("gamemode", 2);
				startActivity(intent);
				break;
			case R.id.option3:
				intent.putExtra("gamemode", 3);
				startActivity(intent);
				break;
			case R.id.exit:
				finish();
				break;
			}
		}
	};
	/*====================================================
	 * SYSTEM METHODS *
	 ===================================================*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);
		TextView text;
		text = (TextView) findViewById(R.id.option1);text.setOnClickListener(click);
		text = (TextView) findViewById(R.id.option2);text.setOnClickListener(click);
		text = (TextView) findViewById(R.id.option3);text.setOnClickListener(click);
		text = (TextView) findViewById(R.id.exit);text.setOnClickListener(click);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
