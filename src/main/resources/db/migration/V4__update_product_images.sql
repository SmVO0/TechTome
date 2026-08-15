-- Replace placehold.co placeholder images with category-appropriate Unsplash photos.
-- URL uniqueness (required by the UNIQUE constraint) is preserved via &v=N suffixes.
-- Photo IDs are pinned to specific Unsplash assets so URLs remain stable.

-- ── Computers ────────────────────────────────────────────────────────────────────
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1587202372775-e229f172b9d7?w=480&q=80&fit=crop&auto=format&v=1'  WHERE name = 'Dell XPS Tower 8960';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1587202372775-e229f172b9d7?w=480&q=80&fit=crop&auto=format&v=2'  WHERE name = 'HP Elite Tower 800 G9';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1593640408182-31c228a25f87?w=480&q=80&fit=crop&auto=format&v=1'  WHERE name = 'ASUS ROG Strix GA35';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1593640408182-31c228a25f87?w=480&q=80&fit=crop&auto=format&v=2'  WHERE name = 'Lenovo IdeaCentre 5i Gen 8';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1616763355548-1b606f439f86?w=480&q=80&fit=crop&auto=format&v=1'  WHERE name = 'Acer Aspire TC-1780';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1616763355548-1b606f439f86?w=480&q=80&fit=crop&auto=format&v=2'  WHERE name = 'Apple Mac Mini M2';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1587202372775-e229f172b9d7?w=480&q=80&fit=crop&auto=format&v=3'  WHERE name = 'MSI MAG Infinite S3 13';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1593640408182-31c228a25f87?w=480&q=80&fit=crop&auto=format&v=3'  WHERE name = 'Intel NUC 13 Pro';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1616763355548-1b606f439f86?w=480&q=80&fit=crop&auto=format&v=3'  WHERE name = 'CyberPowerPC Gamer Master GMA8800BST';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1587202372775-e229f172b9d7?w=480&q=80&fit=crop&auto=format&v=4'  WHERE name = 'Zotac MAGNUS One ECM73070C';

-- ── Laptops ──────────────────────────────────────────────────────────────────────
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=480&q=80&fit=crop&auto=format&v=1'  WHERE name = 'Apple MacBook Air M2 13"';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1525547719571-a2d4ac8945e2?w=480&q=80&fit=crop&auto=format&v=1'  WHERE name = 'Dell XPS 15 9530';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=480&q=80&fit=crop&auto=format&v=2'  WHERE name = 'HP Spectre x360 14';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1525547719571-a2d4ac8945e2?w=480&q=80&fit=crop&auto=format&v=2'  WHERE name = 'Lenovo ThinkPad X1 Carbon Gen 11';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=480&q=80&fit=crop&auto=format&v=3'  WHERE name = 'ASUS ZenBook Pro 15 OLED';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1525547719571-a2d4ac8945e2?w=480&q=80&fit=crop&auto=format&v=3'  WHERE name = 'Acer Swift 3 SF314-512';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1504707748692-419802cf939d?w=480&q=80&fit=crop&auto=format&v=1'  WHERE name = 'Microsoft Surface Laptop 5 13.5"';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1504707748692-419802cf939d?w=480&q=80&fit=crop&auto=format&v=2'  WHERE name = 'Razer Blade 15 (2023)';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1504707748692-419802cf939d?w=480&q=80&fit=crop&auto=format&v=3'  WHERE name = 'Samsung Galaxy Book3 Ultra 16"';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=480&q=80&fit=crop&auto=format&v=4'  WHERE name = 'LG Gram 16 (2023)';

