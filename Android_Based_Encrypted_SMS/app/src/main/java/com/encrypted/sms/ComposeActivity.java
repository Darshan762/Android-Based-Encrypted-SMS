package com.encrypted.sms;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//import Encryption.EncryptionMain;
//import Hash.HashMain;


public class ComposeActivity extends AppCompatActivity {
    ComposeMain encryptionMain;
    DecryptionComposeMain decryptionComposeMain;
   // HashMain hashMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose);

        Fragment fragment = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    public void goToEncryption(View view) {
        encryptionMain = new ComposeMain();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.container, encryptionMain);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void goToDecryption(View view) {
        decryptionComposeMain = new DecryptionComposeMain();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.container, decryptionComposeMain);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    public void encryptionButtonClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.Swtich:
                    encryptionMain.switchAlgho(view);
                    break;
                case R.id.Encrypt_Buuton:
                    encryptionMain.encrypt(view);
                    break;
                case R.id.Decrypt_Buuton:
                    encryptionMain.decrypt(view);
                    break;
                case R.id.copy_button:
                    encryptionMain.copyToClipboard(view);
                    break;
                case R.id.reset_button:
                    encryptionMain.reset(view);
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


}
