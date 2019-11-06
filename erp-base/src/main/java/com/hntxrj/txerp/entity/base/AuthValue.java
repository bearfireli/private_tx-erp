package com.hntxrj.txerp.entity.base;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@IdClass(AuthValuePK.class)
@Table(name = "auth_value_new")
public class AuthValue implements Serializable {
    @Id
    private Integer groupId;
    @Id
    private String funName;

    private Integer menuId;
    private Integer value;
    @Column(insertable = false, updatable = false)
    private Date createTime;
    @Column(insertable = false, updatable = false)
    private Date updateTime;
    private Integer updateUser;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class AuthValuePK implements Serializable {

    private Integer groupId;

    private String funName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthValuePK)) return false;
        AuthValuePK that = (AuthValuePK) o;
        return Objects.equals(getGroupId(), that.getGroupId()) &&
                Objects.equals(getFunName(), that.getFunName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGroupId(), getFunName());
    }
}
