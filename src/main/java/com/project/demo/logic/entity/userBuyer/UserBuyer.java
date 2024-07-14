package com.project.demo.logic.entity.userBuyer;

import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Entity
@Table(name = "user_buyer")
public class UserBuyer extends User{

}
