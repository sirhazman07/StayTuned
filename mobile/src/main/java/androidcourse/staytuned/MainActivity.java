package androidcourse.staytuned;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.lang.Object;

public class MainActivity extends AppCompatActivity {
    //Add interstinial Ad object, note that this uses java.lang.Object
    /*private InterstinialAd mInterstinialAd;*/
    private TextSwitcher mSwitcher;
    //Adding the array for the textswitcher
    String textToShow[] = {"main HeadLIne", "Your Message","New in Technology", "New Articles"};
    int messageCounter = textToShow.length;
    // to keep current index of text
    int currentIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(),"Welcome", Toast.LENGTH_LONG).show();

        /*AdView mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("YOUR_DEVICE_HASH")
                .build();
        mAdView.loadAd(adRequest);*/
        //didn't work so removed it
        //final Uri latinaUri = Uri.parse(extras.getString("http://www.latina.pe/tvenvivo/"));
        //TODO: get reference for the textSwitcher and set the change status(not streaming, streaming to device)
        //Get the button(from the menu)
        ImageButton button= (ImageButton) findViewById(R.id.imageButtonAmerica);
        mSwitcher = (TextSwitcher) findViewById(R.id.textSwitcherStreamingStatus);
        //set the view factory of the TextSwitcher that will create Textview object when asked
        mSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView myText = new TextView(MainActivity.this);
                myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                myText.setTextSize(30);
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
        btnNext.setonClickListener(new view.OnClickListener(){

            public void onCLick(View v){
                //TODO: Auto-generated method stub
                currentIndex++;
                //If index reaches maximum reset it
                if (currentIndex== messageCount)
                    currentIndex=0;
                mSwitcher.setText(textToShow[currentIndex]);
            }
        });

        //TODO: NEW CHANNEL LATINA TV
        //TODO: get the ImageButton and pass the url
        final String latinaUrl = new String("http://www.latina.pe/tvenvivo/");
        //create reference to ImageButton
        ImageButton buttonLatina = (ImageButton) findViewById(R.id.imageButtonLatina);
        //set listener
        //TODO:Set Listener to handle click event
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
        //TODO: NEW CHANNEL AMERICA TV

        final String americaUrl = new String("http://www.latina.pe/tvenvivo/");
        //create reference to ImageButton
        ImageButton buttonAmerica = (ImageButton) findViewById(R.id.imageButtonAmerica);
        //set listener
        //TODO:Set Listener to handle click event
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
