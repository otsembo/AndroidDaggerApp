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
package net.callmeike.android.daggerwithkotlin.modules.named

import android.util.Log
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Named


// Lessons 3.6

private const val TAG = "NAMED"


class NamedObject constructor(val name: String)

@Module
object NamedModule {
    @JvmStatic
    @Provides
    @Named("fname")
    fun provideFirstName() = "tom"

    @JvmStatic
    @Provides
    @Named("lname")
    fun provideLastName() = "swift"

    @JvmStatic
    @Provides
    fun provideNamedObject(@Named("fname") fname: String, @Named("lname") lname: String) = NamedObject("${fname} ${lname}")
}


@Component(modules = [NamedModule::class])
interface NamedFactory {
    @Named("fname")
    fun firstName(): String

    @Named("lname")
    fun lastName(): String

    fun namedObject(): NamedObject
}

object Named {
    fun test() {
        val factory = DaggerNamedFactory.create()
        Log.d(TAG, "${factory.firstName()}")
        Log.d(TAG, "${factory.lastName()}")
        Log.d(TAG, "${factory.namedObject()}")
    }
}
