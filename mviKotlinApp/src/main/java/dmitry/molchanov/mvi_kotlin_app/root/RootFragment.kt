package dmitry.molchanov.mvi_kotlin_app.root

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dmitry.molchanov.mvi_kotlin_app.OnBackPressedHandler
import dmitry.molchanov.mvi_kotlin_app.R
import dmitry.molchanov.mvi_kotlin_app.details.DetailsFragment
import dmitry.molchanov.mvi_kotlin_app.main.MainFragment

class RootFragment : Fragment(R.layout.content), OnBackPressedHandler {

    private val fragmentFactory = FragmentFactoryImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = fragmentFactory

        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            childFragmentManager
                .beginTransaction()
                .add(R.id.content, fragmentFactory.mainFragment(), TAG_MAIN)
                .commit()
        }
    }

    override fun onBackPressed(): Boolean =
        if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack()
            true
        } else {
            false
        }

    private fun openDetails(itemId: Long) {
        childFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_fade_in_bottom, R.anim.scale_fade_out, R.anim.scale_fade_in, R.anim.slide_fade_out_bottom)
            .replace(R.id.content, fragmentFactory.detailsFragment().setArguments(itemId = itemId), TAG_DETAILS)
            .addToBackStack(null)
            .commit()
    }

    private fun findMainFragment(): MainFragment? =
        childFragmentManager.findFragmentByTag(TAG_MAIN) as MainFragment?

    private companion object {
        private const val TAG_MAIN = "MAIN"
        private const val TAG_DETAILS = "DETAILS"
    }

    private inner class FragmentFactoryImpl : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
            when (loadFragmentClass(classLoader, className)) {
                MainFragment::class.java -> mainFragment()
                DetailsFragment::class.java -> detailsFragment()
                else -> super.instantiate(classLoader, className)
            }

        fun mainFragment(): MainFragment = MainFragment(onItemSelected = ::openDetails)

        fun detailsFragment(): DetailsFragment =
            DetailsFragment(
                onItemChanged = { item -> findMainFragment()?.onItemChanged(
                    id = item.id,
                    text = item.text,
                    isDone = item.isDone
                ) },
                onItemDeleted = { id ->
                    findMainFragment()?.onItemDeleted(id = id)
                    childFragmentManager.popBackStack()
                },
            )
    }
}
