# Medcine_Expiry_tracker
It is simple application made using android studio that is based on future implementation. if medicines contain qr with expiry date and name .The app stores the data when scanned and notify user . It is just some innovative idea to reduce the time consumed by other app while entering data manually. 
This is the Structure
MedExpiryTracker/
│
├── app/
│   ├── manifests/
│   │   └── AndroidManifest.xml
│
│   ├── java/
│   │   └── com.example.medexpiry/
│   │       ├── MainActivity.java
│   │       ├── DatabaseHelper.java
│   │       ├── MedicineDataActivity.java
│   │       ├── ExpiryReceiver.java
│   │
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml
│   │   │   ├── activity_medicine_data.xml
│   │   │
│   │   ├── menu/
│   │   │   └── drawer_menu.xml
│   │   │
│   │   ├── values/
│   │   │   ├── strings.xml
│   │   │   ├── colors.xml
│   │   │   ├── themes.xml
│   │   │
│   │   ├── drawable/
│   │   │   └── (icons, images)
│
│   ├── build.gradle.kts
│
├── build.gradle.kts (Project)
