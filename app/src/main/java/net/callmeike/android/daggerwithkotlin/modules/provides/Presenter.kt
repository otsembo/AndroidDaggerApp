/* $Id: $
   Copyright 2017, G. Blake Meike

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package net.callmeike.android.daggerwithkotlin.modules.provides

import dagger.Component
import dagger.Lazy
import dagger.Module
import dagger.Provides
import net.callmeike.android.daggerwithkotlin.MainActivity
import net.callmeike.android.daggerwithkotlin.essentials.Client
import net.callmeike.android.daggerwithkotlin.modules.provides.persist.DAO
import net.callmeike.android.daggerwithkotlin.modules.provides.persist.Transaction
import javax.inject.Inject
import javax.inject.Provider

class Presenter @Inject constructor(
        private val client: Lazy<Client>,
        private val transactionFactory: Provider<Transaction>,
        private val persist: DAO) {

//    // Method injection
//    var client: Lazy<Client>? = null
//        @Inject set

    fun connect(show: (String?) -> Unit) {
        val data = client.get().fetchData()
        show(data)
        persist.store(transactionFactory.get(), data)
    }
}

@Module
object DAOModule {
    @JvmStatic
    @Provides
    fun dao() = DAO.get()

    @JvmStatic
    @Provides
    fun transaction() = Transaction(System.currentTimeMillis())
}

@Component(modules = [DAOModule::class])
interface PresenterFactory {
    fun inject(activity: MainActivity): MainActivity
    fun presenter(): Presenter
}