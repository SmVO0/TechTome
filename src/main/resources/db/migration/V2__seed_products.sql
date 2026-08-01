-- Seed product catalog: adds monitors category and 50 products (10 per category).
-- Six items are marked featured for the home page section.
-- Images use placehold.co URLs as placeholders; replace in §3.1 with real product photos.

-- ── Monitors category (missing from initial data) ────────────────────────────────
INSERT INTO category (id, name) VALUES (UUID_TO_BIN(UUID()), 'monitors');

-- ── Computers (10) ──────────────────────────────────────────────────────────────

INSERT INTO store_item (id, name, short_description, description, image, price, stock, featured, category_id) VALUES
(UUID_TO_BIN(UUID()),
 'Dell XPS Tower 8960',
 'Intel Core i7-13700, 32GB RAM, 1TB NVMe SSD',
 'The Dell XPS Tower 8960 packs a 13th-gen Intel Core i7, 32GB DDR5, and a 1TB NVMe SSD into a tool-less chassis. Ready for gaming, content creation, and heavy multitasking out of the box.',
 'https://placehold.co/400x400/0f172a/60a5fa?text=Dell+XPS+Tower',
 899.99, 18, 1, (SELECT id FROM category WHERE name = 'computers')),

(UUID_TO_BIN(UUID()),
 'HP Elite Tower 800 G9',
 'Intel Core i5-12500, 16GB RAM, 512GB SSD',
 'Business-grade reliability with Intel vPro, hardware-enforced security, and remote management built in. A dependable workhorse for enterprise deployments.',
 'https://placehold.co/400x400/1e3a5f/ffffff?text=HP+Elite+800',
 749.99, 12, 0, (SELECT id FROM category WHERE name = 'computers')),

(UUID_TO_BIN(UUID()),
 'ASUS ROG Strix GA35',
 'AMD Ryzen 9 7900X, RTX 4090, 64GB RAM',
 'Flagship gaming desktop with the ROG ROG liquid cooler, PCIe 5.0, and a tool-free side panel. Dominates 4K gaming and professional rendering workflows.',
 'https://placehold.co/400x400/3b0764/c084fc?text=ASUS+ROG+GA35',
 2299.99, 6, 0, (SELECT id FROM category WHERE name = 'computers')),

(UUID_TO_BIN(UUID()),
 'Lenovo IdeaCentre 5i Gen 8',
 'Intel Core i5-13400, 16GB RAM, 512GB SSD',
 'A balanced everyday PC with a slim tower design, Wi-Fi 6, and Lenovo Vantage software for easy system management. Quiet, efficient, and upgradeable.',
 'https://placehold.co/400x400/064e3b/6ee7b7?text=Lenovo+IdeaCentre+5i',
 649.99, 22, 0, (SELECT id FROM category WHERE name = 'computers')),

(UUID_TO_BIN(UUID()),
 'Acer Aspire TC-1780',
 'Intel Core i3-13100, 8GB RAM, 256GB SSD',
 'An affordable entry-level desktop that handles web browsing, office work, and light photo editing without breaking a sweat. Compact and whisper-quiet.',
 'https://placehold.co/400x400/1e293b/94a3b8?text=Acer+Aspire+TC-1780',
 449.99, 30, 0, (SELECT id FROM category WHERE name = 'computers')),

(UUID_TO_BIN(UUID()),
 'Apple Mac Mini M2',
 'Apple M2 chip, 8GB RAM, 256GB SSD',
 'The smallest Mac ever made, now with the Apple M2 chip. Up to 18-hour battery-equivalent efficiency in a 12.9 cm square footprint. Supports two external displays.',
 'https://placehold.co/400x400/f8fafc/1e293b?text=Mac+Mini+M2',
 749.99, 14, 0, (SELECT id FROM category WHERE name = 'computers')),

