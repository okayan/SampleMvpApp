package oonuma.miyuki.samplemvpapp.presentation.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import oonuma.miyuki.samplemvpapp.R;
import oonuma.miyuki.samplemvpapp.data.repository.UserRepository;
import oonuma.miyuki.samplemvpapp.data.repository.UserRepositoryImpl;

public class MainActivity extends AppCompatActivity implements MainContract.View, View.OnClickListener {

    private EditText ageEditText;
    private RadioGroup sexRadioGroup;
    private EditText nameEditText;
    private View saveButton;
    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserRepository userRepository = new UserRepositoryImpl(getSharedPreferences("PREFS_USER", Context.MODE_PRIVATE));
        presenter = new MainPresenterImpl(this, userRepository);

        initView();
        presenter.onCreate();
    }

    private void initView() {
        nameEditText = findViewById(R.id.et_name);

        OnTextChangeListener onTextChangeListener = new OnTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onValueChange(getName(), getSex(), getAge());
            }
        };
        nameEditText.addTextChangedListener(onTextChangeListener);
        sexRadioGroup = findViewById(R.id.rg_sex);
        sexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                presenter.onValueChange(getName(), getSex(), getAge());
            }
        });
        ageEditText = findViewById(R.id.et_age);
        ageEditText.addTextChangedListener(onTextChangeListener);

        saveButton = findViewById(R.id.btn_save);
        saveButton.setOnClickListener(this);
        findViewById(R.id.btn_load).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
    }

    private String getName() {
        return nameEditText.getText().toString();
    }

    @Nullable
    private Integer getAge() {
        String ageString = ageEditText.getText().toString();
        return TextUtils.isEmpty(ageString) ? null : Integer.valueOf(ageString);
    }

    @Override
    public void setSaveButtonEnabled(boolean enabled) {
        saveButton.setEnabled(enabled);
    }

    @Override
    public void setName(@Nullable String name) {
        nameEditText.setText(name);
    }

    @Override
    public void setSex(@Nullable SEX sex) {
        if (sex == null) {
            sexRadioGroup.clearCheck();
        } else {
            sexRadioGroup.check(sex == SEX.MALE ? R.id.rb_male : R.id.rb_female);
        }
    }

    @Override
    public void setAge(@Nullable Integer age) {
        ageEditText.setText(age != null ? String.valueOf(age) : null);
    }

    @Override
    public void showToast(@StringRes int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                final SEX sex = getSex();
                //noinspection ConstantConditions
                presenter.onSaveButtonClick(nameEditText.getText().toString(), Integer.valueOf(ageEditText.getText().toString()), sex);
                break;
            case R.id.btn_load:
                presenter.onLoadButtonClick();
                break;
            case R.id.btn_delete:
                presenter.onDeleteButtonClick();
                break;
        }
    }

    @Nullable
    private SEX getSex() {
        int checkedRadioButtonId = sexRadioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == -1) {
            return null;
        } else {
            return checkedRadioButtonId == R.id.rb_male ? SEX.MALE : SEX.FEMALE;
        }
    }

    private static abstract class OnTextChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
