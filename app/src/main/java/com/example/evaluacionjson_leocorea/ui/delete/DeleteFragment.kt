package com.example.evaluacionjson_leocorea.ui.delete

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.evaluacionjson_leocorea.databinding.FragmentDeleteBinding

class DeleteFragment: Fragment() {
    private var _binding: FragmentDeleteBinding? = null
    private val binding get() = _binding!!
    private lateinit var builder : AlertDialog.Builder
    var txtIdc: EditText?=null
    var txtNombres: EditText?=null
    var txtApellidos: EditText?=null
    var txtFechaNac: EditText?=null
    var txtTitulo: EditText?=null
    var txtEmail: EditText?=null
    var txtFacultad: EditText?=null

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
        txtIdc = binding.txtIdc
        txtNombres = binding.txtNombres
        txtApellidos = binding.txtApellidos
        txtFechaNac = binding.txtFechaNac
        txtTitulo = binding.txtTitulo
        txtEmail = binding.txtEmail
        txtFacultad = binding.txtFacultad
        tvsearch = binding.txtIdc
        builder = AlertDialog.Builder(activity!!)

        binding.txtIdc.doAfterTextChanged {
            selectDataById()

        }

        binding.btnId.setOnClickListener(){
            deleteData()
        }
    }

    private fun deleteData(){
        if (validateForm()) {
            val url = "http://192.168.100.15:8080/coordinaccion/delete_data.php"
            val queue = Volley.newRequestQueue(getActivity())
            var resultadoPost = object : StringRequest(Request.Method.POST, url,
                Response.Listener { response ->
                    Toast.makeText(
                        getActivity(), "El usuario se ha borrado de manera exitosa",
                        Toast.LENGTH_LONG
                    ).show()
                }, Response.ErrorListener { error ->
                    Toast.makeText(getActivity(), "Error $error", Toast.LENGTH_LONG).show()
                }) {
                override fun getParams(): MutableMap<String, String>? {
                    val parametros = HashMap<String, String>()
                    parametros.put("idC", tvsearch?.text.toString())
                    return parametros
                }
            }
            queue.add(resultadoPost)
            builder.setTitle("Registro Borrado")
                .setPositiveButton("Ok"){dialogInterface, it ->
                    dialogInterface.cancel()
                }.show()
            resetText()
        }
    }

    private fun selectDataById(){
        Log.d("WARNING", "${txtIdc!!.getText()}")
        Toast.makeText(getActivity(), txtIdc!!.getText(), Toast.LENGTH_SHORT).show()
        val reqQueue1: RequestQueue = Volley.newRequestQueue(getActivity())
        val apiLink = "http://192.168.100.15:8080/coordinaccion/select_data.php?idC=${txtIdc!!.getText()}"

        val request = JsonObjectRequest(Request.Method.GET, apiLink, null, { res ->
            txtNombres?.setText(res.getString("nombres"))
            txtApellidos?.setText(res.getString("apellidos"))
            txtFechaNac?.setText(res.getString("fechaNac"))
            txtTitulo?.setText(res.getString("titulo"))
            txtEmail?.setText(res.getString("email"))
            txtFacultad?.setText(res.getString("facultad"))
        }, { err ->
            resetText()
        })
        reqQueue1.add(request)
    }

    private fun validateForm(): Boolean {
        var isValid = true

        val c1 : String = txtIdc?.text.toString()
        val c2 : String = txtNombres?.text.toString()
        val c3 : String = txtApellidos?.text.toString()
        val c4 : String = txtFechaNac?.text.toString()
        val c5 : String = txtTitulo?.text.toString()
        val c6 : String = txtEmail?.text.toString()
        val c7 : String = txtFacultad?.text.toString()

        with(binding){
            if(c1.isBlank()){
                isValid = false
                builder.setTitle("No se pueden dejar campos sin llenar")
                    .setPositiveButton("Ok"){dialogInterface, it ->
                        dialogInterface.cancel()
                    }.show()

            }
            else if(c2.isBlank() && c3.isBlank() && c4.isBlank() && c5.isBlank() && c6.isBlank() && c7.isBlank()){
                isValid = false
                builder.setTitle("No existe el registro")
                    .setPositiveButton("Ok"){dialogInterface, it ->
                        dialogInterface.cancel()
                    }.show()
            } else {
                Toast.makeText(activity, "Wah", Toast.LENGTH_SHORT).show()
            }
        }
        return isValid
    }

    private fun resetText(){
        txtNombres?.setText("")
        txtApellidos?.setText("")
        txtFechaNac?.setText("")
        txtTitulo?.setText("")
        txtEmail?.setText("")
        txtFacultad?.setText("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}