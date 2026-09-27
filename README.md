# 🥗 Smart Pantry Manager

A native Android application built to streamline kitchen inventory tracking, monitor ingredient expiration dates, reduce food waste, and suggest delicious recipes based on items available in your pantry.

---

## 🌟 Application Overview & Features

- **Pantry Inventory Management**: Add, view, edit, and delete ingredients with quantity, unit of measurement, and expiration date.
- **Smart Expiration Alerts**:
  - **⚠️ Expired**: Highlighted in red with explicit "Expired!" alerts for items past their shelf life.
  - **⚠️ Expires Soon**: Highlighted in vibrant orange/amber for items expiring within 3 days.
  - **✓ Fresh**: Styled in clean emerald green for items with ample shelf life.
- **Recipe Suggestions**: Explore curated recipes with ingredient lists and step-by-step cooking directions matching your pantry inventory.
- **Local Persistence**: Powered by SQLite database (`PantryDatabaseHelper`) for reliable offline storage.
- **Clean Dashboard UI**: Modern Material Design 3 cards, smooth splash screen, and responsive layouts.
- **About & Settings**: Dedicated about section detailing app specifications and course assignment details.

---

## 🛠️ Architecture & Technical Stack

- **Language**: Java
- **UI Framework**: Android XML View System & Google Material Components 3 (`MaterialCardView`, `TextInputLayout`, `FloatingActionButton`)
- **Database**: Native SQLite via `SQLiteOpenHelper`
- **Minimum SDK**: API Level 24 (Android 7.0 Nougat)
- **Target SDK**: API Level 37 (Android 15+)
- **Build System**: Gradle 9.0+ with AGP 9.4.0

---

## 🚀 How to Build and Run

1. **Clone or Open Project**:
   Open Android Studio and select **File > Open**, then navigate to the `SmartPantryManager` root directory.
2. **Gradle Sync**:
   Allow Android Studio to sync Gradle project files and dependencies automatically.
3. **Select Target Device**:
   Choose an Android Virtual Device (AVD) running Android 7.0 (API 24) or higher, or attach a physical device with USB debugging enabled.
4. **Run Application**:
   Click the **Run 'app'** button (or press `Shift + F10`) to build the APK and deploy to your device.

---

## 🎓 Course & Submission Credits

- **Application Name**: Smart Pantry Manager
- **Course**: Mobile Application Development / Android Programming
- **Institution**: Richfield Graduate Institute
- **Developer**: Student Assignment Submission
- **Version**: 1.0.0
