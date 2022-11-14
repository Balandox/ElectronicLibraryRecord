package org.suai.library.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class Person {

    private int id;

    @NotBlank(message = "Укажите имя!")
    private String firstName;
    @NotBlank(message = "Укажите отчество!")
        private String middleName;
    @NotBlank(message = "Укажите фамилию!")
    private String lastName;

    @Min(value = 1900, message = "Дата рождения должна быть больше 1900")
    @Max(value = 2022, message = "Дата рождения должна быть не позже текущего года")
    private int birthdayYear;

    private String fullName;

    public Person(int id, String firstName, String middleName, String lastName, int birthdayYear) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthdayYear = birthdayYear;
        this.fullName = getFullName();
    }

    public Person(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName){
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public int getBirthdayYear() {
        return birthdayYear;
    }

    public void setBirthdayYear(int birthdayYear) {
        this.birthdayYear = birthdayYear;
    }

    public String getFullName(){
        return firstName + " " + middleName + " " + lastName;
    }
}
