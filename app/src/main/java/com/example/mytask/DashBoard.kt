package com.example.mytask

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.mytask.RoomDB.DatabaseClient
import com.example.mytask.model.Productfile
import com.google.android.material.navigation.NavigationView

class DashBoard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var edt_name: EditText
    lateinit var edt_desc: EditText
    lateinit var edt_price: EditText
    lateinit var edt_quanitiy: EditText
    lateinit var edt_pid: EditText
    lateinit var btn_add: Button
    lateinit var cart_view: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        edt_name = findViewById(R.id.edt_name)
        edt_pid = findViewById(R.id.edt_pid)
        edt_desc = findViewById(R.id.edt_desc)
        edt_price = findViewById(R.id.edt_price)
        edt_quanitiy = findViewById(R.id.edt_quanitiy)
        cart_view = findViewById(R.id.cart_view)
        btn_add = findViewById(R.id.btn_add)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        btn_add.setOnClickListener(View.OnClickListener {
            if (edt_pid.getText().toString().isEmpty()) {
                edt_pid.setError("please enter Pid ")
            } else if (edt_name.getText().toString().isEmpty()) {
                edt_name.setError("please enter Name")
            } else if (edt_desc.getText().toString().isEmpty()) {
                edt_desc.setError("please enter Description")
            } else if (edt_price.getText().toString().isEmpty()) {
                edt_price.setError("please enter Price")
            } else if (edt_quanitiy.getText().toString().isEmpty()) {
                edt_quanitiy.setError("please enter Quantity")
            } else {
                val productfile = Productfile()
                productfile.count = 0
                productfile.description = edt_desc.getText().toString()
                productfile.name = edt_name.getText().toString()
                productfile.quanitiy = "0"
                productfile.pid = edt_pid.getText().toString()
                productfile.price = Integer.valueOf(edt_price.getText().toString())
                insertintolocal(productfile)
            }
        })
        cart_view.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@DashBoard, ViewCart::class.java)
            startActivity(intent)
        })
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun insertintolocal(productfile: Productfile) {
        class SaveTask : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg params: Void?): Void? {
                DatabaseClient.getInstance(this@DashBoard)?.appDatabase
                        ?.taskDao()
                        ?.insert(productfile)
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                edt_name.setText(null)
                edt_desc.setText(null)
                edt_price.setText(null)
                edt_quanitiy.setText(null)
                edt_pid.setText(null)
                Toast.makeText(this@DashBoard, "Product  Added Successfuly", Toast.LENGTH_SHORT).show()
            }


        }

        val st = SaveTask()
        st.execute()
    }

    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun displaySelectedScreen(itemId: Int) {
        when (itemId) {
            R.id.nav_view_product -> {
                val intent = Intent(this@DashBoard, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_view_cart -> {
                val intentHome = Intent(this@DashBoard, ViewCart::class.java)
                startActivity(intentHome)
            }
        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.itemId)
        //make this method blank
        return true
    }
}