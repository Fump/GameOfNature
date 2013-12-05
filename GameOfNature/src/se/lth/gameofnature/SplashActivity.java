package se.lth.gameofnature;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;


public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		VideoView videoView = (VideoView) findViewById(R.id.videoView1);

	    String vidpath = "android.resource://" + getPackageName() + "/" + R.raw.testintro;
	    videoView.setVideoURI(Uri.parse(vidpath));
	    videoView.requestFocus();
	    
		Thread splash_screen=new Thread(){

			public void run(){
				try{
					sleep(7000);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					startActivity(new Intent(getApplicationContext(),StartActivity.class));
					finish();
				}
			}
		};
		videoView.start();
		splash_screen.start();
	}
}