package com.example.multicontacts;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

class VueDessin extends View {
	private MotionEvent event=null ;
	Typeface font;
	int iInit =0;  
	static final String operateurs[]={"?","+","-","*","/"};
	int valeurs[]={0,0,0};
	int indice=0;
	Builder builder;
	boolean move=false;
	private Paint paint;
	private boolean end= false;
	private boolean opsaisi =false;
	
	public VueDessin(Context context) {
        super(context);
        // Il faut que la police soit présente dans le dossier assets, la telecharger et la mettre si nécessaire avec un simple glisser
        font = Typeface.createFromAsset(context.getAssets(), "arial.ttf");
        builder = new Builder(this.getContext());
        builder.setMessage("test");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new ListenerOK());
        paint = new Paint();  
    }

    protected void onDraw(Canvas canvas) {
    	int x,y,i;
    	canvas.drawRGB(0, 0, 0);
                   	
       	if (event !=null && !end)
       	{
                                
        	paint.setTypeface(font);
            paint.setColor(Color.YELLOW);
            paint.setTextSize(150);
            paint.setTextAlign(Paint.Align.CENTER);
            int corrAction = event.getAction()& MotionEvent.ACTION_MASK;
            // Recuperons le nombre de doigts en contact sur l'ecran
       		int nbCount = event.getPointerCount();
            switch (corrAction) {
    	        case MotionEvent.ACTION_DOWN:
    	            break;
    	        case MotionEvent.ACTION_POINTER_DOWN:
    	            if( !move){
    	            	if(!opsaisi){
	    	       			valeurs[1]=nbCount;
	    	       			indice=2; 
	    	       			opsaisi=true;
    	            	}
    	       		}
    	            break;
    	        case MotionEvent.ACTION_MOVE:
    	        	if(nbCount==2 && move){
    	        		builder.setMessage(valeurs[0]+ " " + operateurs[valeurs[1]] + " " + valeurs[2]+" = " +eval());
    	        		builder.create().show();
    	        		end =true;
    	        	}
    	        	else
    	        		//if(event.getSize()>)
    	        		move=true;
    	            break;
    	        case MotionEvent.ACTION_UP:
    	            if(move)
    	            	valeurs[indice]++;
    	            else{
    	            	if(valeurs[1]<nbCount && !opsaisi)
    	       			valeurs[1]=nbCount;
    	       			indice=2;    	       	
    	       		}
    	            move=false;
    	            break;
    	        case MotionEvent.ACTION_POINTER_UP:
    	            break;
            }
            
			if (corrAction != MotionEvent.ACTION_MOVE)
           		iInit = (event.getAction()& MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
       		Log.i("MultiTouch", "Nb. Contact:" + nbCount + ", Pointer Index:" + iInit);
       		
       		canvas.drawText(valeurs[0]+ " " + operateurs[valeurs[1]] + " " + valeurs[2], canvas.getWidth() / 2,160, paint);	
           	for (i=0; i< nbCount; i++)
           	{
           		paint.setARGB(255,255, 255, 255);
	        	// Puis dessiner nos points dans le canevas           		
               	x = (int)event.getX(i);
               	y = (int)event.getY(i);
               	//canvas.drawText("X:" + x + " , Y:" + y, canvas.getWidth() / 2, 100, paint);
           		canvas.drawCircle(x-50, y-50, 50, paint);
           	}
       	}
    }
    
    private String eval()
    {
    	int result=0;
    	switch (valeurs[1]) 
    	{
	    	case 0:
				return "??";
			case 1:
				result=valeurs[0]+valeurs[2];
				break;
			case 2:
				result=valeurs[0]-valeurs[2];
				break;
			case 3:
				result=valeurs[0]*valeurs[2];
				break;
			case 4:
				if(valeurs[2]==0)
					return "Impossible";
				result=valeurs[0]/valeurs[2];
				break;
		}
    	
    	return result+"";
    }
    
	public void setEvent(MotionEvent event) 
	{
		this.event = event;
	}
}
