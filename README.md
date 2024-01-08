# ApplicationsView
ApplicationsView show promo applications of ATT Solution team

Gradle
------------


###### Project Gradle
```groovy
allprojects {
	repositories {
		...
		mavenCentral()
		maven { url 'https://jitpack.io' }
	}
}
```

###### Module Gradle
```groovy
dependencies {
	implementation 'com.github.phihung1992:attpromouis:1.31'
}
```

Usage
--------

###### Declare view to layout xml file

```groovy
<com.attlib.attpromouis.views.ApplicationsView
        android:id="@+id/av_applications"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@id/ln_watch_video"
        android:orientation="vertical" />
```

###### Load data for ApplicationsView in java code

```xml
ApplicationsView applicationsView = findViewById(R.id.av_applications);
        applicationsView.setRewardCoins(300);
        applicationsView.loadData();
```

setRewardCoins(): set value is displayed in description
