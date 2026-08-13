<p align="center">
  <img src="src/image/id_1_logo.png" alt="FoodExpress Logo" width="120"/>
</p>

<h1 align="center">Javafx project of level 1 term 2</h1>
<h2 align="center">🍔 FoodExpress — Online Food Ordering System</h2>
<p align="center">
  <b>A multi-user, real-time food delivery platform built with JavaFX & Socket Programming</b>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-20-blue?logo=openjdk" alt="Java 20"/>
  <img src="https://img.shields.io/badge/JavaFX-20-green?logo=java" alt="JavaFX 20"/>
  <img src="https://img.shields.io/badge/Maven-3.x-C71A36?logo=apachemaven" alt="Maven"/>
  <img src="https://img.shields.io/badge/Architecture-Client--Server-orange" alt="Client-Server"/>
  <img src="https://img.shields.io/badge/UI-SceneBuilder-purple" alt="SceneBuilder"/>
</p>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Screenshots](#-screenshots)
- [System Architecture](#-system-architecture)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [How to Run](#-how-to-run)
- [Test Credentials](#-test-credentials)
- [User Guide](#-user-guide)
- [Networking Protocol](#-networking-protocol)
- [Design Patterns](#-design-patterns)
- [FXML & SceneBuilder](#-fxml--scenebuilder)
- [Styling](#-styling)
- [Git Configuration](#-git-configuration)
- [Known Limitations](#-known-limitations)
- [Future Improvements](#-future-improvements)

---

## 🌟 Overview

**FoodExpress** is a full-stack food delivery application developed as part of the **CSE-108** course project. It enables **customers** to browse restaurants, search for food, and place orders — while **restaurant owners** can manage menus and accept/reject orders in real-time.

The application follows a **Client-Server architecture** where a central server handles all communications between multiple clients using **Java Socket Programming** with multi-threaded request handling.

> **Two types of users:**
> - 🛒 **Users (Customers)** — Browse, search, and order food
> - 🏪 **Clients (Restaurant Owners)** — Manage menus and process orders

---

## ✨ Features

### User (Customer) Features
| Feature | Description |
|---------|-------------|
| 🔐 Registration & Login | Create an account and securely log in |
| 🔍 Advanced Search | Search restaurants by **name, category, score, price, zip code** |
| 🍕 Food Search | Search food by **name, category, price range** — globally or within a restaurant |
| 🛒 Shopping Cart | Add food items with quantity tracking, manage cart |
| 📦 Order Placement | Confirm and send orders to respective restaurants |
| 🔔 Real-Time Notifications | Receive live alerts when orders are accepted or rejected |
| 🎠 Food Carousel | Animated food image slideshow on the home page |

### Client (Restaurant Owner) Features
| Feature | Description |
|---------|-------------|
| 🔐 Registration & Login | Register restaurant with details (name, score, price, categories) |
| 📋 Menu Management | View current menu and add new food items |
| 📬 Order Reception | Receive customer orders in real-time |
| ✅❌ Order Processing | View order details, accept or reject each order |
| 📊 Dashboard | View restaurant info, menu table, and pending orders |

---

## 📸 Screenshots

### Login Page
<p align="center">
  <img src="src/main/resources/com/example/demo/image/loginPage.jpg" alt="Login Page" width="700"/>
</p>

> The login page features animated button transitions and supports both User and Client authentication. Users can register a new account or log in with existing credentials.

### User Home — Food Search & Cart
<p align="center">
  <img src="src/main/resources/com/example/demo/image/user.png" alt="User Home" width="300"/>
</p>

> The user dashboard includes a dynamic food carousel, advanced combo-box search filters, results table with "Add to Cart" functionality, and a live cart panel.

### Restaurant Search
<p align="center">
  <img src="src/main/resources/com/example/demo/image/restaurantList.jpg" alt="Restaurant List" width="700"/>
</p>

> Users can browse and filter restaurants by various criteria. Results are displayed in a clean ListView with custom styling.

### Restaurant Dashboard
<p align="center">
  <img src="src/main/resources/com/example/demo/image/res.jpg" alt="Restaurant Dashboard" width="700"/>
</p>

> Restaurant owners see their info, current menu (TableView), and incoming orders with real-time updates.

---

## 🏗 System Architecture

```
┌────────────────────────────────────────────────────────────────────┐
│                        FoodExpress System                          │
├────────────────────────────────────────────────────────────────────┤
│                                                                    │
│    ┌──────────────┐         ┌──────────────────┐                  │
│    │  User Client  │◄──────►│                  │                  │
│    │  (Customer)   │  TCP   │                  │                  │
│    │  JavaFX GUI   │ Socket │                  │                  │
│    └──────────────┘         │    SERVER         │                  │
│                             │  (Port 33333)    │                  │
│    ┌──────────────┐         │                  │                  │
│    │ Client Client │◄──────►│  ┌────────────┐  │  ┌────────────┐ │
│    │ (Restaurant)  │  TCP   │  │ReadThread   │  │  │ Data Files │ │
│    │  JavaFX GUI   │ Socket │  │Server       │──┼─►│            │ │
│    └──────────────┘         │  │(per client) │  │  │ menu.txt   │ │
│                             │  └────────────┘  │  │ restaurant │ │
│    ┌──────────────┐         │                  │  │   .txt     │ │
│    │ More Clients  │◄──────►│  ┌────────────┐  │  │ user/client│ │
│    │    . . .      │  TCP   │  │Restaurant   │  │  │ Credentials│ │
│    └──────────────┘  Socket │  │Database     │  │  │   .txt    │ │
│                             │  │(Search Eng.)│  │  └────────────┘ │
│                             │  └────────────┘  │                  │
│                             └──────────────────┘                  │
│                                                                    │
└────────────────────────────────────────────────────────────────────┘
```

### Communication Flow

```
┌────────────┐                  ┌────────────┐                  ┌──────────────┐
│   User     │                  │   Server   │                  │  Restaurant  │
│  (Customer)│                  │            │                  │   (Client)   │
└─────┬──────┘                  └─────┬──────┘                  └──────┬───────┘
      │                               │                                │
      │  1. Login Request              │                                │
      │──────────────────────────────►│                                │
      │  2. Auth Response (Boolean)    │                                │
      │◄──────────────────────────────│                                │
      │                               │                                │
      │  3. Search Food/Restaurant     │                                │
      │──────────────────────────────►│                                │
      │  4. Results (List<Food>)       │                                │
      │◄──────────────────────────────│                                │
      │                               │                                │
      │  5. Place Order                │                                │
      │──────────────────────────────►│                                │
      │                               │  6. Forward Order               │
      │                               │──────────────────────────────►│
      │                               │                                │
      │                               │  7. Accept/Reject Order        │
      │                               │◄──────────────────────────────│
      │  8. Order Status Alert         │                                │
      │◄──────────────────────────────│                                │
      │                               │                                │
```

### Threading Model

```
Server (Main Thread)
  │
  ├── Accepts Connection ──► ReadThreadServer (Thread 1) ──► Handles Client 1
  ├── Accepts Connection ──► ReadThreadServer (Thread 2) ──► Handles Client 2
  ├── Accepts Connection ──► ReadThreadServer (Thread 3) ──► Handles Client 3
  └── ...

User Client (JavaFX Application Thread)
  ├── PresentFoodImage Thread ──► Rotates food carousel images every 3s
  └── RealTimeAlertThread   ──► Listens for order status notifications

Restaurant Client (JavaFX Application Thread)
  └── ordersThread ──► Monitors incoming orders, updates orders table
```

---

## 🛠 Tech Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Java** | 20 | Core programming language |
| **JavaFX** | 20 | GUI framework for desktop application |
| **FXML** | — | XML-based UI layout (designed with SceneBuilder) |
| **CSS** | — | Custom styling for JavaFX components |
| **Maven** | 3.x | Build tool and dependency management |
| **Java Sockets** | — | TCP client-server communication |
| **Java Serialization** | — | Object transmission over network |
| **SceneBuilder** | — | Visual FXML layout designer |
| **JUnit 5** | 5.9.2 | Unit testing framework |

### Maven Dependencies

```xml
<!-- JavaFX Controls (Button, TableView, ComboBox, etc.) -->
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>20</version>
</dependency>

<!-- JavaFX FXML (FXMLLoader, @FXML annotations) -->
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-fxml</artifactId>
    <version>20</version>
</dependency>

<!-- JUnit 5 (Testing) -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.9.2</version>
    <scope>test</scope>
</dependency>
```

---

## 📁 Project Structure

```
demo1/
├── 📄 pom.xml                          # Maven configuration
├── 📄 mvnw / mvnw.cmd                  # Maven wrapper scripts
├── 📄 menu.txt                          # Root menu data (backup)
├── 📄 .gitignore                        # Git ignore rules
│
├── 📁 src/
│   ├── 📁 image/
│   │   └── 🖼 id_1_logo.png            # Application logo
│   │
│   └── 📁 main/
│       ├── 📁 java/
│       │   ├── 📄 module-info.java      # Java module descriptor
│       │   └── 📁 com/example/demo/
│       │       │
│       │       │── 🔷 CORE SERVER
│       │       ├── Server.java              # Main server (port 33333)
│       │       ├── ReadThreadServer.java    # Per-client request handler thread
│       │       ├── SocketWrapper.java       # Socket I/O wrapper (ObjectStreams)
│       │       │
│       │       │── 🔷 APPLICATION ENTRY
│       │       ├── HelloApplication.java    # JavaFX Application entry point
│       │       ├── HelloController.java     # Login screen controller
│       │       │
│       │       │── 🔷 DATA MODELS
│       │       ├── Food.java               # Food item model (Serializable)
│       │       ├── FoodData.java           # Food data loader & persistence
│       │       ├── FoodListForSend.java    # Food + quantity (for cart/orders)
│       │       ├── Restaurant.java         # Restaurant model (Serializable)
│       │       ├── RestaurantData.java     # Restaurant data loader & persistence
│       │       ├── RestaurantDatabase.java # Central search engine (16+ methods)
│       │       │
│       │       │── 🔷 USER (CUSTOMER) CONTROLLERS
│       │       ├── userRegistraitonController.java  # User registration
│       │       ├── UserHomeController.java          # User dashboard & search
│       │       │
│       │       │── 🔷 CLIENT (RESTAURANT) CONTROLLERS
│       │       ├── ClientRegistrationController.java # Restaurant registration
│       │       ├── ClientHomeController.java        # Restaurant dashboard
│       │       ├── AddFoodController.java           # Add menu items
│       │       ├── restaurantSearchController.java  # Restaurant search UI
│       │       ├── ViewOrder.java                   # Order details modal
│       │       │
│       │       │── 🔷 THREADING
│       │       ├── ordersThread.java        # Real-time order listener
│       │       ├── UserThread.java          # User thread (placeholder)
│       │       │
│       │       │── 🔷 UTILITIES
│       │       ├── Signiture.java           # Signature utility
│       │       ├── TabelView.java           # Table view helper
│       │       │
│       │       │── 🔷 DATA FILES
│       │       ├── menu.txt                 # Food items database (CSV)
│       │       ├── restaurant.txt           # Restaurants database (CSV)
│       │       ├── userCredentials.txt      # User login credentials
│       │       └── clientCredentials.txt    # Restaurant login credentials
│       │
│       └── 📁 resources/com/example/demo/
│           │
│           │── 🔷 FXML LAYOUTS (SceneBuilder)
│           ├── login.fxml               # Login page
│           ├── userHome.fxml            # User dashboard
│           ├── clientHome.fxml          # Restaurant dashboard
│           ├── userRegistration.fxml    # User signup form
│           ├── clientRegistration.fxml  # Restaurant signup form
│           ├── addFood.fxml             # Add food dialog
│           ├── restaurantListShow.fxml  # Restaurant search results
│           ├── viewOrder.fxml           # Order details modal
│           │
│           │── 🔷 STYLESHEETS
│           ├── login.css                # Login page styling
│           ├── alertStyle.css           # Alert dialog styling
│           ├── combo-box.css            # ComboBox custom theme
│           ├── list-view.css            # ListView hover/selection effects
│           ├── table-view.css           # TableView header & row styling
│           │
│           └── 📁 image/               # UI images & food carousel
│               ├── loginPage.jpg
│               ├── user.png
│               ├── res.jpg
│               ├── restaurantList.jpg
│               ├── food0.jpg ~ food10.jpg  # Food carousel images
│               └── ...
│
└── 📁 target/                           # Maven build output (auto-generated)
```

---

## ⚙ Prerequisites

Before running this project, ensure you have the following installed:

| Requirement | Version | Download |
|------------|---------|----------|
| **Java JDK** | 20 or higher | [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/) |
| **Maven** | 3.6+ | [Apache Maven](https://maven.apache.org/download.cgi) |
| **SceneBuilder** *(optional, for editing FXML)* | 20+ | [Gluon SceneBuilder](https://gluonhq.com/products/scene-builder/) |

### Verify Installation

```bash
# Check Java version
java -version
# Expected: java version "20.x.x" or higher

# Check Maven version
mvn -version
# Expected: Apache Maven 3.x.x
```

> **Note:** JavaFX 20 is pulled automatically by Maven — no separate JavaFX SDK installation is needed.

---

## 🚀 How to Run

### Step 1: Clone the Repository

```bash
git clone <repository-url>
cd demo1
```

### Step 2: Build the Project

```bash
# Using Maven wrapper (recommended)
./mvnw clean compile

# OR using system Maven
mvn clean compile
```

### Step 3: Start the Server

The server **must be started first** before any clients can connect.

```bash
# Option 1: Run Server using Maven exec
mvn exec:java -Dexec.mainClass="com.example.demo.Server"

# Option 2: Run from your IDE
# Right-click Server.java → Run 'Server.main()'
```

You should see the server start and listen on **port 33333**:
```
Server has started...
```

### Step 4: Launch the Application (Client)

Open a **new terminal** (keep the server running) and run:

```bash
# Using Maven JavaFX plugin
mvn clean javafx:run

# OR using Maven wrapper
./mvnw clean javafx:run
```

> **💡 Tip:** You can launch **multiple instances** of the application to simulate multiple users and restaurants interacting simultaneously.

### Step 5: Login or Register

The login page will appear:

1. **Login as User** → Enter username & password → Click "User Log In"
2. **Login as Client (Restaurant)** → Enter restaurant ID & password → Click "Client Log In"
3. **Register** → Click "Registration" to create a new account

### Running from IDE (IntelliJ IDEA / Eclipse)

1. **Import** the project as a Maven project
2. Let the IDE download dependencies
3. **Run** `Server.java` → `main()` method
4. **Run** `HelloApplication.java` → `main()` method (can run multiple instances)

---

## 🔑 Test Credentials

### User Accounts (Customers)

| Username | Password |
|----------|----------|
| `mehedi` | `123`    |

### Client Accounts (Restaurants)

| Restaurant ID | Password | Restaurant Name |
|---------------|----------|-----------------|
| `1`           | `123`    | KFC             |
| `2`           | `234`    | IHOP            |
| `3`           | `345`    | Starbucks       |
| `4`           | `456`    | McDonald's      |
| `5`           | `4567`   | —               |
| `6`           | `111`    | —               |

---

## 📖 User Guide

### 👤 For Users (Customers)

#### 1. Registration
- From the login page, click **"Registration"**
- Select **"As User"**
- Fill in: Username, Full Name, Password
- Click **Register** — credentials are saved to `userCredentials.txt`

#### 2. Search for Food
After logging in, use the **search panel** on the left:

| Search Type | Description |
|------------|-------------|
| Search by Food Name | Find food items by name across all restaurants |
| Search by Category | Filter by cuisine type (e.g., "Pizza", "Burger") |
| Search by Price Range | Set min/max price range |
| Search in a Restaurant | Search within a specific restaurant |
| Search by Score | Filter restaurants by rating score |
| Search by Zip Code | Find restaurants by location |
| Costliest Food | Show the most expensive item in a restaurant |
| List All Restaurants | View all registered restaurants with food counts |

#### 3. Add to Cart
- Browse search results in the **table view**
- Click **"Add to Cart"** button next to any food item
- Adjust quantities in the **Cart panel** on the right
- Click **"+"** or **"-"** to modify quantities

#### 4. Place an Order
- Review your cart items
- Click **"Confirm Order"**
- Order is sent to the respective restaurant(s)
- Wait for real-time **notification** (accepted ✅ or rejected ❌)

#### 5. Food Carousel
- The home page features an **animated food carousel** that rotates through food images every 3 seconds

---

### 🏪 For Clients (Restaurant Owners)

#### 1. Registration
- From the login page, click **"Registration"**
- Select **"As Client"**
- Fill in: Restaurant ID, Name, Score, Price Level, Zip Code
- Add up to **3 cuisine categories**
- Set a password → credentials saved to `clientCredentials.txt`

#### 2. View Dashboard
After login, the dashboard shows:
- **Restaurant Information** (name, score, categories)
- **Menu Table** — all current food items
- **Pending Orders Table** — incoming orders from customers

#### 3. Add Food Items
- Click **"Add Food"** button
- Fill in: Food Name, Category, Price
- Submit → item is added to the menu and database

#### 4. Process Orders
- When a new order arrives, it appears in the **Orders Table**
- Click **"Details"** to view order items and quantities
- Click **"Accept"** ✅ to confirm the order
- Click **"Decline"** ❌ to reject the order
- The customer receives a **real-time notification** of your decision

---

## 🌐 Networking Protocol

Communication between clients and the server uses **serialized Java objects** over **TCP sockets**. Commands are sent as comma-separated strings.

### Command Categories

#### 🔐 Authentication
```
VerifyUser,<username>,<password>         → Boolean
VerifyClient,<restaurantId>,<password>   → Boolean
```

#### 🔍 Search Commands
```
SearchFood,<foodName>                              → List<Food>
SearchRestaurantByName,<name>                      → List<Food>
SearchByCategory,<category>                        → List<Food>
SearchByPrice,<minPrice>,<maxPrice>                → List<Food>
SearchFoodInAGivenRestaurant,<food>,<restaurant>   → List<Food>
SearchByCategoryInARestaurant,<cat>,<restaurant>   → List<Food>
DisplayCostliestFoodInARestaurant,<restaurant>     → Food
ListOfRestaurantsAndTotalFoodItemOnTheMenu          → List<String>
```

#### 📦 Order Commands
```
sendOrder,<restaurantId>,<userName>                → (forwards order to restaurant)
acceptOrder,<clientKey>,<orderNo>,<restaurantName> → (notifies user)
rejectOrder,<clientKey>,<orderNo>,<restaurantName> → (notifies user)
```

#### 📝 Registration Commands
```
newUser,<username>,<fullName>,<password>            → void
RecordClientCredentials,<restaurantId>,<password>   → void
```

#### ✅ Validation Commands
```
isRestaurantIdValid,<id>         → Boolean (checks uniqueness)
isRestaurantNameValid,<name>     → Boolean (checks uniqueness)
userValidityCheck,<username>     → Boolean (checks uniqueness)
```

---

## 🎨 Design Patterns

### MVC (Model-View-Controller)

```
┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐
│      MODEL        │     │       VIEW       │     │    CONTROLLER    │
│                   │     │                  │     │                  │
│ • Food.java       │     │ • login.fxml     │     │ • HelloController│
│ • Restaurant.java │◄───►│ • userHome.fxml  │◄───►│ • UserHome       │
│ • FoodData.java   │     │ • clientHome     │     │   Controller     │
│ • RestaurantData  │     │   .fxml          │     │ • ClientHome     │
│ • RestaurantDB    │     │ • CSS files      │     │   Controller     │
│ • FoodListForSend │     │                  │     │ • ViewOrder      │
└──────────────────┘     └──────────────────┘     └──────────────────┘
```

### Other Patterns Used

| Pattern | Implementation |
|---------|---------------|
| **Observer** | `ObservableList` for real-time TableView updates |
| **Adapter** | `SocketWrapper` wraps raw Socket with ObjectStreams |
| **Thread-per-Client** | `ReadThreadServer` spawned for each connected client |
| **Command** | String-based command protocol for client-server communication |
| **Singleton-like** | `RestaurantDatabase` serves as central search authority |
| **Factory** | Dynamic Stage creation for modal dialogs |

---

## 🎭 FXML & SceneBuilder

All UI layouts were created using **[Gluon SceneBuilder](https://gluonhq.com/products/scene-builder/)** — a visual drag-and-drop tool for designing JavaFX interfaces.

### FXML Files Overview

| File | Screen | Dimensions | Key Components |
|------|--------|------------|----------------|
| `login.fxml` | Login/Registration | 1104 × 892 | TextField, PasswordField, Animated Buttons |
| `userHome.fxml` | User Dashboard | 1255 × 970 | ComboBox, TableView, ImageView (carousel), Cart Panel |
| `clientHome.fxml` | Restaurant Dashboard | 1104 × 892 | Labels, TableView (menu), TableView (orders) |
| `userRegistration.fxml` | User Signup | 561 × 531 | TextFields, Register Button |
| `clientRegistration.fxml` | Restaurant Signup | 561 × 776 | TextFields (7 fields), Category inputs |
| `addFood.fxml` | Add Food Dialog | 561 × 531 | TextField (name, category, price) |
| `restaurantListShow.fxml` | Restaurant Search | 1050 × 667 | ListView, ComboBox, Search Button |
| `viewOrder.fxml` | Order Details | 750 × 605 | TableView, Accept/Decline Buttons |

### How to Edit FXML Files

1. Install [Gluon SceneBuilder](https://gluonhq.com/products/scene-builder/)
2. Open any `.fxml` file from `src/main/resources/com/example/demo/`
3. Drag and drop components, modify properties
4. Save — changes reflect in the application

> **💡 Tip:** In IntelliJ IDEA, right-click any `.fxml` file → **Open in SceneBuilder**

---

## 🎨 Styling

Custom CSS files provide a modern, themed look to the application:

| CSS File | Target | Highlights |
|----------|--------|------------|
| `login.css` | Login page | Button gradients, text field styling, drop shadow hover effects |
| `table-view.css` | All TableViews | Orange header (`#FF5733`), alternating row colors, blue selection (`#3498db`) |
| `combo-box.css` | ComboBoxes | Custom dropdown colors, `#0078d4` hover effect, font sizing |
| `list-view.css` | ListViews | Hover scale animation (`1.1x`), selection highlighting |
| `alertStyle.css` | Alert dialogs | Custom alert dialog theming |

### Color Scheme

| Element | Color | Hex |
|---------|-------|-----|
| Primary (Headers/Accents) | 🟠 Orange | `#FF5733` |
| Hover/Selection | 🔵 Blue | `#0078d4` / `#3498db` |
| Background | ⬜ Light | `#f4f4f4` |
| Text | ⬛ Dark | Default |

---

## 📂 Git Configuration

### .gitignore

The project `.gitignore` file excludes:

```gitignore
target/                    # Maven build output
.idea/                     # IntelliJ IDEA config
*.iws, *.iml, *.ipr       # IDE project files
.classpath, .project       # Eclipse config
/nbproject/private/        # NetBeans config
.vscode/                   # VS Code config
.DS_Store                  # macOS metadata
build/                     # Alternative build output
```

> **⚠ Note:** Data files (`menu.txt`, `restaurant.txt`, `userCredentials.txt`, `clientCredentials.txt`) are **not** in `.gitignore` and are tracked by Git. This is intentional for demo purposes — these files contain sample data needed to run the application. In a production environment, credentials should be stored securely and excluded from version control.

---

## ⚠ Known Limitations

| Area | Limitation |
|------|-----------|
| **Storage** | Uses flat text files (`.txt`) instead of a proper database |
| **Security** | Passwords stored in plain text, no encryption |
| **Platform** | Some file paths may be Windows-specific |
| **Concurrency** | Limited handling of concurrent order modifications |
| **Validation** | Basic input validation on forms |
| **Scalability** | Thread-per-client model; may not scale to thousands of users |
| **Error Handling** | Minimal error handling for network failures |

---

## 🔮 Future Improvements

- [ ] 🗄 Replace text files with a proper database (MySQL / SQLite)
- [ ] 🔒 Implement password hashing (BCrypt) and encrypted communication (SSL/TLS)
- [ ] 📱 Add a web-based or mobile client
- [ ] 📊 Add analytics dashboard for restaurant owners
- [ ] 💳 Payment integration
- [ ] ⭐ User rating and review system
- [ ] 📍 GPS-based restaurant discovery
- [ ] 🖼 Food image upload support
- [ ] 🧪 Comprehensive unit and integration tests
- [ ] 🐳 Docker containerization for easy deployment

---

## 📄 Data File Formats

### menu.txt
```
<restaurantId>,<category>,<foodName>,<price>
```
Example:
```
1,Fast Food,Fried Chicken,12.99
2,Breakfast,Pancakes,8.50
```

### restaurant.txt
```
<id>,<name>,<score>,<priceLevel>,<zipCode>,<category1>,<category2>,<category3>
```
Example:
```
1,KFC,4.2,$$,10001,Fast Food,Chicken,American
```

### userCredentials.txt
```
<username>,<password>
```

### clientCredentials.txt
```
<restaurantId>,<password>
```

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/my-feature`
3. Commit changes: `git commit -m "Add my feature"`
4. Push to branch: `git push origin feature/my-feature`
5. Open a Pull Request

---

<p align="center">
  Made with ❤️ using JavaFX & Java Socket Programming
</p>
<p align="center">
  <b>CSE-108 Course Project</b>
</p>
