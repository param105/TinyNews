package com.param.tinynewsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.param.tinynewsapp.adapters.NewsAdapter
import com.param.tinynewsapp.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import kotlinx.android.synthetic.main.item_list.*
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.Retrofit
import org.json.JSONException
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import org.json.JSONObject
import com.param.tinynewsapp.model.NewsModelRecycler
import com.param.tinynewsapp.util.RecyclerInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    private var recyclerView: RecyclerView? = null
    private var newsAdapter: NewsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)
        recyclerView = findViewById(R.id.item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        //setupRecyclerView(item_list)
        fetchJSON()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, twoPane)
    }


    /**
     * Function to fetch JSON
     * Used Pcasso use fetch photo from json
     */
    private fun fetchJSON() {

        val retrofit = Retrofit.Builder()
            .baseUrl(RecyclerInterface.JSONURL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val api = retrofit.create(RecyclerInterface::class.java!!)

        val call = api.string

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.i("Responsestring", response.body().toString())
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString())

                        val jsonresponse = response.body().toString()
                        writeRecycler(jsonresponse)

                    } else {
                        Log.i(
                            "onEmptyResponse",
                            "Returned empty response"
                        )//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {

            }
        })
    }

    private fun writeRecycler(response: String) {

        try {
            //getting the whole json object from the response
            val obj = JSONObject(response)
            if (obj.optString("status") == "true") {

                val modelRecyclerArrayList = ArrayList<NewsModelRecycler.NewsModel>()
                val dataArray = obj.getJSONArray("data")

                for (i in 0 until dataArray.length()) {

                    val modelRecycler = NewsModelRecycler.NewsModel(null,null,null,null)
                    val dataobj = dataArray.getJSONObject(i)

                    modelRecycler.imgURL = dataobj.getString("imgURL")
                    modelRecycler.name = dataobj.getString("name")
                    modelRecycler.country = dataobj.getString("country")
                    modelRecycler.city = dataobj.getString("city")

                    modelRecyclerArrayList.add(modelRecycler)
                    NewsModelRecycler.addItem(modelRecycler)

                }

                newsAdapter = NewsAdapter(this,this,false, modelRecyclerArrayList)
                recyclerView?.setAdapter(newsAdapter)
                recyclerView?.setLayoutManager(
                    LinearLayoutManager(
                        applicationContext,
                        RecyclerView.VERTICAL,
                        false
                    )
                )

            } else {
                Toast.makeText(this@ItemListActivity, obj.optString("message") + "", Toast.LENGTH_SHORT).show()
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: ItemListActivity,
        private val values: List<DummyContent.DummyItem>,
        private val twoPane: Boolean
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as DummyContent.DummyItem

                if (twoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(ItemDetailFragment.ARG_ITEM_ID, item.id)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                        putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.idView.text = item.id
            holder.contentView.text = item.content

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.id_text
            val contentView: TextView = view.content
        }
    }
}
