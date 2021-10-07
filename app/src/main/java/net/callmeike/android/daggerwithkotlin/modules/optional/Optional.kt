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
package net.callmeike.android.daggerwithkotlin.modules.optional

import android.util.Log
import dagger.Binds
import dagger.BindsOptionalOf
import dagger.Component
import dagger.Module
import dagger.Provides
import net.callmeike.android.daggerwithkotlin.extra.Prefix
import java.util.Optional
import javax.inject.Inject


private const val TAG = "OPT"


////    P R E F I X

@Module
interface OptionalPrefixModule {
    @BindsOptionalOf
    fun optionalPrefix(): Prefix
}


//// L O G G E R

class Logger @Inject constructor(private val prefix: Optional<Prefix>) {
    fun log() {
        val msg = if (!prefix.isPresent) { "default" } else { prefix.get().prefix() }
        Log.d(TAG, "message: ${msg}")
    }
}


//// P R E F I X L E S S

@Module(includes = [OptionalPrefixModule::class])
object DefaultPrefixModule

@Component(modules = [DefaultPrefixModule::class])
interface DefaultPrefixFactory {
    fun data(): Logger
}


//// C U S T O M   P R E F I X

class CustomPrefix(private val prefix: String): Prefix {
    override fun prefix() = prefix
}

@Module
interface PrefixImplModule {
    @Binds
    fun providesPrefix(prefix: CustomPrefix): Prefix
}

@Module(includes = [OptionalPrefixModule::class, PrefixImplModule::class])
object CustomPrefixModule {
    @JvmStatic
    @Provides
    fun providesPrefixImpl() = CustomPrefix("ootori sama")
}

@Component(modules = [CustomPrefixModule::class])
interface CustomPrefixFactory {
    fun data(): Logger
}


//// T E S T   D R I V E R

object Optional {
    fun test() {
        DaggerDefaultPrefixFactory.create().data().log()
        DaggerCustomPrefixFactory.create().data().log()
    }
}