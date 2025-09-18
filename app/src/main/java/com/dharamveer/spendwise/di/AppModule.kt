package com.dharamveer.spendwise.di

import com.dharamveer.spendwise.domain.repository.ExpenseRepository
import com.dharamveer.spendwise.domain.usecase.AddExpenseUseCase
import com.dharamveer.spendwise.domain.usecase.DeleteExpenseUseCases
import com.dharamveer.spendwise.domain.usecase.ExpenseUseCases
import com.dharamveer.spendwise.domain.usecase.GetAllExpensesUseCase
import com.dharamveer.spendwise.domain.usecase.GetExpensesBetweenUseCase
import com.dharamveer.spendwise.domain.usecase.GetUnsyncedExpensesUseCase
import com.dharamveer.spendwise.domain.usecase.MarkExpensesSyncedUseCase
import com.dharamveer.spendwise.domain.usecase.UpdateExpenseUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideExpenseUseCases(repository: ExpenseRepository): ExpenseUseCases {
        return ExpenseUseCases(
            addExpense = AddExpenseUseCase(repository),
            getAllExpenses = GetAllExpensesUseCase(repository),
            getExpensesBetween = GetExpensesBetweenUseCase(repository),
            updateExpense = UpdateExpenseUseCases(repository),
            deleteExpense = DeleteExpenseUseCases(repository),
            getUnsyncedExpenses = GetUnsyncedExpensesUseCase(repository),
            markExpensesSynced = MarkExpensesSyncedUseCase(repository)
        )
    }
}

