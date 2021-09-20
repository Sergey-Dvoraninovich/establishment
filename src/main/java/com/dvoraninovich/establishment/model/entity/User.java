package com.dvoraninovich.establishment.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class User {

    private long id;
    private String login;
    private String mail;
    //private String passwordHash;
    //private String salt;
    private Role role;
    private UserStatus status;
    private String phoneNumber;
    private String cardNumber;
    private BigDecimal bonusesAmount;
    private String photo;
    //private String code;
    //private LocalDateTime expirationCodeTime;

    private User() {
    }

    public static UserBuilder builder() {
        return new User().new UserBuilder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

//    public String getPasswordHash() {
//        return passwordHash;
//    }
//
//    public void setPasswordHash(String passwordHash) {
//        this.passwordHash = passwordHash;
//    }
//
//    public String getSalt() {
//        return salt;
//    }
//
//    public void setSalt(String salt) {
//        this.salt = salt;
//    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigDecimal getBonusesAmount() {
        return bonusesAmount;
    }

    public void setBonusesAmount(BigDecimal bonusesAmount) {
        this.bonusesAmount = bonusesAmount;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public LocalDateTime getExpirationCodeTime() {
//        return expirationCodeTime;
//    }
//
//    public void setExpirationCodeTime(LocalDateTime expirationCodeTime) {
//        this.expirationCodeTime = expirationCodeTime;
//    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("User{");
        result.append(" id = ").append(id);
        result.append(", login='").append(login).append("'");
//        result.append(", passwordHash='").append(passwordHash).append("'");
//        result.append(", salt='").append(salt).append("'");
        result.append(", role=").append(role);
        result.append(", status=" ).append(status);
        result.append(", phoneNumber='").append(phoneNumber).append("'");
        result.append(", cardNumber='").append(cardNumber).append("'");
        result.append(", bonusesAmount=").append(bonusesAmount);
        result.append(", photo=").append(photo);
//        result.append(", code=").append(code);
//        result.append(", token=").append(expirationCodeTime);
        result.append(" }");
        return result.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        User user = (User) obj;
        return id == user.id && Objects.equals(login, user.login)
                && Objects.equals(mail, user.mail)
//                && Objects.equals(passwordHash, user.passwordHash)
//                && Objects.equals(salt, user.salt) && role == user.role
                && status == user.status
                && Objects.equals(phoneNumber, user.phoneNumber)
                && Objects.equals(cardNumber, user.cardNumber)
                && Objects.equals(bonusesAmount, user.bonusesAmount)
                && Objects.equals(photo, user.photo);
//                && Objects.equals(code, user.code)
//                && Objects.equals(expirationCodeTime, user.expirationCodeTime);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = result * 31 + Long.hashCode(id);
        result = result * 31 + (login != null ? login.hashCode() : 0);
        result = result * 31 + (mail != null ? mail.hashCode() : 0);
//        result = result * 31 + (passwordHash != null ? passwordHash.hashCode() : 0);
//        result = result * 31 + (salt != null ? salt.hashCode() : 0);
        result = result * 31 + (role != null ? role.hashCode() : 0);
        result = result * 31 + (status != null ? status.hashCode() : 0);
        result = result * 31 + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = result * 31 + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = result * 31 + (bonusesAmount != null ? bonusesAmount.hashCode() : 0);
        result = result * 31 + (photo != null ? photo.hashCode() : 0);
//        result = result * 31 + (code != null ? code.hashCode() : 0);
//        result = result * 31 + (expirationCodeTime != null ? expirationCodeTime.hashCode() : 0);

        return result;
    }

    public class UserBuilder {

        private UserBuilder() {

        }

        public UserBuilder of(User user) {
            User.this.id = user.id;
            return this;
        }

        public UserBuilder setId(long id) {
            User.this.id = id;
            return this;
        }

        public UserBuilder setLogin(String login) {
            User.this.login = login;
            return this;
        }

        public UserBuilder setMail(String mail) {
            User.this.mail = mail;
            return this;
        }

//        public UserBuilder setPasswordHash(String passwordHash) {
//            User.this.passwordHash = passwordHash;
//            return this;
//        }
//
//        public UserBuilder setSalt(String salt) {
//            User.this.salt = salt;
//            return this;
//        }

        public UserBuilder setRole(Role role) {
            User.this.role = role;
            return this;
        }

        public UserBuilder setStatus(UserStatus status) {
            User.this.status = status;
            return this;
        }

        public UserBuilder setPhoneNumber(String phoneNumber) {
            User.this.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder setCardNumber(String cardNumber) {
            User.this.cardNumber = cardNumber;
            return this;
        }

        public UserBuilder setBonusesAmount(BigDecimal bonusesAmount) {
            User.this.bonusesAmount = bonusesAmount;
            return this;
        }

        public UserBuilder setPhoto(String photo) {
            User.this.photo = photo;
            return this;
        }

//        public UserBuilder setCode(String code) {
//            User.this.code = code;
//            return this;
//        }
//
//        public UserBuilder setExpirationCodeTime(LocalDateTime expirationCodeTime) {
//            User.this.expirationCodeTime = expirationCodeTime;
//            return this;
//        }

        public User build() {
            return User.this;
        }
    }
}