(UUID_TO_BIN(UUID()),
 'MSI MAG Infinite S3 13',
 'Intel Core i7-13700F, RTX 4070, 32GB RAM',
 'Mid-range gaming powerhouse with the Silent Storm Cooling 3 system, tempered glass side panel, and Mystic Light RGB sync across MSI peripherals.',
 'https://placehold.co/400x400/3f0000/f87171?text=MSI+MAG+Infinite',
 1299.99, 9, 0, (SELECT id FROM category WHERE name = 'computers')),

(UUID_TO_BIN(UUID()),
 'Intel NUC 13 Pro',
 'Intel Core i7-1360P, up to 64GB RAM, Thunderbolt 4',
 'A palm-sized mini PC with desktop-class connectivity: four Thunderbolt 4 ports, dual M.2 slots, and Wi-Fi 6E. Bring your own RAM and storage.',
 'https://placehold.co/400x400/172554/818cf8?text=Intel+NUC+13',
 599.99, 10, 0, (SELECT id FROM category WHERE name = 'computers')),

(UUID_TO_BIN(UUID()),
 'CyberPowerPC Gamer Master GMA8800BST',
 'AMD Ryzen 7 7700X, RX 7900 XT, 32GB DDR5',
 'Pre-built gaming rig ready to run the latest AAA titles at ultra settings. Includes liquid cooling, a 1TB PCIe 4.0 SSD, and free lifetime US-based tech support.',
 'https://placehold.co/400x400/431407/fb923c?text=CyberPower+Gamer',
 1199.99, 7, 0, (SELECT id FROM category WHERE name = 'computers')),

(UUID_TO_BIN(UUID()),
 'Zotac MAGNUS One ECM73070C',
 'Intel Core i7-10700, RTX 3070, 16GB RAM',
 'A compact 8.3-litre gaming PC that fits in a backpack but runs a full desktop GPU. Ideal for LAN parties, small setups, or VR workstations.',
 'https://placehold.co/400x400/1e3a5f/38bdf8?text=Zotac+MAGNUS+One',
 999.99, 5, 0, (SELECT id FROM category WHERE name = 'computers'));

-- ── Laptops (10) ────────────────────────────────────────────────────────────────

INSERT INTO store_item (id, name, short_description, description, image, price, stock, featured, category_id) VALUES
(UUID_TO_BIN(UUID()),
 'Apple MacBook Air M2 13"',
 'Apple M2 chip, 8GB RAM, 256GB SSD, 13.6" Liquid Retina',
 'Fanless, light, and impossibly fast. The M2 MacBook Air delivers up to 18 hours of battery life, a stunning Liquid Retina display, and the full Apple ecosystem in 1.24 kg.',
 'https://placehold.co/400x400/f8fafc/1e293b?text=MacBook+Air+M2',
 1199.99, 20, 1, (SELECT id FROM category WHERE name = 'laptops')),

(UUID_TO_BIN(UUID()),
 'Dell XPS 15 9530',
 'Intel Core i7-13700H, 16GB RAM, 512GB SSD, RTX 4050',
 'A 15.6" OLED display with 3.5K resolution meets a thin-and-light aluminium chassis. InfinityEdge bezels and a class-leading keyboard make this a creator favourite.',
 'https://placehold.co/400x400/0f172a/60a5fa?text=Dell+XPS+15',
 1599.99, 11, 0, (SELECT id FROM category WHERE name = 'laptops')),

(UUID_TO_BIN(UUID()),
 'HP Spectre x360 14',
 'Intel Core i7-1355U, 16GB RAM, 512GB SSD, OLED',
 'A 2-in-1 convertible with a gem-cut design, OLED touch display, and the HP Tile tracker built in. Pen included. Excellent for note-taking and digital art.',
 'https://placehold.co/400x400/1e3a5f/7dd3fc?text=HP+Spectre+x360',
 1349.99, 9, 0, (SELECT id FROM category WHERE name = 'laptops')),

(UUID_TO_BIN(UUID()),
 'Lenovo ThinkPad X1 Carbon Gen 11',
 'Intel Core i7-1365U, 16GB RAM, 512GB SSD, 14" IPS',
 'Military-grade durability (MIL-SPEC 810H), sub-1.12 kg weight, and the legendary ThinkPad keyboard. The gold standard for road-warrior business laptops.',
 'https://placehold.co/400x400/0c0a09/d4d4d4?text=ThinkPad+X1+Carbon',
 1699.99, 8, 0, (SELECT id FROM category WHERE name = 'laptops')),

