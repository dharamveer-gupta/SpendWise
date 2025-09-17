package com.dharamveer.spendwise.di

import android.content.Context
import androidx.room.Room
import com.dharamveer.spendwise.data.local.dao.ExpenseDao
import com.dharamveer.spendwise.data.local.database.ExpenseDatabase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.Provides
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ExpenseDatabase {
        return Room.databaseBuilder(
            appContext,
            ExpenseDatabase::class.java,
            "spendwise_db"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    fun provideExpenseDao(db: ExpenseDatabase): ExpenseDao = db.expenseDao()
}
