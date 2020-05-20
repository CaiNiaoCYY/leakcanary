package leakcanary.internal

import android.app.Activity
import android.app.Application
import android.util.Log
import leakcanary.AppWatcher
import leakcanary.ObjectWatcher

/**
 *
 * @ClassName:      LiveActivityDestroyWatcher
 * @Description:    只检查live相关的类
 * @CreateDate:     2020/5/20 14:14
 * @Version:        1.0
 */
internal class LiveActivityDestroyWatcher private constructor(
    private val objectWatcher: ObjectWatcher,
    private val configProvider: () -> AppWatcher.Config
) {

    private val lifecycleCallbacks =
        object : Application.ActivityLifecycleCallbacks by InternalAppWatcher.noOpDelegate() {
            override fun onActivityDestroyed(activity: Activity) {
                if (configProvider().watchActivities) {
                    Log.d("LiveLeakCanary", "onActivityDestroyed activity.localClassName = ${activity::class.java.name}")
                    if (activity::class.java.name.contains("live")) {
                        objectWatcher.watch(
                            activity, "${activity::class.java.name} received Activity#onDestroy() callback"
                        )
                    }
                }
            }
        }

    companion object {
        fun install(
            application: Application,
            objectWatcher: ObjectWatcher,
            configProvider: () -> AppWatcher.Config
        ) {
            val activityDestroyWatcher =
                LiveActivityDestroyWatcher(objectWatcher, configProvider)
            application.registerActivityLifecycleCallbacks(activityDestroyWatcher.lifecycleCallbacks)
        }
    }
}