(UUID_TO_BIN(UUID()),
 'ASUS ZenBook Pro 15 OLED',
 'Intel Core i9-13900H, 16GB RAM, 1TB SSD, RTX 4060',
 'A 15.6" 2.8K OLED display paired with a dedicated GPU and a ScreenPad Plus secondary touchscreen. Ideal for creative professionals who need colour accuracy and raw power.',
 'https://placehold.co/400x400/3b0764/e9d5ff?text=ZenBook+Pro+15',
 1499.99, 7, 0, (SELECT id FROM category WHERE name = 'laptops')),

(UUID_TO_BIN(UUID()),
 'Acer Swift 3 SF314-512',
 'Intel Core i5-1240P, 8GB RAM, 512GB SSD, 14" IPS',
 'Lightweight at 1.4 kg with an aluminium lid, Wi-Fi 6, and a 16:10 aspect ratio display. A reliable, no-fuss laptop for students and everyday productivity.',
 'https://placehold.co/400x400/1e293b/94a3b8?text=Acer+Swift+3',
 799.99, 25, 0, (SELECT id FROM category WHERE name = 'laptops')),

(UUID_TO_BIN(UUID()),
 'Microsoft Surface Laptop 5 13.5"',
 'Intel Core i5-1245U, 8GB RAM, 512GB SSD, PixelSense',
 'A polished premium laptop with a 3:2 PixelSense touch display, Dolby Atmos speakers, and seamless Windows 11 integration. Available in four colours.',
 'https://placehold.co/400x400/0c4a6e/38bdf8?text=Surface+Laptop+5',
 1299.99, 13, 0, (SELECT id FROM category WHERE name = 'laptops')),

(UUID_TO_BIN(UUID()),
 'Razer Blade 15 (2023)',
 'Intel Core i7-13800H, RTX 4070, 16GB DDR5, 165Hz QHD',
 'CNC-milled aluminium unibody, per-key RGB backlighting, and a 165Hz 1440p display in a 17.8 mm chassis. The definitive thin gaming laptop.',
 'https://placehold.co/400x400/14532d/4ade80?text=Razer+Blade+15',
 2499.99, 4, 0, (SELECT id FROM category WHERE name = 'laptops')),

(UUID_TO_BIN(UUID()),
 'Samsung Galaxy Book3 Ultra 16"',
 'Intel Core i9-13900H, RTX 4070, 32GB RAM, 3K AMOLED',
 'Samsung AMOLED meets Intel HX-series performance. The 3K 120Hz display is stunning, and the optional Galaxy Tab integration turns it into a dual-screen workstation.',
 'https://placehold.co/400x400/1e3a5f/93c5fd?text=Galaxy+Book3+Ultra',
 2299.99, 5, 0, (SELECT id FROM category WHERE name = 'laptops')),

(UUID_TO_BIN(UUID()),
 'LG Gram 16 (2023)',
 'Intel Core i7-1360P, 16GB RAM, 512GB SSD, 16" IPS',
 'Under 1.2 kg on a 16-inch laptop. MIL-STD-810H certified, 80Wh battery, and a 16:10 anti-glare IPS display. The lightest laptop in its class.',
 'https://placehold.co/400x400/f0f9ff/1e293b?text=LG+Gram+16',
 1199.99, 10, 0, (SELECT id FROM category WHERE name = 'laptops'));

-- ── Keyboards (10) ──────────────────────────────────────────────────────────────

INSERT INTO store_item (id, name, short_description, description, image, price, stock, featured, category_id) VALUES
(UUID_TO_BIN(UUID()),
 'Logitech MX Keys Advanced',
 'Wireless, backlit, multi-device, low-profile keys',
 'Perfectly shaped keys with concave dips that cradle your fingertips. Smart illumination activates when your hands approach. Pairs with up to 3 devices and works across Windows, macOS, and Linux.',
 'https://placehold.co/400x400/1e293b/38bdf8?text=MX+Keys+Advanced',
 119.99, 35, 1, (SELECT id FROM category WHERE name = 'keyboards')),

