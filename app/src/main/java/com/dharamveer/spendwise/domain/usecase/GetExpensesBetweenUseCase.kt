package com.dharamveer.spendwise.domain.usecase

import com.dharamveer.spendwise.domain.repository.ExpenseRepository
import javax.inject.Inject

class GetExpensesBetweenUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    operator fun invoke(startTs: Long, endTs: Long) = repository.getExpensesBetween(startTs, endTs)
}