package br.com.juliocnsouza.twitterconnect.activity;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.graphics.Color;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.juliocnsouza.twitterconnect.R;

@EActivity(R.layout.activity_post)
@OptionsMenu(R.menu.post)
public class PostActivity extends Activity {

    private final int POST_MAX_LENGTH = 140;
    private final int POST_CHAR_COUNTER_ALERT_POSITION = 10;

    @ViewById
    TextView lblUser;
    @ViewById
    EditText txtTweet;
    @ViewById
    TextView lblCharCounter;
    @ViewById
    Button btnSendMessage;

    @AfterViews
    void configureUser() {
        this.lblUser.setText(getUser());
    }

    @AfterViews
    void configureCharCounter() {
        this.lblCharCounter.setText(Integer.toString(this.POST_MAX_LENGTH));
        this.lblCharCounter.setTextColor(Color.GREEN);
    }

    @AfterTextChange(R.id.txtTweet)
    public void updateCharCounter() {
        final int count = this.POST_MAX_LENGTH - this.txtTweet.length();
        this.lblCharCounter.setText(Integer.toString(count));
        if (count <= 0) {
            this.lblCharCounter.setTextColor(Color.RED);
        } else if (count < this.POST_CHAR_COUNTER_ALERT_POSITION) {
            this.lblCharCounter.setTextColor(Color.YELLOW);
        } else {
            this.lblCharCounter.setTextColor(Color.GREEN);
        }
    }

    @Click(R.id.btnSendMessage)
    public void onClickBtnSendMessage() {
        startPostOnTwitter();
    }

    @Background
    void startPostOnTwitter() {
        try {
            sendMessage();
            displayResult(getResources().getString(R.string.messageSent));
        } catch (final Exception e) {
            displayResult(getResources().getString(R.string.messageFailure));
        }
    }

    @UiThread
    void displayResult(final String result) {
        Toast.makeText(PostActivity.this, result, Toast.LENGTH_LONG).show();
    }

    @OptionsItem(R.id.action_settings)
    boolean menuItemSettings() {
        return true;
    }

    @OptionsItem(R.id.action_about)
    boolean menuItemAbout() {
        return true;
    }

    private String getUser() {
        return "@juliocnsouza";
    }

    private void sendMessage() {
        // TwitterConnectimainonFactory.getInstance().getConnection().updateStatus(params[0]);
    }

}
