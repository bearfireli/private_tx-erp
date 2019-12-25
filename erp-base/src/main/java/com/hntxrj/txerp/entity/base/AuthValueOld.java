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
@IdClass(AuthValueOldPK.class)
@Table(name = "auth_value")
public class AuthValueOld implements Serializable {
    @Id
    private Integer groupId;
    @Id
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
class AuthValueOldPK implements Serializable {

    private Integer groupId;

    private Integer menuId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthValueOldPK)) return false;
        AuthValueOldPK that = (AuthValueOldPK) o;
        return Objects.equals(getGroupId(), that.getGroupId()) &&
                Objects.equals(getMenuId(), that.getMenuId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGroupId(), getMenuId());
    }
}
