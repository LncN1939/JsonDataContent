package com.example.evaluacionjson_leocorea.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.evaluacionjson_leocorea.R
import com.example.evaluacionjson_leocorea.databinding.FragmentDashboardBinding
import com.example.evaluacionjson_leocorea.databinding.UserItemBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    var userList = arrayListOf<User>()
    private val apiLink = "http://192.168.100.15:8080/coordinaccion/read_data.php"
    private val apiLink60 = "http://192.168.100.15:8080/coordinaccion/read_60.php"
    var recycleView: RecyclerView? = null
    var btn60: Button? = null
    var btnAll: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView = binding.recyclerview
        btn60 = binding.btn60
        btnAll = binding.btnAll

     /*   val reqQueue: RequestQueue = Volley.newRequestQueue(getActivity())
        val request = JsonObjectRequest(Request.Method.GET,apiLink,null, { res ->
            val jsonArray = res.getJSONArray("data")
            for (i in 0 until jsonArray.length()){
                var jsonObj = jsonArray.getJSONObject(i)
                val user = User(
                    jsonObj.getString("idC"),
                    jsonObj.getString("nombres"),
                    jsonObj.getString("apellidos"),
                    jsonObj.getString("fechaNac"),
                    jsonObj.getString("titulo"),
                    jsonObj.getString("email"),
                    jsonObj.getString("facultad")
                )
                userList.add(user)
            }
            Log.d("PedroVaAsuCasa", userList.toString())
            println(userList.toString())

            recycleView?.layoutManager = LinearLayoutManager(getActivity())
            recycleView?.adapter = UserAdapter(userList)

        },{err ->
            Log.d("Volley fail", err.message.toString())
        })
        reqQueue.add(request)
*/
        btn60!!.setOnClickListener {
            userList.clear()
            filter60()
        }

        btnAll!!.setOnClickListener {
            userList.clear()
            filterAll()
        }

    }

    private fun filter60(){
        recycleView = binding.recyclerview

        val reqQueue: RequestQueue = Volley.newRequestQueue(activity)
        val request = JsonObjectRequest(Request.Method.GET,apiLink60,null, { res ->
            val jsonArray = res.getJSONArray("data")
            for (i in 0 until jsonArray.length()){
                var jsonObj = jsonArray.getJSONObject(i)
                val user = User(
                    jsonObj.getString("idC"),
                    jsonObj.getString("nombres"),
                    jsonObj.getString("apellidos"),
                    jsonObj.getString("fechaNac"),
                    jsonObj.getString("titulo"),
                    jsonObj.getString("email"),
                    jsonObj.getString("facultad")
                )
                userList.add(user)
            }
            recycleView?.layoutManager = LinearLayoutManager(activity)
            recycleView?.adapter = UserAdapter(userList)

        },{err ->
            Log.d("Volley fail", err.message.toString())
        })
        reqQueue.add(request)
    }

    private fun filterAll(){
        val reqQueue: RequestQueue = Volley.newRequestQueue(activity)
        val request = JsonObjectRequest(Request.Method.GET,apiLink,null, { res ->
            val jsonArray = res.getJSONArray("data")
            for (i in 0 until jsonArray.length()){
                var jsonObj = jsonArray.getJSONObject(i)
                val user = User(
                    jsonObj.getString("idC"),
                    jsonObj.getString("nombres"),
                    jsonObj.getString("apellidos"),
                    jsonObj.getString("fechaNac"),
                    jsonObj.getString("titulo"),
                    jsonObj.getString("email"),
                    jsonObj.getString("facultad")
                )
                userList.add(user)
            }
            Log.d("PedroVaAsuCasa", userList.toString())
            println(userList.toString())

            recycleView?.layoutManager = LinearLayoutManager(getActivity())
            recycleView?.adapter = UserAdapter(userList)

        },{err ->
            Log.d("Volley fail", err.message.toString())
        })
        reqQueue.add(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}