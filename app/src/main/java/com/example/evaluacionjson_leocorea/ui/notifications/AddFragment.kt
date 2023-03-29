package com.example.evaluacionjson_leocorea.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.evaluacionjson_leocorea.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var builder : AlertDialog.Builder
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
        _binding = FragmentAddBinding.inflate(inflater, container, false)
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
        builder = AlertDialog.Builder(activity!!)

        binding.btnSubmit.setOnClickListener() {
            if(validateForm()){
                val url = "http://192.168.100.15:8080/coordinaccion/add_data.php"
                val queue = Volley.newRequestQueue(activity)
                var resultadoPost = object : StringRequest(Request.Method.POST, url,
                    Response.Listener<String> { response ->
                        Toast.makeText(
                            activity,
                            "Usuario ha sido insertado existosamente",
                            Toast.LENGTH_LONG
                        ).show()
                    }, Response.ErrorListener { error ->
                        Log.d("ERROR","$error")
                        Toast.makeText(activity, "Error: $error", Toast.LENGTH_LONG).show()
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

                builder.setTitle("Registro agregado")
                    .setPositiveButton("Ok"){dialogInterface, it ->
                        dialogInterface.cancel()
                    }.show()

            }
        }

        binding.txtFechaNac.setOnClickListener {
            showDatePicker()
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        val c1 : String = txtNombres?.text.toString()
        val c2 : String = txtApellidos?.text.toString()
        val c3 : String = txtFechaNac?.text.toString()
        val c4 : String = txtTitulo?.text.toString()
        val c5 : String = txtEmail?.text.toString()
        val c6 : String = txtFacultad?.text.toString()

        with(binding){
            if(c1.isBlank() || c2.isBlank() || c3.isBlank() || c4.isBlank() || c5.isBlank() || c6.isBlank()){
                isValid = false
                builder.setTitle("No se pueden dejar campos sin llenar")
                    .setPositiveButton("Ok"){dialogInterface, it ->
                        dialogInterface.cancel()
                    }.show()
            }
        }
        return isValid
    }

    private fun showDatePicker() {
        val datePicker = DatePickerFragment {day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(getParentFragmentManager(),"datePicker")
    }

    fun onDateSelected(day:Int, month:Int, year:Int){
        binding.txtFechaNac.setText("${year.toString()}-${month.toString()}-${day.toString()}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}