(UUID_TO_BIN(UUID()),
 'Keychron K2 Pro QMK',
 'Wireless 75%, hot-swappable, QMK/VIA support',
 'A compact 75% layout with hot-swappable switches, full QMK/VIA programmability, and both Bluetooth 5.1 and USB-C connectivity. Available with Gateron brown, red, or blue switches.',
 'https://placehold.co/400x400/431407/fb923c?text=Keychron+K2+Pro',
 99.99, 28, 0, (SELECT id FROM category WHERE name = 'keyboards')),

(UUID_TO_BIN(UUID()),
 'Corsair K100 RGB',
 'Full-size, Cherry MX Speed Silver, OPX optical option',
 'The K100 features a multi-function iCUE Control Wheel, per-key RGB, and 44-zone LightEdge side lighting. Aluminium frame with a built-in macro engine and 8MB onboard storage.',
 'https://placehold.co/400x400/3f0000/fca5a5?text=Corsair+K100+RGB',
 229.99, 15, 0, (SELECT id FROM category WHERE name = 'keyboards')),

(UUID_TO_BIN(UUID()),
 'Razer BlackWidow V4 Pro',
 'Full-size wireless, Razer Yellow linear switches, media dial',
 'Tri-mode wireless (Razer HyperSpeed, Bluetooth, USB-C), a magnetic wrist rest, and a multi-function media dial for volume, zoom, and scroll. Chroma RGB throughout.',
 'https://placehold.co/400x400/14532d/4ade80?text=BlackWidow+V4+Pro',
 249.99, 12, 0, (SELECT id FROM category WHERE name = 'keyboards')),

(UUID_TO_BIN(UUID()),
 'SteelSeries Apex Pro TKL',
 'TKL, OmniPoint 2.0 adjustable actuation, OLED display',
 'The world is first keyboard with adjustable actuation from 0.1 mm to 4.0 mm per key. The mini-OLED display shows GIF animations, system stats, and Discord notifications.',
 'https://placehold.co/400x400/172554/818cf8?text=Apex+Pro+TKL',
 199.99, 18, 0, (SELECT id FROM category WHERE name = 'keyboards')),

(UUID_TO_BIN(UUID()),
 'Ducky One 3 Mini 60%',
 '60% layout, hot-swappable, PBT double-shot keycaps',
 'A fan-favourite 60% board with south-facing RGB, pre-lubed stabilisers, and Cherry MX or Kailh switch options. Ships with extra novelty keycaps and accessories.',
 'https://placehold.co/400x400/1e293b/e2e8f0?text=Ducky+One+3+Mini',
 119.99, 20, 0, (SELECT id FROM category WHERE name = 'keyboards')),

(UUID_TO_BIN(UUID()),
 'HyperX Alloy FPS Pro',
 'TKL, Cherry MX Red/Blue, red backlight, steel frame',
 'Tenkeyless design with a detachable braided cable for portability. Cherry MX mechanical switches, anti-ghosting, and N-key rollover for every key.',
 'https://placehold.co/400x400/3f0000/f87171?text=HyperX+Alloy+FPS',
 89.99, 22, 0, (SELECT id FROM category WHERE name = 'keyboards')),

(UUID_TO_BIN(UUID()),
 'Roccat Vulcan TKL Pro',
 'TKL, Titan optical switches, AIMO RGB, aluminium frame',
 'Titan optical switches actuate at the speed of light with zero debounce delay. The brushed aluminium top plate and floating key design give a premium desktop look.',
 'https://placehold.co/400x400/3b0764/c084fc?text=Roccat+Vulcan+TKL',
 149.99, 16, 0, (SELECT id FROM category WHERE name = 'keyboards')),

