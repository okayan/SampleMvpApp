package oonuma.miyuki.samplemvpapp.data.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import oonuma.miyuki.samplemvpapp.model.User;

public interface UserRepository {

    /**
     * ユーザ情報を保存
     *
     * @param user
     */
    void saveUser(@NonNull User user);

    /**
     * 保存されているユーザ情報を取得
     *
     * @return 保存されているユーザがない場合null
     */
    @Nullable
    User loadUser();

    /**
     * 保存されているユーザ情報を削除
     */
    void deleteUser();
}
