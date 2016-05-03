package androidcourse.staytuned;

import android.app.Activity;
import android.app.MediaRouteActionProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaRouter;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.media.MediaRouteSelector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.IOException;
import java.lang.Object;
import java.text.SimpleDateFormat;
import java.util.Date;

//Note to always check MainActivity extends AppCompact when using Cast(Sender and Receiver)(on by default)
public class MainActivity extends AppCompatActivity {

    private TextSwitcher mSwitcher;
    //action listener for the new button, Video Path string and Request for video count
    private String currentVideoPath;
    static final int REQUEST_TAKE_VIDEO = 1;
    //Menu with Items
    private MenuItem item;
    //Adding the array for the TextSwitcher
    String textToShow[] = {"Status: Not Streaming", "Status: Streaming to Device..."};
    //also add the menu button to trigger the switch on the TextSwitcher
    private ImageButton streamButton;
    int messageCount = textToShow.length;
    // to keep current index of text
    int currentIndex = -1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMediaROuter = MediaRouter.getInstance(getApplicationContext());
        Toast.makeText(getApplicationContext(),"Welcome", Toast.LENGTH_LONG).show();
        //TODO: add casting (Sender)
        mMediaRouteSelector = new MediaRouteSelector.Builder()
                .addControlCategory(CastMediaControlIntent.categoryForCast("YOUR_APPLICATION_ID"))
                .build();



