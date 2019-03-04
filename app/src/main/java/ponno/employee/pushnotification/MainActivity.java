package ponno.employee.pushnotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;


public class MainActivity extends AppCompatActivity {

    //for creating notification we need 3 things
    //1.Notification Channel
    //2.Notification Builder
    //3.Notification Manager

    //For notifivation channel we also need 3 things
    //now here are 3 constants for notification channel
    public static final String CHANNEL_ID = "shawn_muktadir";
    private static final String CHANNEL_Name = "Shawn Muktadir";
    private static final String CHANNEL_DESC = "Shawn Muktadir Notifications";

    private EditText et_email,et_password;
    private ProgressBar progressbar;

    private FirebaseAuth mAuth;

    //private TextView textViewToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        progressbar = (ProgressBar)findViewById(R.id.progressbar);
        progressbar.setVisibility(View.INVISIBLE);
        et_email = (EditText)findViewById(R.id.et_email);
        et_password = (EditText)findViewById(R.id.et_password);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        //textViewToken = (TextView)findViewById(R.id.textViewToken);

        //if our device is greater or equal to android oreo/android 8 then we need notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_Name, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }

//        findViewById(R.id.btnNotify).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                displayNotification();
//            }
//        });

        //getting firebase message token
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (task.isSuccessful()){
//                            String token = task.getResult().getToken();
//                            //textViewToken.setText("Token : "+token);
//
//                        }else {
//                            //textViewToken.setText(task.getException().getMessage());
//                        }
//                    }
//                });


    }

    private void createUser() {
        final String email = et_email.getText().toString().trim();
        final String password = et_password.getText().toString().trim();

        if (email.isEmpty()){
            et_email.setError("Email Required");
            et_email.requestFocus();
            return;
        }

        if (password.isEmpty()){
            et_password.setError("Password Required");
            et_password.requestFocus();
            return;
        }

        if (password.length() < 6){
            et_password.setError("Password should be at least 6 characters");
            et_password.requestFocus();
            return;
        }

        progressbar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            profileActivity();
                        }else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                userLogin(email,password);
                            }else {
                                progressbar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

    }

    private void userLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    profileActivity();
                }else {
                    progressbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            profileActivity();
        }
    }

    private void profileActivity(){
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    //we require a function or method which will display our notification in notification bar of our device
//    private void displayNotification(){
//        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
//                                              .setSmallIcon(R.drawable.ic_notification)
//                                              .setContentTitle("Hurray Blood Donors...")
//                                              .setContentText("This is notification")
//                                              .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
//        notificationManagerCompat.notify(1,mbuilder.build());
//    }
}
























































