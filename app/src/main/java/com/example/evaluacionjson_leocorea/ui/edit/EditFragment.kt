package com.example.evaluacionjson_leocorea.ui.edit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
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
import com.example.evaluacionjson_leocorea.databinding.FragmentEditBinding
import com.example.evaluacionjson_leocorea.databinding.FragmentNotificationsBinding
import com.example.evaluacionjson_leocorea.ui.dashboard.User
import com.example.evaluacionjson_leocorea.ui.dashboard.UserAdapter

class EditFragment : Fragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    var userList = arrayListOf<User>()
    val apiLink = "http://192.168.100.15:8080/coordinaccion/edit_data.php"
    var recycleView: RecyclerView? = null
    var btnEliminar: Button? = null
    var txtIdc: EditText?=null
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


        binding.txtIdc.doAfterTextChanged {
            Log.d("WARNING","${txtIdc!!.getText()}")
            Toast.makeText(getActivity(), txtIdc!!.getText(), Toast.LENGTH_SHORT).show()

            val reqQueue1: RequestQueue = Volley.newRequestQueue(getActivity())
            val apiLink = "http://192.168.100.15:8080/coordinaccion/select_data.php?idC=${txtIdc!!.getText()}"

            val request = JsonObjectRequest(Request.Method.GET,apiLink,null, { res ->
                txtNombres?.setText(res.getString("nombres"))
                txtApellidos?.setText(res.getString("apellidos"))
                txtFechaNac?.setText(res.getString("fechaNac"))
                txtTitulo?.setText(res.getString("titulo"))
                txtEmail?.setText(res.getString("email"))
                txtFacultad?.setText(res.getString("facultad"))
            }, { err ->
                Toast.makeText(getActivity(), "Error ${err.toString()}", Toast.LENGTH_LONG).show()
            })
            reqQueue1.add(request)

        }
/*          val reqQueue1: RequestQueue = Volley.newRequestQueue(getActivity())
            val apiLink = "http://192.168.100.15:8080/coordinaccion/read_60.php?id=${txtIdc.toString()}"
            Log.d("WARNING","${txtIdc.toString()}")
*/

        binding.btnSubmit.setOnClickListener {
            Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show()
            val reqQueue: RequestQueue = Volley.newRequestQueue(getActivity())
            val resultadoPost = object : StringRequest(Request.Method.POST,apiLink,
                Response.Listener { res ->
                    Toast.makeText(context,"El usuario ha sido editado exitosamente", Toast.LENGTH_LONG).show()
                },
                Response.ErrorListener { err ->
                    Toast.makeText(context,"Error al editar el usuario", Toast.LENGTH_LONG).show()
                }
            ){
                override fun getParams(): MutableMap<String, String>? {
                    val parametros = HashMap<String,String>()
                    parametros.put("idC",txtIdc?.text.toString())
                    parametros.put("nombres",txtNombres?.text.toString())
                    parametros.put("apellidos",txtApellidos?.text.toString())
                    parametros.put("fechaNac",txtFechaNac?.text.toString())
                    parametros.put("titulo",txtTitulo?.text.toString())
                    parametros.put("email",txtEmail?.text.toString())
                    parametros.put("facultad",txtFacultad?.text.toString())
                    return parametros
                }
            }
            reqQueue.add(resultadoPost)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}