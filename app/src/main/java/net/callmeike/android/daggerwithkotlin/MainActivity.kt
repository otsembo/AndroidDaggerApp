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
package net.callmeike.android.daggerwithkotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import net.callmeike.android.daggerwithkotlin.essentials.Presenter
import net.callmeike.android.daggerwithkotlin.essentials.DaggerPresenterFactory
import net.callmeike.android.daggerwithkotlin.modules.multibind.Multibinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
//    // Basic injection:
//    private lateinit var presenter: MainPresenter
//
//    fun setPresenter(presenter: MainPresenter) {
//        this.presenter = presenter
//    }

//    // Factory injection:
//    private var presenter = MainPresenterFactory().get()

//    // Dagger factory injection
//    private var presenter = DaggerPresenterFactory.create().presenter()

    // Field Injection
    @Inject
    lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerPresenterFactory.create().inject(this)

        setContentView(R.layout.activity_main)

        connect.setOnClickListener {
            presenter.connect { t -> textView.text = t }
        }
    }

    final override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    final override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.decorate -> textView.text = Multibinding.test(input.text.toString())
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

}
