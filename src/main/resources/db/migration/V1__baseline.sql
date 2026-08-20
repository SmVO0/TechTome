-- TechTome baseline schema — captures the schema at the point Flyway was introduced.
-- This migration runs only on a fresh database; existing databases are baselined in place.

CREATE TABLE shopping_cart (
    id          binary(16)     NOT NULL,
    total_price decimal(38, 2) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE category (
    id   binary(16)   NOT NULL,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uq_category_name (name)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

INSERT INTO category (id, name) VALUES
    (UUID_TO_BIN(UUID()), 'laptops'),
    (UUID_TO_BIN(UUID()), 'computers'),
    (UUID_TO_BIN(UUID()), 'keyboards'),
    (UUID_TO_BIN(UUID()), 'mice');

CREATE TABLE user (
    id               binary(16)                              NOT NULL,
    email            varchar(255)                            NOT NULL,
    first_name       varchar(255)                            DEFAULT NULL,
    last_name        varchar(255)                            DEFAULT NULL,
    password         varchar(255)                            NOT NULL,
    profile_picture  varchar(255)                            DEFAULT NULL,
    role             enum('ADMIN','EDITOR','GODMODE','USER') NOT NULL,
    username         varchar(255)                            NOT NULL,
    shopping_cart_id binary(16)                              DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uq_user_email (email),
    UNIQUE KEY uq_user_username (username),
    UNIQUE KEY uq_user_shopping_cart (shopping_cart_id),
    CONSTRAINT fk_user_shopping_cart FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE store_item (
    id                binary(16)     NOT NULL,
    name              varchar(255)   NOT NULL,
    description       varchar(255)   NOT NULL,
    short_description varchar(255)   DEFAULT NULL,
    image             varchar(255)   NOT NULL,
    price             decimal(38, 2) NOT NULL,
    stock             int            NOT NULL DEFAULT 100,
    featured          bit(1)         NOT NULL,
    category_id       binary(16)     NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uq_store_item_name (name),
    UNIQUE KEY uq_store_item_image (image),
    CONSTRAINT fk_store_item_category FOREIGN KEY (category_id) REFERENCES category (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE subscription (
    id           binary(16)     NOT NULL,
    type         varchar(20)    NOT NULL,
    status       varchar(20)    NOT NULL,
    price        decimal(38, 2) NOT NULL,
    created_on   datetime(6)    NOT NULL,
    completed_on datetime(6)    NOT NULL,
    owner_id     binary(16)     DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_subscription_user FOREIGN KEY (owner_id) REFERENCES user (id) ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE orders (
    id                  binary(16)                                              NOT NULL,
    total_price         decimal(38, 2)                                          NOT NULL,
    status              enum('CANCELLED','DELIVERED','PENDING','PROCESSING','SHIPPED') NOT NULL,
    created_on          datetime(6)                                             NOT NULL,
    payment_status      enum('FAILED','MOCK_PAID','PENDING')                    DEFAULT NULL,
    tracking_number     varchar(50)                                             DEFAULT NULL,
    delivery_city       varchar(100)                                            DEFAULT NULL,
    delivery_post_code  varchar(10)                                             DEFAULT NULL,
    delivery_street     varchar(150)                                            DEFAULT NULL,
    delivery_num        varchar(20)                                             DEFAULT NULL,
    delivery_other      varchar(150)                                            DEFAULT NULL,
    delivery_address    varchar(300)                                            DEFAULT NULL,
    delivery_cost       decimal(38, 2)                                          DEFAULT NULL,
    recipient_name      varchar(150)                                            DEFAULT NULL,
    recipient_phone     varchar(20)                                             DEFAULT NULL,
    buyer_id            binary(16)                                              NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_orders_buyer FOREIGN KEY (buyer_id) REFERENCES user (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE order_item (
    id           binary(16)     NOT NULL,
    quantity     int            NOT NULL,
    unit_price   decimal(38, 2) NOT NULL,
    order_id     binary(16)     NOT NULL,
    store_item_id binary(16)    NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_item_order      FOREIGN KEY (order_id)      REFERENCES orders     (id),
    CONSTRAINT fk_order_item_store_item FOREIGN KEY (store_item_id) REFERENCES store_item (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE shopping_cart_item (
    id               binary(16)      NOT NULL,
    quantity         int             NOT NULL,
    unit_price       decimal(19, 4)  NOT NULL DEFAULT 0.0000,
    shopping_cart_id binary(16)      NOT NULL,
    store_item_id    binary(16)      NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_sci_cart      FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart (id),
    CONSTRAINT fk_sci_store_item FOREIGN KEY (store_item_id)  REFERENCES store_item    (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE cart_store_items (
    shopping_cart_id binary(16) NOT NULL,
    store_item_id    binary(16) NOT NULL,
    CONSTRAINT fk_csi_cart      FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart (id),
    CONSTRAINT fk_csi_store_item FOREIGN KEY (store_item_id)  REFERENCES store_item    (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE store_item_shopping_cart (
    store_item_id    binary(16) NOT NULL,
    shopping_cart_id binary(16) NOT NULL,
    CONSTRAINT fk_sisc_store_item FOREIGN KEY (store_item_id)    REFERENCES store_item    (id),
    CONSTRAINT fk_sisc_cart       FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE wishlist_items (
    id            binary(16)  NOT NULL,
    added_on      datetime(6) NOT NULL,
    user_id       binary(16)  NOT NULL,
    store_item_id binary(16)  NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uq_wishlist_user_item (user_id, store_item_id),
    CONSTRAINT fk_wishlist_user       FOREIGN KEY (user_id)       REFERENCES user       (id),
    CONSTRAINT fk_wishlist_store_item FOREIGN KEY (store_item_id) REFERENCES store_item (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
