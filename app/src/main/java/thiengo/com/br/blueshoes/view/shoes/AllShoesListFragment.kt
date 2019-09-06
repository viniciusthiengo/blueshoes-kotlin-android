package thiengo.com.br.blueshoes.view.shoes


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_all_shoes_list.*
import kotlinx.android.synthetic.main.fragment_config_list.*
import thiengo.com.br.blueshoes.R
import thiengo.com.br.blueshoes.data.AllShoesDataBase
import thiengo.com.br.blueshoes.domain.Shoes
import thiengo.com.br.blueshoes.view.MainActivity


class AllShoesListFragment : Fragment() {

    companion object{
        const val GRID_COLUMNS = 2
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

        return inflater
            .inflate(
                R.layout.fragment_all_shoes_list,
                container,
                false
            )
    }

    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        initItems()
    }

    private fun initItems(){
        rv_shoes.setHasFixedSize( false )

        val layoutManager = GridLayoutManager(
            activity,
            GRID_COLUMNS,
            RecyclerView.VERTICAL,
            false
        )
        rv_shoes.layoutManager = layoutManager

        val adapter = AllShoesListAdapter( AllShoesDataBase.getItems() )
        rv_shoes.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity)
            .updateToolbarTitleInFragment( R.string.all_shoes_list_frag_title )
    }
}
