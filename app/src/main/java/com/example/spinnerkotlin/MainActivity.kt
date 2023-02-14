package com.example.spinnerkotlin

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.DialogFragment
import com.example.spinnerkotlin.databinding.ActivityMainBinding

var boton: Button? = null
var cajaFecha: EditText? = null

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var aaCountries: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)



        aaCountries = ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_dropdown_item)

        aaCountries.addAll(listOf("Elige tu destino con Flybondi", "Argentina", "Brasil", "Paraguay"))
        //aaCountries.add("Bolivia")

        mBinding.spinerCountries.onItemSelectedListener = this
        mBinding.spinerCountries.adapter = aaCountries

        inicializarComponentes();

        val btnOpenURL: Button=findViewById(R.id.btnOpenURL)

        btnOpenURL.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://flybondi.com/ar")

            startActivity(openURL)
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val countrySelected = aaCountries.getItem(position)
        mBinding.tvSelected.text = "El pais de destino es: $countrySelected"
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    private fun inicializarComponentes() {
        boton = findViewById(R.id.btnFecha)
        cajaFecha = findViewById(R.id.etCajaFecha)
        boton?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val Dialogfecha = DatePickerFragment {year, month, day -> mostrarResultado(year, month, day) }
        Dialogfecha.show(supportFragmentManager,"DatePicker")
    }

    private fun mostrarResultado(year: Int, month: Int, day: Int) {
        cajaFecha?.setText("$year/$month/$day")
    }

    class DatePickerFragment (val listener: (year: Int, month: Int, day: Int)-> Unit): DialogFragment(), DatePickerDialog.OnDateSetListener{

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = java.util.Calendar.getInstance()
            var year = c.get(Calendar.YEAR)
            var month = c.get(Calendar.MONTH)
            var day = c.get(Calendar.DAY_OF_MONTH)

            return DatePickerDialog(requireActivity(), this, year, month, day)
        }

        override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
            listener(year, month+1, day)
        }

    }
}