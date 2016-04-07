package androidcourse.staytuned;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import java.lang.Object;

public class MainActivity extends AppCompatActivity {
    //Add interstinial Ad object, note that this uses java.lang.Object
    /*private InterstinialAd mInterstinialAd;*/

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
