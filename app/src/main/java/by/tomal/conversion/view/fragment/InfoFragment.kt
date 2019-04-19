package by.tomal.conversion.view.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.tomal.conversion.rtt.R
import kotlinx.android.synthetic.main.fragment_info.*
import android.content.Intent
import android.content.ActivityNotFoundException
import android.net.Uri


class InfoFragment : Fragment() {
    lateinit var attachActivity: Activity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRateApp.setOnClickListener {
            val uri = Uri.parse("market://details?id=${attachActivity.packageName}")
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            goToMarket.addFlags(
                Intent.FLAG_ACTIVITY_NO_HISTORY or
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            )
            try {
                startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=${attachActivity.packageName}")
                    )
                )
            }
        }
        btnCloseInfo.setOnClickListener {
            attachActivity.onBackPressed()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        attachActivity = context as Activity
    }

}