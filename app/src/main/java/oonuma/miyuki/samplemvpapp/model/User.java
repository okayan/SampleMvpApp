package oonuma.miyuki.samplemvpapp.model;

import java.util.Objects;

public class User {

    private final String name;

    private final int age;

    private final SEX sex;

    public enum SEX {
        MALE,
        FEMALE
    }

    public User(String name, int age, SEX sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public SEX getSex() {
        return sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age &&
                Objects.equals(name, user.name) &&
                sex == user.sex;
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, age, sex);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}

