package com.locus.weatherapp.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.locus.weatherapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.main_fragment, container,false)
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val lookupBtn = mView.findViewById<Button>(R.id.lookup_btn)
        val cityET = mView.findViewById<EditText>(R.id.city_nm)
        lookupBtn.setOnClickListener {
            if (TextUtils.isEmpty(cityET.text.toString())) {
                cityET.error = resources.getString(R.string.empty_city_error)
            } else {
                val data = Bundle()
                data.putString("CityName", cityET.text.toString())
                (activity as MainActivity).replaceFragments(CityWeatherFragment::class.java, data)
            }
        }
    }
}