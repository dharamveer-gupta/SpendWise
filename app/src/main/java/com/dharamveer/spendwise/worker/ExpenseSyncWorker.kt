package com.dharamveer.spendwise.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dharamveer.spendwise.domain.usecase.ExpenseUseCases
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

/**
 * Mock sync worker: fetches unsynced expenses, "uploads" them, marks them as synced.
 */
@HiltWorker
class ExpenseSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val useCases: ExpenseUseCases
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        private const val TAG = "ExpenseSyncWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            Log.d(TAG, "Starting expense sync work")

            val unsynced = useCases.getUnsyncedExpenses()
            Log.d(TAG, "Found ${unsynced.size} unsynced expenses")

            if (unsynced.isNotEmpty()) {
                // Simulate network operation
                delay(2000)

                val ids = unsynced.map { it.id }
                useCases.markExpensesSynced(ids)

                Log.d(TAG, "Successfully synced ${ids.size} expenses")
            } else {
                Log.d(TAG, "No expenses to sync")
            }

            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Worker failed with error: ${e.message}", e)

            // Retry on failure with exponential backoff
            if (runAttemptCount < 3) {
                Log.d(TAG, "Retrying work (attempt ${runAttemptCount + 1}/3)")
                Result.retry()
            } else {
                Log.e(TAG, "Max retry attempts reached, marking as failure")
                Result.failure()
            }
        }
    }
}

