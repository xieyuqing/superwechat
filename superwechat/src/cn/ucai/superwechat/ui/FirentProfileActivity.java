package cn.ucai.superwechat.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.domain.User;
import com.hyphenate.easeui.utils.EaseUserUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.SuperWeChatHelper;
import cn.ucai.superwechat.utils.L;
import cn.ucai.superwechat.utils.MFGT;

/**
 * Created by Administrator on 2017/2/14 0014.
 */

public class FirentProfileActivity extends Activity {
    private static final String TAG = FirentProfileActivity.class.getSimpleName();
    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.profile_image)
    ImageView mProfileImage;
    @BindView(R.id.tv_userinfo_nick)
    TextView mTvUserinfoNick;
    @BindView(R.id.tv_userinfo_name)
    TextView mTvUserinfoName;
    @BindView(R.id.btn_add_contact)
    Button mBtnAddContact;
    @BindView(R.id.btn_send_msg)
    Button mBtnSendMsg;
    @BindView(R.id.btn_send_video)
    Button mBtnSendVideo;
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firent_profile);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mImgBack.setVisibility(View.VISIBLE);
        mTxtTitle.setVisibility(View.VISIBLE);
        mTxtTitle.setText(R.string.userinfo_txt_profile);
        user = (User) getIntent().getSerializableExtra(I.User.USER_NAME);
        L.e(TAG,"user="+user);
        if (user != null) {
            showUserInfo(user);
        } else {
            MFGT.finish(this);
        }

    }

    private void showUserInfo(User user) {
        mTvUserinfoNick.setText(user.getMUserNick());
        EaseUserUtils.setAppUserAvatarByPath(this,user.getAvatar(),mProfileImage);
        mTvUserinfoName.setText("微信号:"+user.getMUserName());
        if (isFirent()) {
            mBtnSendMsg.setVisibility(View.VISIBLE);
            mBtnSendVideo.setVisibility(View.VISIBLE);
        } else {
            mBtnAddContact.setVisibility(View.VISIBLE);
        }
    }

    private boolean isFirent() {
        User u = SuperWeChatHelper.getInstance().getAppContactList().get(user.getMUserName());
        if (u == null) {
            return false;
        } else {
            SuperWeChatHelper.getInstance().saveAppContact(user);
            return true;
        }
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        MFGT.finish(this);
    }
}
