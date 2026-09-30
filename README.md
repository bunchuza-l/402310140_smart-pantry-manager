# 🥗 Smart Pantry Manager

A native Android application developed for **402310140 Mobile_APP_Dev** to help users track home ingredients, monitor expiration dates, eliminate food waste, and suggest recipes strictly based on available pantry items.

---

## 📋 Assignment & Guidelines Compliance

- **Course**: 402310140 Mobile_APP_Dev
- **Programming Language**: **100% Java** (Android SDK)
- **Database Engine**: Native SQLite (`SQLiteOpenHelper` with full local CRUD persistence)
- **Core Value & Strict-Matching Rule**: A recipe is **only** suggested if **every single required ingredient** is present in the user's pantry. No shopping trip required!
- **Pre-Loaded Recipes**: Database seeded with **18 complete recipes** on first app launch.
- **Bonus Stretch Feature**: Includes a clearly separated **"Almost There (Missing 1 Ingredient)"** section.

---

## 🌟 Key Features

1. **Pantry Inventory Management**:
   - **Create**: Add new ingredients with quantity, unit, and expiration date.
   - **Read**: Dynamic `ListView` bound directly to SQLite database.
   - **Urgency Priority Sorting**: Items are automatically ordered in ascending order of urgency:
     1. **⚠️ EXPIRED**: Highlighted in red (top priority).
     2. **⚠️ EXPIRES SOON**: Highlighted in vibrant orange/amber for items expiring within 3 days.
     3. **✓ FRESH**: Styled in emerald green for items with ample shelf life.
     *(Items within each category are ordered ascending by expiration date).*
   - **Update**: Edit existing ingredient amounts or expiration dates.
   - **Delete**: Remove ingredients with confirmation dialogs.

2. **Strict Recipe Matching Engine**:
   - Compares recipe requirements against current SQLite pantry items.
   - **Strict Match**: Displays `STRICT MATCH 🍳` when 100% of required ingredients are present.
   - **Almost There**: Displays `ALMOST THERE (MISSING 1)` showing the 1 missing item.
   - **Zero-Match Feedback**: Friendly alert screen when no recipes strictly match current pantry contents.

3. **Recipe Details**:
   - Displays required ingredients and step-by-step preparation directions.

4. **Branded Initial Splash Page & Dashboard**:
   - Hero Material Card displaying cereal bowl & milk logo, app title **"Smart Pantry Manager"**, and slogan **"Master Your Pantry. Eliminate Waste. Cook Genius."**

5. **Settings & Preferences**:
   - Toggle for expiring-soon alerts, app preferences, and assignment credits.

---

## 🛠️ Architecture & Tech Stack

- **Language**: Java
- **UI Framework**: Android XML Views & Material Components 3 (`MaterialCardView`, `TextInputLayout`, `SwitchMaterial`, `FloatingActionButton`)
- **Persistence**: SQLite (`SQLiteOpenHelper`)
- **Min SDK**: API Level 24 (Android 7.0 Nougat)
- **Target SDK**: API Level 37 (Android 15)

---

## 🚀 How to Build & Run

1. Open Android Studio and select **File > Open**, navigating to the project root directory.
2. Sync Gradle dependencies.
3. Select an Android Virtual Device (AVD) running API 24 or higher.
4. Click **Run 'app'** (`Shift + F10`) to build and launch the application.

---

## 🎓 Submission Credits

- **Application Name**: Smart Pantry Manager
- **Slogan**: Master Your Pantry. Eliminate Waste. Cook Genius.
- **Course**: 402310140 Mobile_APP_Dev
- **Institution**: Richfield Graduate Institute
- **Developer**: Student Assignment Submission
- **Version**: 1.0.0
