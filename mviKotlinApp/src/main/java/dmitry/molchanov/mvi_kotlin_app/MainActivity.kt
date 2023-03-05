package dmitry.molchanov.mvi_kotlin_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dmitry.molchanov.mvi_kotlin_app.domain.DefaultDispatchers
import dmitry.molchanov.mvi_kotlin_app.root.RootFragment

class MainActivity : AppCompatActivity() {

    private val fragmentFactory = FragmentFactoryImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory

        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, fragmentFactory.rootFragment())
                .commit()
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if ((it as? OnBackPressedHandler)?.onBackPressed() == true) {
                return
            }
        }

        super.onBackPressed()
    }

    private inner class FragmentFactoryImpl : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
            when (loadFragmentClass(classLoader, className)) {
                RootFragment::class.java -> rootFragment()
                else -> super.instantiate(classLoader, className)
            }

        fun rootFragment(): RootFragment =
            RootFragment(
                storeFactory = storeFactoryInstance,
                dispatchers = DefaultDispatchers,
            )
    }
}
