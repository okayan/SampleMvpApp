package oonuma.miyuki.samplemvpapp.presentation.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

public interface MainContract {

    interface View {

        enum SEX {
            MALE,
            FEMALE
        }

        /**
         * 保存ボタンの有効状態を変更する
         *
         * @param enabled true:有効, false:無効
         */
        void setSaveButtonEnabled(boolean enabled);

        /**
         * 名前を設定
         *
         * @param name nullが渡されたときはクリアする
         */
        void setName(@Nullable String name);

        /**
         * 性別を設定
         *
         * @param sex nullが渡されたときはクリアする
         */
        void setSex(@Nullable SEX sex);

        /**
         * 年齢を設定
         *
         * @param age nullが渡されたときはクリアする
         */
        void setAge(@Nullable Integer age);

        void showToast(@StringRes int resId);
    }

    interface Presenter {

        void onCreate();

        /**
         * 入力値変更時に呼ばれる処理
         *
         * @param name 名前.
         * @param sex  性別. 何も選択していないときnull
         * @param age  年齢. 何も入力していないときnull
         */
        void onValueChange(@NonNull String name, @Nullable View.SEX sex, @Nullable Integer age);

        /**
         * 保存ボタン押下時に呼ばれる処理
         *  @param name 名前
         * @param age  年齢
         * @param sex  性別
         */
        void onSaveButtonClick(@NonNull String name, int age, @NonNull View.SEX sex);

        void onLoadButtonClick();

        void onDeleteButtonClick();
    }
}
