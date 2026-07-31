package com.SVO.TechTome.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "wishlist_items",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "store_item_id"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_item_id", nullable = false)
    private StoreItem storeItem;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime addedOn = LocalDateTime.now();
}
