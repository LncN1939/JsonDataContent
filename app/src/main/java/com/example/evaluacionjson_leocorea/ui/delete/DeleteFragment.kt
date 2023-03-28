package com.example.evaluacionjson_leocorea.ui.delete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.evaluacionjson_leocorea.databinding.FragmentDeleteBinding

class DeleteFragment: Fragment() {
    private var _binding: FragmentDeleteBinding? = null
    private val binding get() = _binding!!

    var tvsearch: EditText?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvsearch = binding.tvsearch

        binding.btnId.setOnClickListener(){
            val url="http://192.168.100.15:8080/coordinaccion/delete_data.php"
            val queue= Volley.newRequestQueue(getActivity())
            var resultadoPost = object : StringRequest(Request.Method.POST, url,
                Response.Listener { response ->
                    Toast.makeText(getActivity(),"El usuario se ha borrado de manera exitosa",
                        Toast.LENGTH_LONG).show()
                }, Response.ErrorListener { error ->
                    Toast.makeText(getActivity(),"Error $error", Toast.LENGTH_LONG).show()
                }){
                override fun getParams(): MutableMap<String, String>? {
                    val parametros = HashMap<String,String>()
                    parametros.put("idC",tvsearch?.text.toString())
                    return parametros
                }
            }
            queue.add(resultadoPost)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}