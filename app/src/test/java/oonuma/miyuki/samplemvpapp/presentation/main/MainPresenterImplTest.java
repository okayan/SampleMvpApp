package oonuma.miyuki.samplemvpapp.presentation.main;

import android.support.annotation.NonNull;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import oonuma.miyuki.samplemvpapp.R;
import oonuma.miyuki.samplemvpapp.data.repository.UserRepository;
import oonuma.miyuki.samplemvpapp.model.User;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterImplTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private MainContract.View view;

    @Mock
    private UserRepository repository;

    @InjectMocks
    private MainPresenterImpl presenter;

    @Test
    public void onCreate_画面の入力値をクリアすること() {
        presenter.onCreate();

        verify(view).setName(null);
        verify(view).setAge(null);
        verify(view).setSex(null);
    }

    @Test
    public void onValueChange_全て入力済みの場合保存ボタンを有効にしそれ以外は無効にすること() {
        boolean[][] resultSet = {
                // name, sex, age, expected
                {false, false, false, false},
                {false, false, true, false},
                {false, true, false, false},
                {false, true, true, false},
                {true, false, false, false},
                {true, false, true, false},
                {true, true, false, false},
                {true, true, true, true},
        };

        for (boolean[] result : resultSet) {
            verifyOnValueChanged(result[0], result[1], result[2], result[3]);
        }
    }

    @Test
    public void onSaveButtonClick_ユーザ情報を保存すること() {
        User expUser = new User("名前", 1, convertSex(MainContract.View.SEX.FEMALE));

        presenter.onSaveButtonClick(expUser.getName(), expUser.getAge(), convertSex(expUser.getSex()));

        verify(repository).saveUser(expUser);
    }

    @Test
    public void onLoadButtonClick_リポジトリよりユーザ情報を取得すること() {
        presenter.onLoadButtonClick();

        verify(repository).loadUser();
    }

    @Test
    public void onLoadButtonClick_保存済みデータあり_値を画面に反映すること() {
        User expUser = new User("name", 1, User.SEX.FEMALE);
        when(repository.loadUser()).thenReturn(expUser);

        presenter.onLoadButtonClick();

        verify(view).setName(expUser.getName());
        verify(view).setAge(expUser.getAge());
        verify(view).setSex(convertSex(expUser.getSex()));
    }

    @Test
    public void onLoadButtonClick_保存済みデータなし_トーストを表示すること() {
        when(repository.loadUser()).thenReturn(null);

        presenter.onLoadButtonClick();

        verify(view).showToast(R.string.message_no_data);
    }

    @Test
    public void onDeleteButtonClick_リポジトリの値をクリアすること() {
        presenter.onDeleteButtonClick();

        verify(repository).deleteUser();
    }

    @Test
    public void onDeleteButtonClick_画面の値をクリアすること() {
        presenter.onDeleteButtonClick();

        verify(view).setName(null);
        verify(view).setAge(null);
        verify(view).setSex(null);
    }

    private void verifyOnValueChanged(boolean name, boolean sex, boolean age, boolean exp) {
        presenter.onValueChange(name ? "名前" : "",
                sex ? MainContract.View.SEX.MALE : null,
                age ? 1 : null);

        verify(view).setSaveButtonEnabled(exp);
        // モックをクリアする
        Mockito.reset(view);
    }

    @NonNull
    private User.SEX convertSex(@NonNull MainContract.View.SEX sex) {
        switch (sex) {
            case MALE:
                return User.SEX.MALE;
            case FEMALE:
                return User.SEX.FEMALE;
            default:
                throw new IllegalArgumentException();
        }
    }

    @NonNull
    private MainContract.View.SEX convertSex(@NonNull User.SEX sex) {
        switch (sex) {
            case MALE:
                return MainContract.View.SEX.MALE;
            case FEMALE:
                return MainContract.View.SEX.FEMALE;
            default:
                throw new IllegalArgumentException();
        }
    }
}