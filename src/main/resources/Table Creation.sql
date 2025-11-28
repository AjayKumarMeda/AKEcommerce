--User Table creation
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    phonenumber VARCHAR(20),
    profileimageurl TEXT,
    role VARCHAR(50) DEFAULT 'USER',
    isactive BOOLEAN DEFAULT TRUE,
    isemailverified BOOLEAN DEFAULT FALSE,
    isphoneverified BOOLEAN DEFAULT FALSE,
    lastloginat TIMESTAMP,
    createdat TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedat TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--Product and Categories Tables creation
CREATE TABLE categories (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE,
  description TEXT,
  created_at TIMESTAMPTZ DEFAULT NOW(),
  updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE products (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  brand VARCHAR(20),
  price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
  stock_quantity INT DEFAULT 0 CHECK (stock_quantity >= 0),
  category_id BIGINT REFERENCES categories(id) ON DELETE SET NULL,
  image_url TEXT,
  status VARCHAR(20) DEFAULT 'active' CHECK (status IN ('active', 'inactive')),
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

--User cart Table creation
create table usercart (
	id bigserial primary key,
	user_id bigint not null,
	username varchar(225),
	product_id bigint not null references products(id),
	quantity int ,

	constraint unique_user_product unique (user_id,product_id)
);

--User Address Table creation
CREATE TABLE addresses (
    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,

    full_name VARCHAR(255) NOT NULL,
    phone VARCHAR(15) NOT NULL,

	address Varchar(255) not null,
    pincode VARCHAR(10) NOT NULL,
    street TEXT NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,

    address_type VARCHAR(20) DEFAULT 'home'
        CHECK (address_type IN ('home', 'work', 'other')),

    is_default BOOLEAN DEFAULT FALSE,

    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

--User Order Table Creation
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    address_id BIGINT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL CHECK (total_amount >= 0),
    order_status VARCHAR(20) DEFAULT 'PENDING'
        CHECK (order_status IN ('PENDING','CONFIRMED','CANCELLED','DELIVERED')),
    payment_status VARCHAR(20) DEFAULT 'NOT_PAID'
        CHECK (payment_status IN ('NOT_PAID','PAID')),
    created_at TIMESTAMP DEFAULT CURRENT_TIME
);

--user Order_items Table Creation
CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES products(id),
    quantity INT NOT NULL CHECK (quantity > 0),
    price_at_purchase DECIMAL(10,2) NOT NULL CHECK (price_at_purchase >= 0)
    state varchar(50)
);

--Order Status History Table Creation
CREATE TABLE order_status_history (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id),

    old_status VARCHAR(50),
    new_status VARCHAR(50),

    changed_by_id BIGINT,             -- user/admin/agent ID (nullable when system)

    reason TEXT,
    changed_at TIMESTAMP DEFAULT NOW()
);