# AndroidXFragmentDestroyWatcher is loaded via reflection
-keep class leakcanary.internal.AndroidXFragmentDestroyWatcher { *; }
-keep class leakcanary.internal.LiveAndroidXFragmentDestroyWatcher { *; }
# ViewModelClearedWatcher reaches into ViewModelStore using reflection.
-keep class androidx.lifecycle.ViewModelStore { *; }
