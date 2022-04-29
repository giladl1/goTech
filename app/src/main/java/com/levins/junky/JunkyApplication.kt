package com.levins.junky

import android.app.Application
import com.levins.junky.repository.PileRepository
//import com.levins.junky.room.PileDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class JunkyApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts

//    val database by lazy { PileDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { PileRepository(/*database.daoAccess()!!,*/applicationContext) }
//    val db by lazy {FirebaseFirestore.getInstance()}
}

