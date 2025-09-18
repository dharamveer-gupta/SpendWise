package com.dharamveer.spendwise.worker

import android.content.Context
import android.util.Log
import androidx.lifecycle.asFlow
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

/**
 * Utility object to schedule or trigger sync workers.
 */
object SyncScheduler {

    private const val TAG = "SyncScheduler"
    private const val PERIODIC_SYNC_WORK_NAME = "ExpensePeriodicSync"
    private const val ONE_TIME_SYNC_WORK_NAME = "ExpenseOneTimeSync"

    /**
     * Schedules periodic expense sync work that runs every 15 minutes
     * when device is connected to network and not in battery saver mode.
     */
    fun scheduleExpenseSync(context: Context) {
        try {
            Log.d(TAG, "=== SCHEDULING PERIODIC WORK ===")
            Log.d(TAG, "Called from: ${Thread.currentThread().stackTrace[3].className}")
            Log.d(TAG, "Context: ${context.javaClass.simpleName}")

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()

            val request = PeriodicWorkRequestBuilder<ExpenseSyncWorker>(
                15, TimeUnit.MINUTES
            )
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    30, TimeUnit.SECONDS
                )
                .addTag("expense_sync")
                .addTag("scheduled_by_${context.javaClass.simpleName}")
                .build()

            Log.d(TAG, "Work Request ID: ${request.id}")
            Log.d(TAG, "Work Tags: ${request.tags}")

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                PERIODIC_SYNC_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )

            Log.d(TAG, "Periodic expense sync scheduled successfully")
            Log.d(TAG, "=== END SCHEDULING ===")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to schedule periodic expense sync", e)
        }
    }

    /**
     * Triggers immediate one-time sync work.
     */
    fun triggerOneTimeSync(context: Context) {
        try {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = OneTimeWorkRequestBuilder<ExpenseSyncWorker>()
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    10, TimeUnit.SECONDS
                )
                .addTag("expense_sync_immediate")
                .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                ONE_TIME_SYNC_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                request
            )

            Log.d(TAG, "One-time expense sync triggered successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to trigger one-time expense sync", e)
        }
    }

    /**
     * Cancels all scheduled sync work.
     */
    fun cancelAllSyncWork(context: Context) {
        try {
            val workManager = WorkManager.getInstance(context)
            workManager.cancelUniqueWork(PERIODIC_SYNC_WORK_NAME)
            workManager.cancelUniqueWork(ONE_TIME_SYNC_WORK_NAME)
            workManager.cancelAllWorkByTag("expense_sync")
            workManager.cancelAllWorkByTag("expense_sync_immediate")

            Log.d(TAG, "All sync work cancelled successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to cancel sync work", e)
        }
    }

    /**
     * Observes the status of periodic sync work.
     */
    fun observePeriodicSyncStatus(context: Context): Flow<WorkInfo.State?> {
        return WorkManager.getInstance(context)
            .getWorkInfosForUniqueWorkLiveData(PERIODIC_SYNC_WORK_NAME)
            .asFlow()
            .map { workInfos -> workInfos.firstOrNull()?.state }
    }

    /**
     * Observes the status of one-time sync work.
     */
    fun observeOneTimeSyncStatus(context: Context): Flow<WorkInfo.State?> {
        return WorkManager.getInstance(context)
            .getWorkInfosForUniqueWorkLiveData(ONE_TIME_SYNC_WORK_NAME)
            .asFlow()
            .map { workInfos -> workInfos.firstOrNull()?.state }
    }

    /**
     * Logs current work status for debugging.
     */
    fun logWorkStatus(context: Context) {
        try {
            val workManager = WorkManager.getInstance(context)

            // Check periodic work status
            val periodicWork = workManager.getWorkInfosForUniqueWork(PERIODIC_SYNC_WORK_NAME).get()
            Log.d(TAG, "Periodic sync status: ${periodicWork.map { "${it.id}: ${it.state}" }}")

            // Check one-time work status  
            val oneTimeWork = workManager.getWorkInfosForUniqueWork(ONE_TIME_SYNC_WORK_NAME).get()
            Log.d(TAG, "One-time sync status: ${oneTimeWork.map { "${it.id}: ${it.state}" }}")

        } catch (e: Exception) {
            Log.e(TAG, "Failed to get work status", e)
        }
    }
}
