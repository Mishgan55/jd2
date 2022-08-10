package domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;
import java.util.Objects;

public class User {
    private Long id;

    private String userName;

    private String userSurname;

    private Timestamp userBirth;

    private Boolean isDeleted;

    private Timestamp creationDate;

    private Timestamp modificationDate;

    private Double weight;


    public User() {
    }

    public User(Long id, String userName, String userSurname,
                Timestamp userBirth, Boolean isDeleted,
                Timestamp creationDate, Timestamp modificationDate,
                Double weight) {
        this.id = id;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userBirth = userBirth;
        this.isDeleted = isDeleted;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public Timestamp getUserBirth(Timestamp timestamp) {
        return userBirth;
    }

    public void setUserBirth(Timestamp userBirth) {
        this.userBirth = userBirth;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Timestamp getCreationDate(Timestamp timestamp) {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getModificationDate(Timestamp timestamp) {
        return modificationDate;
    }

    public void setModificationDate(Timestamp modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Double getWeight(double aDouble) {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(userName, user.userName)
                && Objects.equals(userSurname, user.userSurname)
                && Objects.equals(userBirth, user.userBirth)
                && Objects.equals(isDeleted, user.isDeleted)
                && Objects.equals(creationDate, user.creationDate)
                && Objects.equals(modificationDate, user.modificationDate)
                && Objects.equals(weight, user.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, userSurname, userBirth, isDeleted, creationDate, modificationDate, weight);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
