package com.example.anothertry;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ExplainPage extends Activity {
	/*====================================================
	 * GLOBAL VARIABLES *
	 ===================================================*/
	int gamemode = 0;
	Drawable []basics;
	int [] allTheImageId = 
		{
			R.id.imageView1,
			R.id.imageView2,
			R.id.imageView3,
			R.id.imageView4,
			R.id.imageView5,
			R.id.imageView6
		};
	int [] allTheTextId = 
		{
			R.id.textView1,
			R.id.textView2,
			R.id.textView3,
			R.id.textView4,
			R.id.textView5,
			R.id.textView6
		};
	String [] descrips = 
		{
			"Sapphire - normal amount of points",
			"Emerald - gives as one above",
			"Diamond - does pretty much nothing",
			"Amethyst - this one does even less",
			"Ruby - should I continue saying this?",
			"Amber - i think you have already understood the rules",
			
			"Heniu - po u³o¿eniu trójki, jeden zawsze zostaje",
			"Adamska - powiêksza mno¿nik punktów o 3",
			"Dziêba - odnawia ca³kowicie czas, ale sprawia, ¿e szybciej ucieka",
			"Siwka - dodaje punkty oraz +1 spot na Kika",
			"Kiku - dodaje tyle % obecnych punktów ile ma obecnie spotów",
			"Paula - nie robi nic specjalnego, ale by³oby g³upio gdyby jej nie by³o",
			
			"Zasady",
			"Tak",
			"Jak",
			"W",
			"Trybie",
			"Casual"
		};
	/*====================================================
	 * MAIN METHODS *
	 ===================================================*/
	public void GetBack(View view)
	{
		Intent intent = new Intent(getApplicationContext(), HomePage.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	public void StartTheGame(View view)
	{
		Intent intent = new Intent(ExplainPage.this,Main.class);
		intent.putExtra("gamemode", gamemode);
		startActivity(intent);
	}
	/*====================================================
	 * SYSTEM METHODS *
	 ===================================================*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.explainpage);
		gamemode = getIntent().getIntExtra("gamemode", 0);
		Resources base_res = getResources();
		switch(gamemode)
		{
			case 0:
				finish();
				break;
			case 1: 
				Drawable []temp1 = {
						base_res.getDrawable(R.drawable.jewel0),
						base_res.getDrawable(R.drawable.jewel1),
						base_res.getDrawable(R.drawable.jewel2),
						base_res.getDrawable(R.drawable.jewel3),
						base_res.getDrawable(R.drawable.jewel4),
						base_res.getDrawable(R.drawable.jewel5)
				};
				basics = temp1;
				break;
			case 2: 
				Drawable []temp2 = {
						base_res.getDrawable(R.drawable.school0),
						base_res.getDrawable(R.drawable.school1),
						base_res.getDrawable(R.drawable.school2),
						base_res.getDrawable(R.drawable.school3),
						base_res.getDrawable(R.drawable.school4),
						base_res.getDrawable(R.drawable.school5)
				};
				basics = temp2;
				break;
			case 3: 
				Drawable []temp3 = {
						base_res.getDrawable(R.drawable.school0),
						base_res.getDrawable(R.drawable.school1),
						base_res.getDrawable(R.drawable.school2),
						base_res.getDrawable(R.drawable.school3),
						base_res.getDrawable(R.drawable.school4),
						base_res.getDrawable(R.drawable.school5)
				};
				basics = temp3;
				break;
		
		}
		ImageView image;
		TextView text;
		for(int i=0; i<6; i++)
		{
			text = (TextView) findViewById(allTheTextId[i]);
			text.setText(descrips[i+((gamemode-1)*6)]);
			image = (ImageView) findViewById(allTheImageId[i]);
			image.setImageDrawable(basics[i]);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