(UUID_TO_BIN(UUID()),
 'ASUS ROG Strix Scope RX TKL',
 'TKL wireless, ROG RX red optical switches, Bluetooth',
 'ROG optical-mechanical switches with a 100M keystroke lifespan, tri-mode connectivity, and a dedicated stealth key that mutes mic and turns off RGB instantly.',
 'https://placehold.co/400x400/3f0000/f97316?text=ROG+Strix+Scope',
 169.99, 14, 0, (SELECT id FROM category WHERE name = 'keyboards')),

(UUID_TO_BIN(UUID()),
 'Anne Pro 2 60%',
 '60% Bluetooth 5.0, hot-swappable, Gateron switches',
 'A beloved compact with tap-and-hold arrow keys, full RGB, and QMK-compatible firmware via the ObinsKit app. A gateway to the mechanical keyboard hobby.',
 'https://placehold.co/400x400/1e293b/6366f1?text=Anne+Pro+2',
 79.99, 30, 0, (SELECT id FROM category WHERE name = 'keyboards'));

-- ── Mice (10) ────────────────────────────────────────────────────────────────────

INSERT INTO store_item (id, name, short_description, description, image, price, stock, featured, category_id) VALUES
(UUID_TO_BIN(UUID()),
 'Logitech MX Master 3S',
 'Wireless, 8K DPI, MagSpeed scroll, ergonomic',
 'The MX Master 3S adds near-silent clicks to the beloved MX Master formula. MagSpeed electromagnetic scrolling flicks through long documents in seconds. Works across three devices via Logi Bolt or Bluetooth.',
 'https://placehold.co/400x400/1e293b/38bdf8?text=MX+Master+3S',
 109.99, 40, 1, (SELECT id FROM category WHERE name = 'mice')),

(UUID_TO_BIN(UUID()),
 'Razer DeathAdder V3 Pro',
 'Wireless, 30K DPI Focus Pro sensor, 90-hour battery',
 'Optimised for claw and fingertip grip with a focused 69g body and Razer HyperSpeed wireless for 4x less wireless latency than traditional gaming mice. Gen-3 optical switches rated for 90M clicks.',
 'https://placehold.co/400x400/14532d/4ade80?text=DeathAdder+V3+Pro',
 159.99, 22, 0, (SELECT id FROM category WHERE name = 'mice')),

(UUID_TO_BIN(UUID()),
 'SteelSeries Rival 650 Wireless',
 'Dual sensor, 256g adjustable weight, quantum wireless',
 'A dual optical sensor system and a customisable weight system with 256 possible configurations. Quantum wireless charges via USB-C with no speed penalty.',
 'https://placehold.co/400x400/172554/818cf8?text=Rival+650+Wireless',
 99.99, 17, 0, (SELECT id FROM category WHERE name = 'mice')),

(UUID_TO_BIN(UUID()),
 'Corsair Dark Core RGB Pro SE',
 'Wireless, 18K DPI, wireless charging compatible, 8 buttons',
 'Ships with a Qi wireless charging pad in the box. Slipstream wireless achieves sub-1ms latency. The 18,000 DPI sensor tracks on any surface, even glass.',
 'https://placehold.co/400x400/3f0000/fca5a5?text=Dark+Core+RGB+Pro',
 89.99, 19, 0, (SELECT id FROM category WHERE name = 'mice')),

(UUID_TO_BIN(UUID()),
 'ASUS ROG Gladius III Wireless',
 'Tri-mode wireless, 36K DPI, hot-swappable switches',
 'Push-fit switch socket lets you swap optical or mechanical switches without tools. Tri-mode connectivity: 2.4GHz, Bluetooth, and USB-C wired. Ergonomic right-hand shape.',
 'https://placehold.co/400x400/3f0000/f97316?text=ROG+Gladius+III',
 79.99, 25, 0, (SELECT id FROM category WHERE name = 'mice')),

(UUID_TO_BIN(UUID()),
 'HyperX Pulsefire Haste 2 Wireless',
 'Wireless, 26K DPI, ultra-light honeycomb, 100-hour battery',
 'A honeycomb shell keeps weight under 61g without sacrificing structural rigidity. The 26,000 DPI sensor and TGS switches deliver competition-ready precision at an accessible price.',
 'https://placehold.co/400x400/3f0000/f87171?text=Pulsefire+Haste+2',
 69.99, 32, 0, (SELECT id FROM category WHERE name = 'mice')),

