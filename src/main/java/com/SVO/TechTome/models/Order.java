package com.SVO.TechTome.models;

import com.SVO.TechTome.models.enums.OrderStatus;
import com.SVO.TechTome.models.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(scale = 2)
    private BigDecimal deliveryCost;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PaymentStatus paymentStatus;

    @Column(length = 150)
    private String recipientName;

    @Column(length = 20)
    private String recipientPhone;

    @Column(length = 150)
    private String deliveryStreet;

    @Column(length = 20)
    private String deliveryNum;

    @Column(length = 150)
    private String deliveryOther;

    @Column(length = 100)
    private String deliveryCity;

    @Column(length = 10)
    private String deliveryPostCode;

    @Column(length = 50)
    private String trackingNumber;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();
}
