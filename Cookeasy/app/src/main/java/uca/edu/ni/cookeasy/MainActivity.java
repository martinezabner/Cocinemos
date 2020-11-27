package uca.edu.ni.cookeasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.List;

import Adapters.CategoryAdapter;
import Data.CategoryRepository;
import Fragments.HomeFragment;
import Fragments.LogoFragment;
import Models.Category;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 600;

    // Variables
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        showLogo();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment homeFragment =  new HomeFragment(getBaseContext());
                fragmentTransaction.replace(R.id.frg_main, homeFragment);
                fragmentTransaction.commit();
            }
        }, SPLASH_SCREEN);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showLogo() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        LogoFragment logoFragment =  new LogoFragment();
        fragmentTransaction.replace(R.id.frg_main, logoFragment);
        fragmentTransaction.commit();
    }

}