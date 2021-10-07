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
package net.callmeike.android.daggerwithkotlin.modules.multibind

import dagger.Component
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import dagger.multibindings.StringKey
import javax.inject.Inject
import kotlin.reflect.KClass


private const val TAG = "MULTIBIND"


// Lessons 3.3, 3.4, 3.5

data class Decorator(val displayName: String, val fn: (String) -> String)

class SetProcessor @Inject constructor(private val decorators: Set<Decorator>) {
    fun decorate(str: String) = decorators.map { it -> it.fn(str) }
}

class StringMapProcessor @Inject constructor(private val decorators: Map<String, Decorator>) {
    fun decorate(str: String) = decorators.entries.map { it -> "${it.key} -> ${it.value.fn(str)}" }
    fun decorate(key: String, str: String) = decorators[key]?.fn?.invoke(str)
}

class ClassMapProcessor @Inject constructor(private val decorators: Map<Class<*>, Decorator>) {
    fun decorate(str: String) = decorators.entries.map { it -> "${it.key} -> ${it.value.fn(str)}" }
    fun decorate(key: Class<*>, str: String) = decorators[key]?.fn?.invoke(str)
}

class EnumMapProcessor @Inject constructor(private val decorators: Map<DecoratorType, Decorator>) {
    fun decorate(str: String) = decorators.entries.map() { it -> "${it.key} -> ${it.value.fn(str)}" }
    fun decorate(key: DecoratorType, str: String) = decorators[key]?.fn?.invoke(str)
}

class ComplexMapProcessor @Inject constructor(private val decorators: Map<ComplexMapKey, Decorator>) {
    fun decorate(str: String) = decorators.entries.map() { it -> "${it.key} -> ${it.value.fn(str)}" }
    fun decorate(key: ComplexMapKey, str: String) = decorators[key]?.fn?.invoke(str)
}


/////////////////   S E T

@Module
object SetModule {
    @JvmStatic
    @Provides
    @IntoSet
    fun provideOneDecorator() = Decorator("Sparkle", { s -> "*$s*" })

    @JvmStatic
    @Provides
    @ElementsIntoSet
    fun provideSomeDecorators() = setOf<Decorator>(
            Decorator("Emphatic", { s -> "$s!!" }),
            Decorator("Yell", { s -> ":-O $s" }))
}


/////////////////   S T R I N G   M A P

@Module
object StringMapModule {
    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("sparkle")
    fun provideDecorator1() = Decorator("Sparkle", { s -> "*$s*" })

    @JvmStatic
    @Provides
    @IntoMap
    @StringKey("emphatic")
    fun provideDecorator2() = Decorator("Emphatic", { s -> "$s!!" })
}


/////////////////   C L A S S   M A P

@Module
object ClassMapModule {
    @JvmStatic
    @Provides
    @IntoMap
    @ClassKey(Int::class)
    fun provideDecorator1() = Decorator("Sparkle", { s -> "*$s*" })

    @JvmStatic
    @Provides
    @IntoMap
    @ClassKey(Long::class)
    fun provideDecorator2() = Decorator("Emphatic", { s -> "$s!!" })
}


/////////////////   E N U M   M A P

enum class DecoratorType { SPARKLE, EMPHATIC, YELL }

@MapKey
annotation class MapKey0(val value: DecoratorType)

@Module
object EnumMapModule {
    @JvmStatic
    @Provides
    @IntoMap
    @MapKey0(DecoratorType.SPARKLE)
    fun provideDecorator1() = Decorator("Sparkle", { s -> "*$s*" })

    @JvmStatic
    @Provides
    @IntoMap
    @MapKey0(DecoratorType.EMPHATIC)
    fun provideDecorator2() = Decorator("Emphatic", { s -> "$s!!" })

    @JvmStatic
    @Provides
    @IntoMap
    @MapKey0(DecoratorType.YELL)
    fun provideDecorator3() = Decorator("Yell", { s -> ":-O $s" })
}


/////////////////  M U L T I - M E M B E R   K E Y   M A P

// Requires dependency on auto-value-annotations and auto-value
@MapKey(unwrapValue = false)
annotation class ComplexMapKey(
        val klass: KClass<*> = Object::class,
        val name: String,
        val type: DecoratorType,
        val id: Int,
        val weight: Long)

@Module
object ComplexMapModule {
    @JvmStatic
    @Provides
    @IntoMap
    @ComplexMapKey(name = "sparkle", type = DecoratorType.SPARKLE, id = 0, weight = 100L)
    fun provideDecorator1() = Decorator("Sparkle", { s -> "*$s*" })

    @JvmStatic
    @Provides
    @IntoMap
    @ComplexMapKey(name = "emphatic", type = DecoratorType.EMPHATIC, id = 1, weight = 150L)
    fun provideDecorator2() = Decorator("Emphatic", { s -> "$s!!" })

    @JvmStatic
    @Provides
    @IntoMap
    @ComplexMapKey(name = "yell", type = DecoratorType.YELL, id = 2, weight = 200L)
    fun provideDecorator3() = Decorator("Yell", { s -> ":-O $s" })
}


@Component(modules = [SetModule::class, StringMapModule::class, ClassMapModule::class, EnumMapModule::class, ComplexMapModule::class])
interface MultibindingFactory {
    fun setProcessor(): SetProcessor
    fun stringProcessor(): StringMapProcessor
    fun classProcessor(): ClassMapProcessor
    fun enumProcessor(): EnumMapProcessor
    fun complexProcessor(): ComplexMapProcessor
}


object Multibinding {
    fun test(str: String): String {
        val factory = DaggerMultibindingFactory.create()

        val out = mutableListOf<String>()

        out.addAll(factory.setProcessor().decorate(str))

        out.addAll(factory.stringProcessor().decorate(str))

        out.addAll(factory.classProcessor().decorate(str))


        out.addAll(factory.enumProcessor().decorate(str))

        out.addAll(factory.complexProcessor().decorate(str))

        return out.joinToString("\n")
    }
}