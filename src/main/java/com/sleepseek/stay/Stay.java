package com.sleepseek.stay;

import com.sleepseek.accomodation.AccommodationTemplate;
import com.sleepseek.common.BaseEntity;
import com.sleepseek.image.Image;
import com.sleepseek.review.Review;
import com.sleepseek.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stays")
public final class Stay extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Embedded
    private Address address;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 10000)
    private String description;

    @Column(nullable = false)
    private Long minPrice;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private String mainPhoto;

    @ElementCollection(targetClass = StayProperty.class, fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @CollectionTable
    @Column(name = "properties")
    private Set<StayProperty> properties;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StayCategory category;

    @OneToMany(targetEntity = Image.class, fetch = FetchType.EAGER)
    private List<Image> photos;

    @OneToMany(mappedBy = "stay", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<AccommodationTemplate> accommodationTemplates;

    @OneToMany(mappedBy = "stay", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Review> reviews;

    public void addAccommodation(AccommodationTemplate accommodationTemplate) {
        accommodationTemplates.add(accommodationTemplate);
        accommodationTemplate.setStay(this);
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setStay(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setStay(null);
    }

    public void removeAccommodation(AccommodationTemplate accommodationTemplate) {
        accommodationTemplates.remove(accommodationTemplate);
        accommodationTemplate.setStay(null);
    }

}