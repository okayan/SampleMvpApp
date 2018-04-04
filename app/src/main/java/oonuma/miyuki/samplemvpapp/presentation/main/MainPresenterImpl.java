package oonuma.miyuki.samplemvpapp.presentation.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import oonuma.miyuki.samplemvpapp.R;
import oonuma.miyuki.samplemvpapp.data.repository.UserRepository;
import oonuma.miyuki.samplemvpapp.model.User;

public class MainPresenterImpl implements MainContract.Presenter {

    private final MainContract.View view;
    private final UserRepository userRepository;

    public MainPresenterImpl(@NonNull MainContract.View view, @NonNull UserRepository userRepository) {
        this.view = view;
        this.userRepository = userRepository;
    }

    @Override
    public void onCreate() {
        view.setName(null);
        view.setAge(null);
        view.setSex(null);
    }

    @Override
    public void onValueChange(@NonNull String name, @Nullable MainContract.View.SEX sex, @Nullable Integer age) {
        view.setSaveButtonEnabled(!name.isEmpty() && sex != null && age != null);
    }

    @Override
    public void onSaveButtonClick(@NonNull String name, int age, @NonNull MainContract.View.SEX sex) {
        userRepository.saveUser(new User(name, age, convertSex(sex)));
        view.showToast(R.string.message_saved);
    }

    @Override
    public void onLoadButtonClick() {
        User user = userRepository.loadUser();
        if (user == null) {
            view.showToast(R.string.message_no_data);
        } else {
            view.setName(user.getName());
            view.setAge(user.getAge());
            view.setSex(convertSex(user.getSex()));
        }
    }

    @Override
    public void onDeleteButtonClick() {
        userRepository.deleteUser();
        view.setName(null);
        view.setAge(null);
        view.setSex(null);
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
