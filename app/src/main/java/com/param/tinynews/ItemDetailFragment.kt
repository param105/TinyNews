package com.param.tinynews

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.param.tinynews.model.NewsModelRecycler
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail.view.*

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: NewsModelRecycler.NewsDataModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.

                item = NewsModelRecycler.ITEM_MAP[it.get(ARG_ITEM_ID)]
                activity?.toolbar_layout?.title = item?.title
               Picasso.get().load(item?.imgURL).into(activity?.toolbar_imageview)
            }
        }
    }

    /***
     * creates view at initial setup phase
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        // Show the dummy content as text in a TextView.
        item?.let {
            rootView.item_detail.text = it.content

        }

        var moreButton = rootView.findViewById<Button>(R.id.more_button)
        moreButton.setOnClickListener(moreButtonClickListener)
        return rootView
    }

    /**
     * click listener
     */
    val moreButtonClickListener = View.OnClickListener { view ->
        when(view?.id){
            R.id.more_button -> showBrowserActivity()

        }
    }

    private fun showBrowserActivity() {
        Toast.makeText(this.activity?.applicationContext,"more button clicked", Toast.LENGTH_LONG).show()
        val url:String? = item?.siteURL
        val builder:CustomTabsIntent.Builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(resources.getColor(R.color.colorPrimary))
        var customTabsIntent:CustomTabsIntent = builder?.build()
        customTabsIntent.launchUrl(this.context, Uri.parse(url))



    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
