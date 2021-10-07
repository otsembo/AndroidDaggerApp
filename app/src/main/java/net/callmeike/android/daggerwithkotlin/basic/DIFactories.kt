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
package net.callmeike.android.daggerwithkotlin.basic

import net.callmeike.android.daggerwithkotlin.BuildConfig
import net.callmeike.android.daggerwithkotlin.extra.ConnectionImpl
import net.callmeike.android.daggerwithkotlin.extra.DEBUG_ENDPOINT
import net.callmeike.android.daggerwithkotlin.extra.PROD_ENDPOINT


class MainPresenterFactory {
    private val clientFactory = NetworkClientFactory()
    fun get() = MainPresenter(clientFactory.get())
}

class NetworkClientFactory {
    private val connectionFactory = NetworkConnectionFactory()
    fun get() = DINetworkClient(connectionFactory.get())
}

class NetworkConnectionFactory {
    private val endPointFactory = EndPointFactory()
    fun get() = ConnectionImpl(endPointFactory.get())
}

class EndPointFactory {
    fun get() = if (BuildConfig.DEBUG) {
        DEBUG_ENDPOINT
    } else {
        PROD_ENDPOINT
    }
}