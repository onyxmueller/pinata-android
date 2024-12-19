package net.onyxmueller.pinataandroiddemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import net.onyxmueller.pinataandroiddemo.adapter.FileAdapter
import net.onyxmueller.pinataandroiddemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModelFactory: MainViewModelFactory = MainViewModelFactory()
    private val viewModel: MainViewModel by lazy {
        viewModelFactory.create(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main,
        ).apply {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
            adapter = FileAdapter()
        }
    }
}
