package com.dharamveer.spendwise.domain.usecase

import com.dharamveer.spendwise.domain.repository.ExpenseRepository
import javax.inject.Inject

class MarkExpensesSyncedUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(ids: List<Int>) = repository.markExpensesSynced(ids)
}