(UUID_TO_BIN(UUID()),
 'Glorious Model O Wireless',
 'Wireless, 19K DPI, honeycomb, 71g',
 'The Model O Wireless pioneered the ultralight wireless category. An ASCII sensor, Kailh switches, and a braided cable are included alongside the 2.4GHz dongle.',
 'https://placehold.co/400x400/1e293b/e2e8f0?text=Glorious+Model+O',
 79.99, 27, 0, (SELECT id FROM category WHERE name = 'mice')),

(UUID_TO_BIN(UUID()),
 'BenQ Zowie EC2-C',
 'Wired, 3200 DPI, ergonomic right-hand, no software needed',
 'Plug-and-play with no RGB and no software — just a tuned 3200 DPI sensor, medium-right ergonomic shape, and a stress-free braided cable. Preferred by esports professionals.',
 'https://placehold.co/400x400/1e293b/94a3b8?text=Zowie+EC2-C',
 59.99, 38, 0, (SELECT id FROM category WHERE name = 'mice')),

(UUID_TO_BIN(UUID()),
 'Logitech G Pro X Superlight 2',
 'Wireless, HERO 2 25K DPI, 60g, zero-additive PTFE feet',
 'Built in partnership with esports athletes: 60g, the HERO 2 sensor with 32000 DPI, and LIGHTSPEED wireless at under 1ms. The benchmark for competitive gaming mice.',
 'https://placehold.co/400x400/f8fafc/1e293b?text=G+Pro+Superlight+2',
 159.99, 21, 1, (SELECT id FROM category WHERE name = 'mice')),

(UUID_TO_BIN(UUID()),
 'Razer Viper V2 Pro',
 'Wireless, Focus Pro 30K DPI, 58g, optical switches',
 'One of the lightest wireless gaming mice ever at 58g. Gen-3 optical switches click at the speed of light with no debounce delay. HyperSpeed wireless keeps latency near zero.',
 'https://placehold.co/400x400/14532d/86efac?text=Viper+V2+Pro',
 149.99, 18, 0, (SELECT id FROM category WHERE name = 'mice'));

-- ── Monitors (10) ────────────────────────────────────────────────────────────────

INSERT INTO store_item (id, name, short_description, description, image, price, stock, featured, category_id) VALUES
(UUID_TO_BIN(UUID()),
 'LG 27GP950-B UltraGear 4K',
 '27" 4K UHD, 160Hz, Nano IPS, 1ms GtG, HDMI 2.1',
 'One of the first gaming monitors with HDMI 2.1, enabling true 4K 120Hz on consoles. Nano IPS delivers accurate colour (98% DCI-P3) and wide viewing angles alongside a 1ms grey-to-grey response.',
 'https://placehold.co/400x400/0c4a6e/38bdf8?text=LG+27GP950-B',
 799.99, 10, 1, (SELECT id FROM category WHERE name = 'monitors')),

(UUID_TO_BIN(UUID()),
 'Samsung Odyssey G7 32"',
 '32" QHD 1000R curved, 240Hz, 1ms, DisplayHDR 600',
 'A 1000R curvature matches the human field of view for total immersion. Quantum Dot technology and 1000-nit HDR peak brightness deliver vivid colours even in bright rooms.',
 'https://placehold.co/400x400/1e3a5f/60a5fa?text=Odyssey+G7+32',
 699.99, 8, 0, (SELECT id FROM category WHERE name = 'monitors')),

(UUID_TO_BIN(UUID()),
 'ASUS ROG Swift PG279QM',
 '27" QHD IPS, 240Hz, G-Sync Ultimate, 1ms GtG',
 'Drives 240Hz at 1440p with G-Sync Ultimate, achieving tear-free gaming at both high and low framerates. Ergonomic stand with 130mm height adjustment and full tilt, swivel, and pivot.',
 'https://placehold.co/400x400/3f0000/f97316?text=ROG+Swift+PG279QM',
 749.99, 9, 0, (SELECT id FROM category WHERE name = 'monitors')),

