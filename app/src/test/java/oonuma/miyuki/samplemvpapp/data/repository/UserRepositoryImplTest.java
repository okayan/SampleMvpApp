package oonuma.miyuki.samplemvpapp.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import oonuma.miyuki.samplemvpapp.model.User;

import static oonuma.miyuki.samplemvpapp.data.repository.UserRepositoryImpl.KEY_AGE;
import static oonuma.miyuki.samplemvpapp.data.repository.UserRepositoryImpl.KEY_NAME;
import static oonuma.miyuki.samplemvpapp.data.repository.UserRepositoryImpl.KEY_SEX;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class UserRepositoryImplTest {

    private SharedPreferences prefs;
    private UserRepositoryImpl userRepository;

    @Before
    public void setUp() throws Exception {
        final Context context = RuntimeEnvironment.application;
        prefs = context.getSharedPreferences("test", Context.MODE_PRIVATE);

        userRepository = new UserRepositoryImpl(prefs);
    }

    @After
    public void tearDown() throws Exception {
        prefs.edit().clear().apply();
    }

    @Test
    public void saveUser_ユーザ情報が保存されること() {
        String expUserName = "user";
        int expAge = 1;
        User.SEX expSex = User.SEX.FEMALE;
        User user = new User(expUserName, expAge, expSex);

        userRepository.saveUser(user);

        assertThat(prefs.getString(KEY_NAME, null)).isEqualTo(expUserName);
        assertThat(prefs.getInt(KEY_AGE, 0)).isEqualTo(expAge);
        assertThat(prefs.getString(KEY_SEX, null)).isEqualTo(expSex.name());
    }

    @Test
    public void loadUser_ユーザ情報あり_ユーザ情報が返されること() {
        String expUserName = "user";
        int expAge = 1;
        User.SEX expSex = User.SEX.FEMALE;
        User expUser = new User(expUserName, expAge, expSex);
        saveUser(expUser);

        User actualUser = userRepository.loadUser();

        assertThat(actualUser).isNotNull();
        assertThat(actualUser.getName()).isEqualTo(expUserName);
        assertThat(actualUser.getAge()).isEqualTo(expAge);
        assertThat(actualUser.getSex()).isEqualTo(expSex);
    }

    @Test
    public void loadUser_ユーザ情報なし_ユーザ情報がnullで返されること() {
        User actualUser = userRepository.loadUser();

        assertThat(actualUser).isNull();
    }

    @Test
    public void deleteUser_ユーザ情報がクリアされること() {
        saveUser(new User("user", 1, User.SEX.FEMALE));

        userRepository.deleteUser();

        assertThat(prefs.getAll()).isEmpty();
    }

    private void saveUser(@NonNull User user) {
        prefs.edit()
                .putString(KEY_NAME, user.getName())
                .putInt(KEY_AGE, user.getAge())
                .putString(KEY_SEX, user.getSex().name())
                .apply();
    }
}