        //TODO: get reference for the textSwitcher and set the change status(not streaming, streaming to device) + DONE
        //TODO: fix this so it talks to the menu item button (startStreaming) for streaming and updates the listener below (btnNext) DONE
        //Get the switch(from the menu)
        ImageButton streamButton= (ImageButton) findViewById(R.id.media_route_menu_item);
        mSwitcher = (TextSwitcher) findViewById(R.id.textSwitcherStreamingStatus);
        //set the view factory of the TextSwitcher that will create TextView object when asked
        mSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                //NOTE IMPORTANT: Text switcher is an empty container in which we will create a new textView and pass Set values(an array etc).Yai!
                TextView myText = new TextView(MainActivity.this);
                myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                myText.setTextSize(30);
                //Get the text and change the background for each result
                String getText = myText.getText().toString();
                if (getText == "Status: Streaming to Device..."){

                    myText.setBackgroundColor(Color.GREEN);
                } else
                {
                    myText.setBackgroundColor(Color.RED);
                }
                myText.setAllCaps(true);
                myText.setTextColor(Color.WHITE);
                return myText;
            }
        });
        //Declare the in and out animations and initialize them
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        //set the animation type of the textSwitcher
        mSwitcher.setInAnimation(in);
        mSwitcher.setOutAnimation(out);

        //ClickListener for NEXT button
        //When click on Button TextSwitcher will switch between texts set above
        //The Current Text will go OUT and the next text will come in with specified animation
        //TODO: fix this and assign to menu icon (start_stream) click event DONE
        //Assert the button exists on the view Activity
        assert streamButton != null;
        streamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Auto-generated method stub DONE
                currentIndex++;
                //If index reaches maximum reset it
                if (currentIndex== messageCount)
                    currentIndex=0;
                mSwitcher.setText(textToShow[currentIndex]);
            }
        });

        //TODO: NEW CHANNEL LATINA TV DONE- NEEDS DIRECT REPOSITORY
        //TODO: get the ImageButton and pass the url
        final String latinaUrl = new String("http://www.latina.pe/tvenvivo/");
        //create reference to ImageButton
        ImageButton buttonLatina = (ImageButton) findViewById(R.id.imageButtonLatina);
        //set listener
        //TODO:Set Listener to handle click event
        //if the button exists in the Menu
        if (buttonLatina != null) {
            buttonLatina.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ChannelView.class);
                    //TODO: pass URI to the live stream
                    intent.setData(Uri.parse(latinaUrl));
                    //put extra didn't work so removed it
                    //intent.putExtra("http://www.latina.pe/tvenvivo/",latinaUri);
                    startActivity(intent);
                    //Uneccessary Toast? Not
                    Toast.makeText(MainActivity.this, "Frequencia Latina", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //TODO: NEW CHANNEL AMERICA TV

        final String americaUrl = new String("http://www.latina.pe/tvenvivo/");
        //create reference to ImageButton
        ImageButton buttonAmerica = (ImageButton) findViewById(R.id.imageButtonAmerica);
        //set listener
        //TODO:Set Listener to handle click event
        if (buttonAmerica != null) {
            buttonAmerica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,ChannelView.class);
                    //TODO: pass URI to the live stream
                    intent.setData(Uri.parse(americaUrl));
                    //put extra didn't work so removed it
                    //intent.putExtra("http://www.latina.pe/tvenvivo/",latinaUri);
                    startActivity(intent);
                    //Uneccessary Toast? think Not
                    Toast.makeText(MainActivity.this, "America Television", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.OnStart();
        mMediaRouter.addCalback(mMediaRouteSelector , mMediaRouterCallback,
                MediaRouter.CALLBACK_FLAG_REQUEST_DISCOVERY);
    }

    @Override
    protected void onStop() {
        mMediaRouter.removeCallback(mMediaRouterCallback);
        super.onStop();
    }
    Cast.CastOptions.Builder apiOptionsBuilder = Cast.CastOptions
            .builder(mSelectedDevice, mCastClientListener);

    mApiClient = new GoogleApiClient.Builder(this)
            .addApi(Cast.API, apiOptionsBuilder.build())
            .addConnectionCallbacks(mConnectionCallbacks)
    .addOnConnectionFailedListener(mConnectionFailedListener)
    .build();


    //TODO:Creates the Options Menu
    //Also assign mediaRouteSelector to MediaRouteActionProvider in the action Bar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.streaming_menu,menu);
        MenuItem mediaRouteMenuItem = menu.findItem(R.id.media_route_menu_item);
        MediaRouteActionProvider mediaRouteActionProvider =
                (MediaRouteActionProvider) MenuItemCompat.getActionProvider(mediaRouteMenuItem);
        mediaRouteActionProvider.setRouteSelector(mMediaRouteSelector);
        return true;
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_TAKE_VIDEO && resultCode == RESULT_OK){
            //disable video icon
            item.setVisible(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.select_device) {
            Intent returnHomeIntent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(returnHomeIntent);
            finish();
        }
        else if (item.getItemId() == R.id.return_home){
            Intent returnHomeIntent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(returnHomeIntent);
            finish();
        }
        else if (item.getItemId() == R.id.record_screen){
            Intent takeVideoIntent = new Intent (MediaStore.ACTION_VIDEO_CAPTURE);
            //Ensure that there's a camera activity to handle the intent
            if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                //Create the File where the video should go
                File videoFile = null;
                try {
                    Toast.makeText(MainActivity.this, "Recording Started", Toast.LENGTH_SHORT).show();
                     videoFile = createVideoFile();
                } catch (IOException ex){
                    //Error occurred while creating the File
                    Toast.makeText(MainActivity.this, "Error occurred while creating the Video File",
                            Toast.LENGTH_SHORT).show();
                }
                //Continue only if the File was successfully created
                if ( videoFile != null){
                    takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile( videoFile));
                    startActivityForResult(takeVideoIntent, REQUEST_TAKE_VIDEO);
                }
            }
        }
        else if (item.getItemId() == R.id.return_home){
            Intent returnHomeIntent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(returnHomeIntent);
            finish();
        }
        return true;
    }

    //TODO: Create and Name Video File (to Local Storage)
    private File createVideoFile () throws IOException{
        //create an image FIle name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String videoFileName = "MP4_" + timeStamp + "_";
        //getExternalFilesDir makes photos to remain private to your app only
        File storageDir = getExternalFilesDir(null);
        File video = File.createTempFile(
                videoFileName, /* [prefix */
                "mp4",          /* suffix  */
                storageDir      /* directory */
        );
        // Save the file: path for use with ACTION_VIEW intents
        currentVideoPath = video.getAbsolutePath();
        //NOTE: Maybe make a void because you still want to continue viewing the stream
        return video;

        //TODO: MEdia Router CallBack
        private class MyMediaRouterCallback extends MediaRouter.Callback {

            @Override
            public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo info) {
                mSelectedDevice = CastDevice.getFromBundle(info.getExtras());
                String routeId = info.getId();

            }

            @Override
            public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo info) {
                teardown();
                mSelectedDevice = null;
            }
        }
    }

}
