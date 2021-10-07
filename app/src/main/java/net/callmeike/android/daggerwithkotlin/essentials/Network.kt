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
package net.callmeike.android.daggerwithkotlin.essentials

import net.callmeike.android.daggerwithkotlin.BuildConfig
import net.callmeike.android.daggerwithkotlin.extra.DEBUG_ENDPOINT
import net.callmeike.android.daggerwithkotlin.extra.PROD_ENDPOINT
import javax.inject.Inject
import javax.inject.Provider


class Client @Inject constructor(private val connectionFactory: Provider<Connection>) {
    fun fetchData(): String = connectionFactory.get().doReq()
}

class Connection @Inject constructor() {
    private val endpoint = if (BuildConfig.DEBUG) {
        DEBUG_ENDPOINT
    } else {
        PROD_ENDPOINT
    }

    // just return the endpoint url
    fun doReq() = endpoint
}
