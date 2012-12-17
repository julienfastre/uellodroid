package org.progracqteur.uellodroid.activities;

import org.progracqteur.uellodroid.R;
import org.progracqteur.uellodroid.R.layout;
import org.progracqteur.uellodroid.R.menu;
import org.progracqteur.uellodroid.activities.LoginActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//redirect to LoginActivity if no user is registered
		SharedPreferences sharedPrefs = this.getPreferences(Context.MODE_PRIVATE);
		String login = sharedPrefs.getString(LoginActivity.LOGIN_KEY, null);
		if (login == null) {
			Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(intent);
		}
			
		
		loginButton = (Button) findViewById(R.id.main_login_button);
		loginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(intent);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
