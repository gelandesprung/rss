package top.froms.www;

import static java.lang.System.exit;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import top.froms.www.fragments.BlogFragment;
import top.froms.www.fragments.ImgFragment;
import top.froms.www.fragments.NoneFragment;
import top.froms.www.fragments.SurpriseFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private final String TAG_BLOG = "blog";
    private final String TAG_IMG = "img";
    private final String TAG_SURPRISE = "surprise";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_blog:
                        loadfragment(TAG_BLOG);
                        return true;
                    case R.id.navigation_img:
                        loadfragment(TAG_IMG);
                        return true;
                    case R.id.navigation_surprise:
                        loadfragment(TAG_SURPRISE);
                        return true;
                }
                return false;
            };

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(permission.INTERNET,permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(r->{
                    if (!r) exit(0);
                });
        loadfragment(TAG_BLOG);
    }

    private void loadfragment(String tag) {
        Fragment frag = fragmentManager.findFragmentByTag(tag);
        if (frag == null) {
            switch (tag) {
                case TAG_BLOG:
                    frag = new BlogFragment();
                    break;
                case TAG_IMG:
                    frag = new ImgFragment();
                    break;
                case TAG_SURPRISE:
                    frag = new SurpriseFragment();
                    break;
                default:
                    frag = new NoneFragment();
                    break;
            }
        }
        fragmentManager.beginTransaction().replace(R.id.fl_main, frag, tag).commit();
    }
}