-- ── Keyboards ────────────────────────────────────────────────────────────────────
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1595044426077-d36d9236d54a?w=480&q=80&fit=crop&auto=format&v=1'  WHERE name = 'Logitech MX Keys Advanced';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=480&q=80&fit=crop&auto=format&v=1'  WHERE name = 'Keychron K2 Pro QMK';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1595044426077-d36d9236d54a?w=480&q=80&fit=crop&auto=format&v=2'  WHERE name = 'Corsair K100 RGB';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=480&q=80&fit=crop&auto=format&v=2'  WHERE name = 'Razer BlackWidow V4 Pro';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1595044426077-d36d9236d54a?w=480&q=80&fit=crop&auto=format&v=3'  WHERE name = 'SteelSeries Apex Pro TKL';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=480&q=80&fit=crop&auto=format&v=3'  WHERE name = 'Ducky One 3 Mini 60%';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1595044426077-d36d9236d54a?w=480&q=80&fit=crop&auto=format&v=4'  WHERE name = 'HyperX Alloy FPS Pro';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=480&q=80&fit=crop&auto=format&v=4'  WHERE name = 'Roccat Vulcan TKL Pro';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1595044426077-d36d9236d54a?w=480&q=80&fit=crop&auto=format&v=5'  WHERE name = 'ASUS ROG Strix Scope RX TKL';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=480&q=80&fit=crop&auto=format&v=5'  WHERE name = 'Anne Pro 2 60%';

-- ── Mice ─────────────────────────────────────────────────────────────────────────
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1527814050087-3793815479db?w=480&q=80&fit=crop&auto=format&v=1'  WHERE name = 'Logitech MX Master 3S';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1527814050087-3793815479db?w=480&q=80&fit=crop&auto=format&v=2'  WHERE name = 'Razer DeathAdder V3 Pro';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1527814050087-3793815479db?w=480&q=80&fit=crop&auto=format&v=3'  WHERE name = 'SteelSeries Rival 650 Wireless';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=480&q=80&fit=crop&auto=format&v=1'  WHERE name = 'Corsair Dark Core RGB Pro SE';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=480&q=80&fit=crop&auto=format&v=2'  WHERE name = 'ASUS ROG Gladius III Wireless';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1527814050087-3793815479db?w=480&q=80&fit=crop&auto=format&v=4'  WHERE name = 'HyperX Pulsefire Haste 2 Wireless';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=480&q=80&fit=crop&auto=format&v=3'  WHERE name = 'Glorious Model O Wireless';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1527814050087-3793815479db?w=480&q=80&fit=crop&auto=format&v=5'  WHERE name = 'BenQ Zowie EC2-C';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=480&q=80&fit=crop&auto=format&v=4'  WHERE name = 'Logitech G Pro X Superlight 2';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1527814050087-3793815479db?w=480&q=80&fit=crop&auto=format&v=6'  WHERE name = 'Razer Viper V2 Pro';

-- ── Monitors ─────────────────────────────────────────────────────────────────────
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1527443224154-c4a573d5dd31?w=480&q=80&fit=crop&auto=format&v=1'  WHERE name = 'LG 27GP950-B UltraGear 4K';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1593305841991-05c297ba4575?w=480&q=80&fit=crop&auto=format&v=1'  WHERE name = 'Samsung Odyssey G7 32"';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1527443224154-c4a573d5dd31?w=480&q=80&fit=crop&auto=format&v=2'  WHERE name = 'ASUS ROG Swift PG279QM';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1593305841991-05c297ba4575?w=480&q=80&fit=crop&auto=format&v=2'  WHERE name = 'Dell UltraSharp U2723QE';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1527443224154-c4a573d5dd31?w=480&q=80&fit=crop&auto=format&v=3'  WHERE name = 'BenQ MOBIUZ EX2710Q';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1593305841991-05c297ba4575?w=480&q=80&fit=crop&auto=format&v=3'  WHERE name = 'MSI Optix MAG274QRF-QD';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1527443224154-c4a573d5dd31?w=480&q=80&fit=crop&auto=format&v=4'  WHERE name = 'Acer Nitro XZ320QX';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1593305841991-05c297ba4575?w=480&q=80&fit=crop&auto=format&v=4'  WHERE name = 'ViewSonic Elite XG270Q';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1527443224154-c4a573d5dd31?w=480&q=80&fit=crop&auto=format&v=5'  WHERE name = 'Gigabyte M28U 4K';
UPDATE store_item SET image = 'https://images.unsplash.com/photo-1593305841991-05c297ba4575?w=480&q=80&fit=crop&auto=format&v=5'  WHERE name = 'AOC CU34G2X Ultrawide';
