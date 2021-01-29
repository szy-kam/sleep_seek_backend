package com.sleepseek.image;

import com.sleepseek.common.BaseEntity;
import com.sleepseek.stay.Stay;
import com.sleepseek.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "images")
public class Image extends BaseEntity {
    @Column
    private String url;

    @Column
    private String originalFilename;

    @ManyToOne
    @JoinColumn(name = "stay_id")
    private Stay stay;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
}
