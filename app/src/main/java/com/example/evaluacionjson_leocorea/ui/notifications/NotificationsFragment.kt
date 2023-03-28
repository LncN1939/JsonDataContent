package com.example.evaluacionjson_leocorea.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.evaluacionjson_leocorea.databinding.FragmentDashboardBinding
import com.example.evaluacionjson_leocorea.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    var txtNombres: EditText?=null
    var txtApellidos: EditText?=null
    var txtFechaNac: EditText?=null
    var txtTitulo: EditText?=null
    var txtEmail: EditText?=null
    var txtFacultad: EditText?=null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtNombres = binding.txtNombres
        txtApellidos = binding.txtApellidos
        txtFechaNac = binding.txtFechaNac
        txtTitulo = binding.txtTitulo
        txtEmail = binding.txtEmail
        txtFacultad = binding.txtFacultad

        binding.btnSubmit.setOnClickListener() {
            val url = "http://192.168.100.15:8080/coordinaccion/add_data.php"
            val queue = Volley.newRequestQueue(getActivity())
            var resultadoPost = object : StringRequest(Request.Method.POST, url,
                Response.Listener<String> { response ->
                    Toast.makeText(
                        getActivity(),
                        "Usuario ha sido insertado existosamente",
                        Toast.LENGTH_LONG
                    ).show()
                }, Response.ErrorListener { error ->
                    Log.d("ERROR","$error")
                    Toast.makeText(getActivity(), "Error: $error", Toast.LENGTH_LONG).show()
                    Log.d("ERROR","$error")
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val parametros = HashMap<String, String>()
                    parametros.put("nombres", txtNombres?.text.toString())
                    parametros.put("apellidos", txtApellidos?.text.toString())
                    parametros.put("fechaNac", txtFechaNac?.text.toString())
                    parametros.put("titulo", txtTitulo?.text.toString())
                    parametros.put("email", txtEmail?.text.toString())
                    parametros.put("facultad", txtFacultad?.text.toString())
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