package oonuma.miyuki.samplemvpapp.data.repository;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import oonuma.miyuki.samplemvpapp.model.User;

public class UserRepositoryImpl implements UserRepository {

    private final SharedPreferences preferences;

    static final String KEY_NAME = "KEY_NAME";
    static final String KEY_AGE = "KEY_AGE";
    static final String KEY_SEX = "KEY_SEX";

    public UserRepositoryImpl(@NonNull SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void saveUser(@NonNull User user) {
        preferences.edit()
                .putString(KEY_NAME, user.getName())
                .putInt(KEY_AGE, user.getAge())
                .putString(KEY_SEX, user.getSex().name())
                .apply();
    }

    @Nullable
    @Override
    public User loadUser() {
        if (!preferences.contains(KEY_NAME)) {
            return null;
        } else {
            return new User(preferences.getString(KEY_NAME, null),
                    preferences.getInt(KEY_AGE, -1),
                    User.SEX.valueOf(preferences.getString(KEY_SEX, null)));
        }
    }

    @Override
    public void deleteUser() {
        preferences.edit().clear().apply();
    }
}
