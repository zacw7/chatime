package edu.neu.cs5520.chatime.presentation.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import edu.neu.cs5520.chatime.R;
import edu.neu.cs5520.chatime.domain.executor.impl.ThreadExecutor;
import edu.neu.cs5520.chatime.presentation.presenters.MainPresenter;
import edu.neu.cs5520.chatime.presentation.presenters.impl.MainPresenterImpl;
import edu.neu.cs5520.chatime.storage.WelcomeMessageRepository;
import edu.neu.cs5520.chatime.threading.MainThreadImpl;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends AppCompatActivity implements MainPresenter.View {

  @BindView(R.id.head_toolbar)
  Toolbar mToolbar;
  @BindView(R.id.nav_view)
  BottomNavigationView mNavView;

  private MainPresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

    // create a presenter for this view
    mPresenter = new MainPresenterImpl(
        ThreadExecutor.getInstance(),
        MainThreadImpl.getInstance(),
        this,
        new WelcomeMessageRepository()
    );

    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        R.id.navigation_chat_list, R.id.navigation_home, R.id.navigation_bottle_list)
        .build();
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    NavigationUI.setupWithNavController(mNavView, navController);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.toolbar_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_profile) {
      Toast.makeText(MainActivity.this, "Profile button clicked", Toast.LENGTH_LONG).show();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onResume() {
    super.onResume();

    // let's start welcome message retrieval when the app resumes
    mPresenter.resume();
  }

  @Override
  public void showProgress() {
  }

  @Override
  public void hideProgress() {
    Toast.makeText(this, "Retrieved!", Toast.LENGTH_LONG).show();
  }

  @Override
  public void displayWelcomeMessage(String msg) {

  }

  @Override
  public void showError(String message) {

  }
}
