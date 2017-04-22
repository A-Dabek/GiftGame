package com.example.anothertry;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Main extends Activity {

	/*====================================================
	 * DECLARATION OF CLASS *
	 ===================================================*/
	public class Tile{
		float primaryX;
		float primaryY;
		int ID;
		int order;
		public void setTile(int xID, int xorder)
		{
			ID = Integer.valueOf(xID);
			order = Integer.valueOf(xorder);
			ImageView image = (ImageView) findViewById(xID);
			primaryX = image.getX();
			primaryY = image.getY();
		}
		public void RefreshCoords()
		{
			ImageView image = (ImageView) findViewById(ID);
			primaryX = image.getX();
			primaryY = image.getY();
		}
		public void copyTile(Tile rand)
		{
			ID = rand.ID;
			order = rand.order;
			primaryX = rand.primaryX;
			primaryY = rand.primaryY;
		}
		public int getID()
		{
			return Integer.valueOf(ID);
		}
		public int getOrder()
		{
			return Integer.valueOf(order);
		}
		public ImageView getImageView()
		{
			return (ImageView) findViewById(ID);
		}
	}
	public class Que
	{
		ImageView image;
		float postX;
		float postY;
		public Que(ImageView im, float x, float y)
		{
			image = im;
			postX = Float.valueOf(x);
			postY = Float.valueOf(y);
		}
	}
	/*====================================================
	 * SUPPORTING METHODS *
	 ===================================================*/
	private int getTileById(int ID)
	{
		for(int aa=0; aa<horNR*verNR; aa++)
		{
			if(tileSet[aa].ID == ID){return aa;}
		}
		return -1;
	}
	private int getTileByOrder(int order)
	{
		for(int aa=0; aa<horNR*verNR; aa++)
		{
			if(tileSet[aa].order == order){return aa;}
		}
		return -1;
	}
	private boolean checkQueIfDone(int ID)
	{
		if(mainQue.get(ID).postX == tileSet[ID].getImageView().getX() && mainQue.get(ID).postY == tileSet[ID].getImageView().getY())
		{
			tileSet[ID].RefreshCoords();
			return true;
		}
		return false;
	}
	public int getDrawableID(Drawable draw)
	{
		for(int i=0; i<6; i++)
		{
			if(basics[i]==draw)return i;
		}
		return -1;
	}
	/*====================================================
	 * INITIALIZATION OF VARIABLE *
	 ===================================================*/
	int spotNR = -1;
	int multiplier = -1;
	int previoustime = 0;
	int score = 0;
	int Mtime = 0;
	int time = 0;
	///////////////////////////////////////////////////////
	final int horNR = 5;
	final int verNR = 5;
	float priPOSx = -1;
	float priPOSy = -1;
	int gamemode = 0;
	boolean gameHasStarted = false;
	boolean allSetUp = false;
	boolean release = false;
	boolean [] blockVERT = new boolean[verNR];
	boolean [] blockHOR = new boolean[horNR];
	int [] theIDs = 
		{
			R.id.ImageView00,
			R.id.ImageView01,
			R.id.ImageView02,
			R.id.ImageView03,
			R.id.ImageView04,
			
			R.id.ImageView10,
			R.id.ImageView11,
			R.id.ImageView12,
			R.id.ImageView13,
			R.id.ImageView14,
			
			R.id.ImageView20,
			R.id.ImageView21,
			R.id.ImageView22,
			R.id.ImageView23,
			R.id.ImageView24,
			
			R.id.ImageView30,
			R.id.ImageView31,
			R.id.ImageView32,
			R.id.ImageView33,
			R.id.ImageView34,
			
			R.id.ImageView40,
			R.id.ImageView41,
			R.id.ImageView42,
			R.id.ImageView43,
			R.id.ImageView44,
		};
	Tile tileSet[] = new Tile[horNR*verNR];
	Drawable basics[];
	ArrayList<Que>mainQue = new ArrayList<Que>(0);
	private Handler handle = new Handler();
	private Runnable runthis = new Runnable()
	{
		@Override
		public void run()
		{
			if(!allSetUp){setTileSet(); allSetUp=true;}
			if(mainQue.size()==0){setQue();}
			if(gameHasStarted)Check();
			Refill();
			ProgressBar PGB2 = (ProgressBar) findViewById(R.id.progress);
			PGB2.setMax(Mtime);
			time -=1;
			PGB2.setProgress(time);
			if(PGB2.getProgress()==0)
			{
				Intent intent = new Intent(Main.this,ScorePage.class);
				intent.putExtra("score", score);
				intent.putExtra("gamemode", gamemode);
				startActivity(intent);
			}
			else{handle.postDelayed(this,100);}
		}
	};
	/*====================================================
	 * DECLARATION OF ON_TOUCH EVENT *
	 ===================================================*/
	OnTouchListener move = new OnTouchListener()
	{
		@Override
		public boolean onTouch(View view, MotionEvent event)
		{
			gameHasStarted = true;
			final float X = (int) event.getRawX();
		    final float Y = (int) event.getRawY();
		    if(priPOSx == -1 || priPOSy==-1){priPOSx=X; priPOSy=Y;}
		    float diffrenceX = X-priPOSx;
		    float diffrenceY = Y-priPOSy;
		    boolean movement = false;
		    
		    switch (event.getAction() & MotionEvent.ACTION_MASK) {
		        case MotionEvent.ACTION_MOVE:
		        	Move(view.getId(),diffrenceX,diffrenceY);
		        	movement = true;
		        	break;
		    }
		    if(!movement){priPOSx=-1; priPOSy=-1; release=true;}
			return true;
		}
	};
	/*====================================================
	 * PROGRAM INITIALIZATION METHODS *
	 ===================================================*/
	public void setTileSet()
	{
		for(int i=0, ord=0; i<horNR*verNR; i++)
		{
			tileSet[i] = new Tile();
			tileSet[i].setTile(theIDs[i], ord);
			ord+=1;
			if(ord%10==5){ord+=5;}
		}
	}
	public void setQue()
	{
		for(int i=0; i<verNR*horNR; i++)
		{
			ImageView temp = tileSet[i].getImageView();
			mainQue.add(new Que(temp,temp.getX(),temp.getY()));
		}
	}
	/*====================================================
	 * MAIN METHODS *
	 ===================================================*/
	private void Move(int ID, float dX, float dY)
	{
		ImageView active = (ImageView) findViewById(ID); 
		ImageView passive = (ImageView) findViewById(ID);;
		Tile present = tileSet[getTileById(ID)];
		if(Math.abs(dX)>3*Math.abs(dY) && Math.abs(dX)<=active.getWidth())
		{
			if(dX>0 && present.order/10<horNR-1)
			{
				passive = (ImageView) findViewById(tileSet[getTileByOrder(present.order+10)].ID);
			}
			else if(dX<0 && present.order/10>0)
			{
				passive = (ImageView) findViewById(tileSet[getTileByOrder(present.order-10)].ID);
			}
		}
		else if(Math.abs(dY)>3*Math.abs(dX) && Math.abs(dY)<=active.getHeight())
		{
			if(dY>0 && present.order%10<verNR-1)
			{
				passive = (ImageView) findViewById(tileSet[getTileByOrder(present.order+1)].ID);
			}
			else if(dY<0 && present.order%10>0)
			{
				passive = (ImageView) findViewById(tileSet[getTileByOrder(present.order-1)].ID);
			}		
		}
		if(active!=passive && release) {release = false; Swap(getTileById(active.getId()),getTileById(passive.getId()));}
	}
	private void Swap(int tile1, int tile2)
	{
		ImageView first = (ImageView) findViewById(tileSet[tile1].ID);
		ImageView second = (ImageView) findViewById(tileSet[tile2].ID);
		float ax=tileSet[tile1].primaryX,
			  ay=tileSet[tile1].primaryY,
			  bx=tileSet[tile2].primaryX,
			  by=tileSet[tile2].primaryY;
		
	if(ax!=bx ^ ay!=by)
	{
		int aID=first.getId(),
			bID=second.getId(),
			aorder=tileSet[tile1].getOrder(),
			border=tileSet[tile2].getOrder();
			
		tileSet[tile1].setTile(bID, aorder);
		tileSet[tile2].setTile(aID, border);
		tileSet[tile1].RefreshCoords();
		tileSet[tile2].RefreshCoords();
		
		first.animate().x(bx);
		first.animate().y(by);
		second.animate().x(ax);
		second.animate().y(ay);
		
		mainQue.remove(tile1);
		mainQue.add(tile1,new Que(second, ax, ay));
		mainQue.remove(tile2);
		mainQue.add(tile2,new Que(first, bx, by));
		}
	}
	private void Check()
	{
		for(int b=0; b<horNR*verNR; b+=2)
		{
		for(; (b%horNR)<horNR-2; b+=1)
			{
				Drawable temp = tileSet[b].getImageView().getDrawable();
				if(checkQueIfDone(b) && checkQueIfDone(b+1) && checkQueIfDone(b+2))
				{
					if(tileSet[b+1].getImageView().getDrawable()==temp && tileSet[b+2].getImageView().getDrawable()==temp)
					{
						tileSet[b].getImageView().setVisibility(View.INVISIBLE);
						tileSet[b+1].getImageView().setVisibility(View.INVISIBLE);
						tileSet[b+2].getImageView().setVisibility(View.INVISIBLE);
						if(gamemode==2)
						{
							int presentID=getDrawableID(temp);
							switch(presentID)
							{
								case 0:
									ImageView img = tileSet[b+2].getImageView();
									img.setVisibility(View.VISIBLE);
									img.setImageDrawable(basics[0]);
									break;
								case 1:
									multiplier+=3;
									break;
								case 2:
									time=Mtime;
									Mtime-=25;
									break;
								case 3:
									spotNR+=1;
									break;
								case 4:
									score += (score*spotNR/100);
									spotNR=0;
									break;
								case 5:
									break;
							}
						}
						ProgressBar PGB2 = (ProgressBar) findViewById(R.id.progress);
						score += 3*multiplier;
						if(Math.abs(previoustime-PGB2.getProgress())<15){multiplier+=1;}
						else {multiplier = 1;}
						previoustime = PGB2.getProgress();
						time+=3;

					}
				}
			}
		}
		for(int b=0; b<horNR; b+=1)
		{
			for(; (b/horNR)<horNR-2; b+=verNR)
			{
				Drawable temp = tileSet[b].getImageView().getDrawable();
				if(checkQueIfDone(b) && checkQueIfDone(b+verNR) && checkQueIfDone(b+2*verNR))
				{
					if(tileSet[b+verNR].getImageView().getDrawable()==temp && tileSet[b+2*verNR].getImageView().getDrawable()==temp)
					{
						tileSet[b].getImageView().setVisibility(View.INVISIBLE);
						tileSet[b+verNR].getImageView().setVisibility(View.INVISIBLE);
						tileSet[b+2*verNR].getImageView().setVisibility(View.INVISIBLE);
						if(gamemode==2)
						{
							int presentID=getDrawableID(temp);
							switch(presentID)
							{
								case 0:
									ImageView img = tileSet[b].getImageView();
									img.setVisibility(View.VISIBLE);
									img.setImageDrawable(basics[0]);
									break;
								case 1:
									multiplier+=3;
									break;
								case 2:
									time=Mtime;
									Mtime-=25;
									break;
								case 3:
									spotNR+=1;
									break;
								case 4:
									score += (score*spotNR/100);
									spotNR=0;
									break;
								case 5:
									break;
							}
						}
						ProgressBar PGB2 = (ProgressBar) findViewById(R.id.progress);
						score += 3*multiplier;
						if(Math.abs(previoustime-PGB2.getProgress())<15){multiplier+=1;}
						else {multiplier = 1;}
						previoustime = PGB2.getProgress();
						time+=3;
					}
				}
			}
			b=b%horNR;
		}
		TextView text = (TextView) findViewById(R.id.points);
		text.setText(Integer.toString(score));
		text = (TextView) findViewById(R.id.spots);
		text.setText("(+"+Integer.toString(spotNR)+")");
		text = (TextView) findViewById(R.id.multiplier);
		text.setText("(x"+Integer.toString(multiplier)+")");
	}
	private void Refill()
	{
		ImageView image, temp;
		for(int i=verNR*horNR-1; i>=0;i--)
		{
			image = tileSet[i].getImageView();
			if(image.getVisibility()==View.INVISIBLE)
			{
				if(i%verNR==0)
				{
					if(checkQueIfDone(i))
					{
						image.setVisibility(View.VISIBLE);
						image.setImageDrawable(basics[5-(int)(Math.random()*6)]);
					}
				}
				else
				{
					for(int g=1; g<=(i%verNR); g++)
					{
						temp = tileSet[i-g].getImageView();
						if(checkQueIfDone(i-g) && checkQueIfDone(i))
						{
							if(temp.getVisibility()==View.VISIBLE)
							{
								Swap(i,i-g); continue;
							}
							else if(g==i%verNR)
							{
								temp.setVisibility(View.VISIBLE);
								temp.setImageDrawable(basics[5-(int)(Math.random()*6)]);
								Swap(i,i-g);continue;
							}
						}
					}
				}
			}
		}
	}
	public void Debug(View view)
	{
		priPOSx=-1;
		priPOSy=-1;
		gameHasStarted=false;
		allSetUp=false;
		mainQue.clear();
		setContentView(R.layout.main);
		Resources base_res = getResources();
		TextView text = (TextView) findViewById(R.id.spots);		
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
				text.setVisibility(View.INVISIBLE);
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
				text.setVisibility(View.VISIBLE);
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
				text.setVisibility(View.INVISIBLE);
				break;
		}
		ProgressBar PGB2 = (ProgressBar) findViewById(R.id.progress);
		PGB2.setMax(Mtime);
		PGB2.getProgressDrawable().setColorFilter(Color.RED, Mode.SRC_IN);
		ImageView image;
		for(int i=0; i<verNR*horNR; i++)
		{
			image = (ImageView) findViewById(theIDs[i]);image.setImageDrawable(basics[(int)(Math.random()*5)]);image.setOnTouchListener(move);
		}
		setTileSet();
	}
	public void Back(View view)
	{
		Intent intent = new Intent(getApplicationContext(), HomePage.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	/*====================================================
	 * SYSTEM METHODS *
	 ===================================================*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gamemode = getIntent().getIntExtra("gamemode", 0);
		if(gamemode == 2)
		{
			Mtime=1500;
			spotNR=0;
		}
		else
		{
			Mtime=1000;
			spotNR=-1;
		}
		multiplier = 1;
		score = 0;
		time = Mtime;
		Debug(null);
		handle.postDelayed(runthis, 100);
		ProgressBar PGB2 = (ProgressBar) findViewById(R.id.progress);
		PGB2.setMax(Mtime);
		PGB2.setProgress(Mtime);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
