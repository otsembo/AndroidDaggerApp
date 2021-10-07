/* $Id: $
   Copyright 2018, G. Blake Meike

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
package net.callmeike.android.daggerwithkotlin.modules.named

import dagger.Component
import dagger.Module
import dagger.Provides
import net.callmeike.android.daggerwithkotlin.BuildConfig
import net.callmeike.android.daggerwithkotlin.extra.DEBUG_ENDPOINT
import net.callmeike.android.daggerwithkotlin.extra.PROD_ENDPOINT
import javax.inject.Inject
import javax.inject.Named


class Client @Inject constructor(private val connection: Connection) {
    fun fetchData(): String = connection.doReq()
}

class Connection @Inject constructor(/* @Named("endpoint") */ val endpoint: String) {
    // just return the endpoint url
    fun doReq() = endpoint
}

@Component(modules = [ConnectionModule::class])
interface PresenterFactory {
    fun client(): Client
}

@Module
object ConnectionModule {
    @JvmStatic
    @Provides
//    @Named("endpoint")
    fun providesEndpoint() = if (BuildConfig.DEBUG) {
        DEBUG_ENDPOINT
    } else {
        PROD_ENDPOINT
    }
}