(UUID_TO_BIN(UUID()),
 'Dell UltraSharp U2723QE',
 '27" 4K IPS Black, 60Hz, 99% sRGB, USB-C 90W PD',
 'IPS Black technology delivers a 2000:1 contrast ratio — extraordinary for an IPS panel. Ships factory calibrated to delta E < 2 and includes a USB-C port with 90W Power Delivery.',
 'https://placehold.co/400x400/0f172a/e2e8f0?text=Dell+U2723QE',
 649.99, 12, 0, (SELECT id FROM category WHERE name = 'monitors')),

(UUID_TO_BIN(UUID()),
 'BenQ MOBIUZ EX2710Q',
 '27" QHD IPS, 165Hz, FreeSync Premium, HDRi, speakers',
 'BenQ HDRi automatically adjusts display HDR based on ambient lighting. The built-in 2.1ch speakers with treVolo tuning are genuinely usable, making this a great all-in-one gaming display.',
 'https://placehold.co/400x400/064e3b/6ee7b7?text=BenQ+EX2710Q',
 399.99, 20, 0, (SELECT id FROM category WHERE name = 'monitors')),

(UUID_TO_BIN(UUID()),
 'MSI Optix MAG274QRF-QD',
 '27" QHD Quantum Dot IPS, 165Hz, 1ms, FreeSync',
 'Quantum Dot IPS combines wide colour (98% DCI-P3) with fast response and wide viewing angles. Night Vision ambient light sensor, KVM switch, and USB hub built in.',
 'https://placehold.co/400x400/3f0000/fca5a5?text=MSI+MAG274QRF-QD',
 449.99, 15, 0, (SELECT id FROM category WHERE name = 'monitors')),

(UUID_TO_BIN(UUID()),
 'Acer Nitro XZ320QX',
 '31.5" FHD VA curved, 240Hz, 0.5ms VRB, FreeSync',
 'Entry-level curved gaming without compromise: 240Hz refresh rate and 0.5ms MPRT response in a 31.5" 1080p panel. Great for fast-paced FPS and racing games.',
 'https://placehold.co/400x400/1e293b/6366f1?text=Acer+Nitro+XZ320QX',
 349.99, 25, 0, (SELECT id FROM category WHERE name = 'monitors')),

(UUID_TO_BIN(UUID()),
 'ViewSonic Elite XG270Q',
 '27" QHD IPS, 165Hz, G-Sync Compatible, 1ms IPS',
 'IPS-level colour with a 165Hz gaming-grade panel, a removable headphone hanger, and a VESA-compatible stand with height, tilt, swivel, and pivot.',
 'https://placehold.co/400x400/172554/818cf8?text=ViewSonic+XG270Q',
 379.99, 17, 0, (SELECT id FROM category WHERE name = 'monitors')),

(UUID_TO_BIN(UUID()),
 'Gigabyte M28U 4K',
 '28" 4K SS IPS, 144Hz, HDMI 2.1, USB-C 18W, KVM',
 'A 28" 4K display with HDMI 2.1 for console players and a built-in KVM switch for sharing keyboard and mouse between two computers. Also has a USB-C input with 18W charging.',
 'https://placehold.co/400x400/0c4a6e/7dd3fc?text=Gigabyte+M28U',
 549.99, 11, 0, (SELECT id FROM category WHERE name = 'monitors')),

(UUID_TO_BIN(UUID()),
 'AOC CU34G2X Ultrawide',
 '34" WQHD VA curved ultrawide, 144Hz, FreeSync, 1ms MPRT',
 'An immersive 21:9 ultrawide at a mid-range price. 3440x1440 resolution, a 1500R curve, and 144Hz make this a compelling all-rounder for productivity and gaming.',
 'https://placehold.co/400x400/1e3a5f/7dd3fc?text=AOC+CU34G2X',
 499.99, 14, 0, (SELECT id FROM category WHERE name = 'monitors'));
