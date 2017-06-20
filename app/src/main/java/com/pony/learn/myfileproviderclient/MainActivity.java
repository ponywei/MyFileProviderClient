package com.pony.learn.myfileproviderclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
	private ImageView imageView;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setAction("com.pony.learn.myfileproviderdemo.CAPTURE");
				startActivityForResult(intent, 110);
			}
		});

		imageView = (ImageView) findViewById(R.id.imageView);
		textView = (TextView) findViewById(R.id.textView);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			Toast.makeText(this, "nothing.", Toast.LENGTH_SHORT).show();
			return;
		}

		if (requestCode == 110 && data != null) {
			try {
				// 通过Uri读取对应的图片文件
				Uri imageUri = data.getData();
				ParcelFileDescriptor fileDescriptor = getContentResolver().openFileDescriptor(imageUri, "r");
				assert fileDescriptor != null;
				Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor());
//				FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());

				imageView.setImageBitmap(image);
				textView.setText(imageUri.toString());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
