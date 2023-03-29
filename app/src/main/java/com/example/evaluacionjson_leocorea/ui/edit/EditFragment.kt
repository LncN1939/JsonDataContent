package com.example.evaluacionjson_leocorea.ui.edit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.JsonObjectRequest
import com.example.evaluacionjson_leocorea.databinding.FragmentEditBinding
import org.json.JSONException
import java.util.regex.Pattern
import android.widget.AdapterView
import android.widget.ArrayAdapter

class EditFragment : Fragment(), AdapterView.OnItemSelectedListener{
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    val apiLink = "http://192.168.100.15:8080/coordinaccion/edit_data.php"
    private lateinit var builder : AlertDialog.Builder
    private lateinit var spinnerID: Spinner
    private var IdList = ArrayList<String>()
    private lateinit var idCAdapter: ArrayAdapter<String>
    var txtIdc: EditText?=null
    var txtNombres: EditText?=null
    var txtApellidos: EditText?=null
    var txtFechaNac: EditText?=null
    var txtTitulo: EditText?=null
    var txtEmail: EditText?=null
    var txtFacultad: EditText?=null
    var idSelected: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
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
        builder = AlertDialog.Builder(activity!!)

        spinnerID = binding.spinner
        binding.spinner.onItemSelectedListener = this

        val reqQueue: RequestQueue = Volley.newRequestQueue(activity)
        val url = "http://192.168.100.15:8080/coordinaccion/read_data.php"
        val request = JsonObjectRequest(Request.Method.GET,url,null, { res ->
                try {
                    val jsonArray = res.getJSONArray("data")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val IDC = jsonObject.optString("idC")
                        IdList.add(IDC)
                        idCAdapter = ArrayAdapter(
                            activity!!, android.R.layout.simple_spinner_item,
                            IdList
                        )
                        idCAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinner.adapter = idCAdapter
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            },
            Response.ErrorListener { err -> }
            )
        reqQueue.add(request)

        binding.txtIdc.doAfterTextChanged {
            Log.d("WARNING","${txtIdc!!.getText()}")
            Toast.makeText(getActivity(), txtIdc!!.getText(), Toast.LENGTH_SHORT).show()

            val reqQueue1: RequestQueue = Volley.newRequestQueue(getActivity())
            val apiLink2 = "http://192.168.100.15:8080/coordinaccion/select_data.php?idC=${txtIdc!!.getText()}"

            val request = JsonObjectRequest(Request.Method.GET,apiLink2,null, { res ->
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

        binding.btnSubmit.setOnClickListener {
            if (validateForm() && validateEmail()) {
               val reqQueue: RequestQueue = Volley.newRequestQueue(activity)
                val resultadoPost = object : StringRequest(Request.Method.POST, apiLink,
                    Response.Listener { res ->
                        Toast.makeText(
                            context,
                            "El usuario ha sido editado exitosamente",
                            Toast.LENGTH_LONG
                        ).show() },
                        Response.ErrorListener { err ->
                            Toast.makeText(context, "Error al editar el usuario", Toast.LENGTH_LONG)
                                .show()
                        }
                    ) {
                    override fun getParams(): MutableMap<String, String>? {
                        val parametros = HashMap<String, String>()
                        parametros.put("idC", txtIdc?.text.toString())
                        parametros.put("nombres", txtNombres?.text.toString())
                        parametros.put("apellidos", txtApellidos?.text.toString())
                        parametros.put("fechaNac", txtFechaNac?.text.toString())
                        parametros.put("titulo", txtTitulo?.text.toString())
                        parametros.put("email", txtEmail?.text.toString())
                        parametros.put("facultad", txtFacultad?.text.toString())
                        return parametros
                        }
                    }
                    reqQueue.add(resultadoPost)
                    builder.setTitle("Usuario Editado")
                        .setPositiveButton("Ok") { dialogInterface, it ->
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
        val c1 : String = txtIdc?.text.toString()
        val c2 : String = txtNombres?.text.toString()
        val c3 : String = txtApellidos?.text.toString()
        val c4 : String = txtFechaNac?.text.toString()
        val c5 : String = txtTitulo?.text.toString()
        val c6 : String = txtEmail?.text.toString()
        val c7 : String = txtFacultad?.text.toString()

        with(binding){

        if(c1.isBlank() || c2.isBlank() || c3.isBlank() || c4.isBlank() || c5.isBlank() || c6.isBlank() || c7.isBlank()){
                isValid = false
                builder.setTitle("No se pueden dejar datos vacios")
                    .setPositiveButton("Ok"){dialogInterface, it ->
                        dialogInterface.cancel()
                    }.show()
            }
        }
        return isValid
    }

    private fun showDatePicker() {
        val datePicker = DatePickerFragmentEdit {day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(parentFragmentManager,"datePicker")
    }

    fun onDateSelected(day:Int, month:Int, year:Int){
        binding.txtFechaNac.setText("${year.toString()}-${month.toString()}-${day.toString()}")
    }

    fun isValidEmail(): Boolean{
        val pattern = Pattern.compile(
            //"^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$"
            "^[A-Za-z0-9+_.-]+@est\\.uca\\.edu\\.ni$"
        )
        val matcher = pattern.matcher(txtEmail?.text.toString())
        return matcher.matches()
    }

    fun validateEmail(): Boolean{
        var isValid = true

        with(binding) {
            if (isValidEmail() == false) {
                isValid = false
                builder.setTitle("Email invalido (Debe user el @est.uca.edu.ni)")
                    .setPositiveButton("Ok") { dialogInterface, it ->
                        dialogInterface.cancel()
                    }.show()
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        idSelected = idCAdapter.getItem(position).toString()
        binding.txtIdc.setText(idSelected)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}