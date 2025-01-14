package com.morakmorak.morak_back_end.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name ="receiver_id")
    private User receiver;

    private String content;

    private Integer point;

    @OneToOne(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Answer answer;

    @Builder.Default
    @OneToMany(mappedBy = "review", cascade = CascadeType.PERSIST)
    private List<ReviewBadge> reviewBadges = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    public void mapArticleAndUsers() {
        this.sender.getWrittenReviews().add(this);
        this.receiver.getReceivedReviews().add(this);
        this.answer.isAcceptedWith(this);
    }
}
