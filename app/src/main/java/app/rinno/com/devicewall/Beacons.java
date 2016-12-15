package app.rinno.com.devicewall;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.estimote.sdk.EstimoteSDK;
import com.estimote.sdk.SystemRequirementsChecker;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.rinno.com.devicewall.model.Beacon;
import app.rinno.com.devicewall.model.Dispositivo;
import app.rinno.com.devicewall.utils.ClienteConex;
import app.rinno.com.devicewall.utils.MultiScaler;
import app.rinno.com.devicewall.utils.NearableID;
import app.rinno.com.devicewall.utils.Product;
import app.rinno.com.devicewall.utils.ServerSocket;
import app.rinno.com.devicewall.utils.ShowroomManager;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Beacons extends AppCompatActivity implements MediaPlayer.OnPreparedListener, SurfaceHolder.Callback
{
    private ShowroomManager showroomManager;
    SurfaceHolder holder;
    private MediaPlayer player;
    private int item = 1;
    public static File carpetaHome = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Source/DeviceAccesorios");
    public static File carpetaVideos = new File(carpetaHome.getAbsoluteFile() + "/videos/");
    public static File carpetaBeacons = new File(carpetaHome.getAbsolutePath()+"/beacons/");
    public static File inactividad = new File(carpetaHome.getAbsolutePath()+"/inactividad/");

    Button cerrar;
    FrameLayout panel;
    ImageView img;
    ServerSocket server;
    /*ROUTE IP*/
    ArrayList<String> ipsLine;

    static String ip = "";
    static int port = 50000;
    static int input = 0;
    static int screen = 0;
    static String nameMydevice = "Minix2-P2";
    List<Dispositivo> listDispositivos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Fresco.initialize(this);
        setContentView(R.layout.activity_beacons);
        ButterKnife.bind(this);

        //<editor-fold desc="HIDE">
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        EstimoteSDK.initialize(getApplicationContext(), "gabriel-donoso-rinno-la-s--mcv", "6c74635dbdcdf891c23e9ddbf25f09a4");
        //</editor-fold>

        VideoView surface = (VideoView) findViewById(R.id.surface);
        surface.requestFocus();

        /*Ips Minix Hermanos*/
        /*LISTADO DISPOSITIVOS*/
        listDispositivos = new ArrayList<Dispositivo>();
        /*Dispositivo Pantalla 1*/
       /**/Dispositivo d = new Dispositivo();
        d.setIp("192.168.1.112");
        d.setIpEscalador("192.168.1.39");
        d.setName("Minix1-P1");
        d.setNumPantalla("1");
        d.setOutput("2");

        Beacon b1 = new Beacon();
        b1.setId("69d7bc9c4e588390"); //Perro
        b1.setVideo("1.mp4");

        Beacon b2 = new Beacon();
        b2.setId("030c30cabf4a0913"); //Silla
        b2.setVideo("2.mp4");

        List<Beacon> listBeacon = new ArrayList<Beacon>();
        listBeacon.add(b1);
        listBeacon.add(b2);

        d.setListBeacons(listBeacon);
        listDispositivos.add(d);

        /*Dispositivo Pantalla 2*/
        /*Dispositivo d2 = new Dispositivo();
        d2.setIp("192.168.1.110");
        d2.setIpEscalador("192.168.1.40");
        d2.setName("Minix2-P2");
        d2.setNumPantalla("3");
        d2.setOutput("4");

        Beacon b3 = new Beacon();
        b3.setId("98101929509ee771"); //Auto
        b3.setVideo("3.mp4");

        List<Beacon> listBeacon2 = new ArrayList<Beacon>();
        listBeacon2.add(b3);
        d2.setListBeacons(listBeacon2);
        listDispositivos.add(d2);*/

        //Dispositivo Pantalla 3
        /*
        Dispositivo d3 = new Dispositivo();
        d3.setIp("192.168.1.111");
        d3.setIpEscalador("192.168.1.40");
        d3.setName("Minix3-P3");
        d3.setNumPantalla("1");
        d3.setOutput("2");

        Beacon b4 = new Beacon();
        b4.setId("56b7a5ced57c04e2"); //Zapato
        b4.setVideo("4.mp4");

        Beacon b5 = new Beacon();
        b5.setId("10bf26cca280a5c9"); //Cama
        b5.setVideo("5.mp4");

        List<Beacon> listBeacon3 = new ArrayList<Beacon>();
        listBeacon3.add(b4);
        listBeacon3.add(b5);

        d3.setListBeacons(listBeacon3);
        listDispositivos.add(d3);*/


        cerrar = (Button) findViewById(R.id.btncerrar);
        panel = (FrameLayout) findViewById(R.id.panelimg);
        //img = (SimpleDraweeView) findViewById(R.id.miimg);
        img = (ImageView) findViewById(R.id.miimgview);
        //ip = (TextView) findViewById(R.id.ip);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               cerrarPanel(0);
            }
        });

        cerrarPanel(0);
        server = new ServerSocket(this);

        Map<NearableID, Product> products = new HashMap<>();
        // TODO: replace with identifiers of your own nearables//



        /*Beacons Pantalla 1*/
        //products.put(new NearableID("69d7bc9c4e588390"), new Product(2, "/1.mp4"));  //<------- AQUI PONER EL BEACONS NUEVO PARA LA PANTALLA 1 (SOLO AQUI ES NECESARIO)
        products.put(new NearableID("b173fa2ff1fa8d12"), new Product(2, "/1.mp4"));  //<------- AQUI PONER EL BEACONS NUEVO PARA LA PANTALLA 1 (SOLO AQUI ES NECESARIO)
        products.put(new NearableID("98101929509ee771"), new Product(3, "/3.mp4"));


        //<--  DEJAR ESTOS INACTIVOS. SI NECESITAS PARA LA PANTALLA 2 O 3, PUEDES ACTIVARLOS.
        //<--  EL BEACONS QUE FALTA, ES PARA LA PANTALLA 1
        //<--  NO ES NECESARIO MODIFICAR LAS IP, AUN NO TRABAJO CON ELLAS.

        /*Beacons Pantalla 2*/
        //products.put(new NearableID("56b7a5ced57c04e2"), new Product(3, "/4.mp4"));

        /*Beacons Pantalla 3*/
        //products.put(new NearableID("030c30cabf4a0913"), new Product(3, "/2.mp4"));
        //products.put(new NearableID("10bf26cca280a5c9"), new Product(4, "/5.mp4"));

        /*Registro de Beacons*/

        /*for(Dispositivo item : listDispositivos){

            for(Beacon bitem : item.getListBeacons()) {
                products.put(new NearableID(bitem.getId()), new Product(cont, bitem.getVideo()));
                cont++;
            }
        }*/

        showroomManager = new ShowroomManager(this, products);
        showroomManager.setListener(new ShowroomManager.Listener()
        {
            @Override
            public void onProductPickup(final Product product)
            {
                Log.i("Psaasaaaa","OK");

                    if (product.getId()>=1)
                    {
                        String pathFile = Beacons.carpetaBeacons.getPath() + "/" + product.getUrl();
                        Log.i("Beacons Data " , product.getId() + " - " + pathFile);

                        try {
                            video(pathFile);
                        }catch(Exception ex){
                            Log.i("Error video", "err-" + ex);
                        }

                        List<Dispositivo> listDis = new ArrayList<Dispositivo>();
                        Dispositivo d = new Dispositivo();
                        d.setIpEscalador("192.168.1.39");
                        d.setNumPantalla("1");
                        d.setReproductor("1");
                        d.setOutput("2");
                        listDis.add(d);

                        Dispositivo d2 = new Dispositivo();
                        d2.setIpEscalador("192.168.1.40");
                        d2.setNumPantalla("1");
                        d2.setReproductor("1");
                        d2.setOutput("2");
                        listDis.add(d2);

                        Dispositivo d3 = new Dispositivo();
                        d3.setIpEscalador("192.168.1.40");
                        d3.setNumPantalla("3");
                        d3.setReproductor("3");
                        d3.setOutput("4");
                        listDis.add(d3);

                        for(Dispositivo it : listDis){

                            ip = it.getIpEscalador();
                            screen = Integer.parseInt(it.getNumPantalla());
                            input = Integer.parseInt(it.getOutput());
                            sendRoute();
                        }

                        /*int i = 0;
                        for(Dispositivo it : listDispositivos){

                            for(Beacon bi : it.getListBeacons()) {
                                //Log.i("Data Route",".-"+ screen + "");

                                if(bi.getId().equals(product.getCode())) {

                                    Log.i("getId:", it.getName() + " - " + bi.getId() + "=" + product.getCode());
                                    Log.i("Conn:", "Input:" + it.getNumPantalla() + " | Output:" + it.getOutput());
                                }


                                //if(it.getName().equals("Minix2-P2")){
                               *//* try {

                                    if(it.getName().equals(nameMydevice)) {
                                        Log.i("variable", it.getName() + "-" + nameMydevice);
                                    }

                                    ip = it.getIpEscalador();
                                    screen = Integer.parseInt(it.getNumPantalla());
                                    input = Integer.parseInt(it.getOutput());
                                    sendRoute();
                                    sendIn();
                                    video(pathFile);

                                }catch(Exception ex){
                                    Log.i("NOK Video","Error:" + ex);
                                }*//*
                            }
                        }*/
                        cerrarPanel(0);
                        item = product.getId();
                    }

            }

            @Override
            public void onProductPutdown(Product product)
            {

            }


        });

        holder = surface.getHolder();
        holder.addCallback(this);


        player = new MediaPlayer();
        player.setOnPreparedListener(this);


    }

    public void sendRoute(){

        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... params) {

                MultiScaler ms = new MultiScaler();
                ms.connect(50000,ip);
                ms.sendRoute(screen,input);


                return null;

            }
        }.execute();
    }



    public void video(String url) throws IOException

    {
        if (player != null)
        {
            player.stop();
            player.reset();
            player.setDataSource(url);
            player.prepareAsync();

        }
    }

    public void cerrarPanel(int estado){
        if(estado==0){
            panel.setVisibility(View.GONE);
        }else{
            panel.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onPrepared(MediaPlayer mp)
    {
        player.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setDisplay(holder);
        try
        {
            player.setDataSource(inactividad+ "/INACTIVIDAD.mp4");
            player.prepareAsync();

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                try
                {
                    player.stop();
                    player.reset();
                    player.setDataSource(inactividad+ "/INACTIVIDAD.mp4");
                    player.prepareAsync();
                    item = 1;
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

    }


    public void setImage(String path) {

        String ruta = path;

        try {
            Log.i("Video Externo","Cargando video por socket");
            video(ruta);
        }catch(Exception ex){
            Log.i("Error Video", "Error al cargar el video externo, cod:" + ex);
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {

    }

    @Override
    protected void onDestroy()
    {
        showroomManager.destroy();
        super.onDestroy();
        Log.d("INFOCODE-VIDEO", "VIDEO DESTRUIDO");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        showroomManager.stopUpdates();
        Log.d("INFOCODE-VIDEO", "VIDEO PAUSADO");
        player.release(); player.release();
        player = null;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this))
        {
            Log.e("Rinno", "Can't scan for beacons, some pre-conditions were not met");
            Log.e("Rinno", "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
            Log.e("Rinno", "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
        } else
        {
            Log.d("Rinno", "Starting ShowroomManager updates");
            showroomManager.startUpdates();
        }
    }
}

