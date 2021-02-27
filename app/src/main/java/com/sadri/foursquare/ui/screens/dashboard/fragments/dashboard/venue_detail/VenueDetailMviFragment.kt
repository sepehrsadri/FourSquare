package com.sadri.foursquare.ui.screens.dashboard.fragments.dashboard.venue_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sadri.foursquare.R
import com.sadri.foursquare.models.MyPoint
import com.sadri.foursquare.ui.navigation.NavigationCommand
import com.sadri.foursquare.ui.utils.mvi.BaseMviFragment
import com.sadri.foursquare.ui.utils.setSrcCompat
import com.sadri.foursquare.ui.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_venue_detail.*
import timber.log.Timber

@AndroidEntryPoint
class VenueDetailMviFragment :
    BaseMviFragment<VenueDetailViewState, VenueDetailIntent, VenueDetailMviViewModel>() {
    override val viewModel: VenueDetailMviViewModel by viewModels()

    private val args: VenueDetailMviFragmentArgs by navArgs()

    override fun render(viewState: VenueDetailViewState) {
        super.render(viewState)
        updateUI(viewState.result)
    }

    private lateinit var callIntent: CallToAction
    private lateinit var locationIntent: CallToAction

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_venue_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.dispatch(VenueDetailIntent.Fetch(args.venueId))

        addViewsListeners()
    }

    override fun container(): View = detailContainer

    private fun addViewsListeners() {
        backIv.setOnClickListener {
            navigate(NavigationCommand.Back)
        }

        callBtn.setOnClickListener {
            if (this::callIntent.isInitialized.not()) {
                showCallToActionErrorToast()
            }
            handleCallToAction(callIntent)
        }

        directionBtn.setOnClickListener {
            if (this::locationIntent.isInitialized.not()) {
                showCallToActionErrorToast()
            }
            handleCallToAction(locationIntent)
        }
    }

    private fun handleCallToAction(action: CallToAction) {
        try {
            requireActivity()
                .startActivity(
                    action.intent
                )
        } catch (ex: Exception) {
            Timber.e(ex, "Error accrued on ${action.tag}")
            showCallToActionErrorToast()
        }
    }

    private fun showCallToActionErrorToast() {
        requireContext().snackBar(
            R.string.error_unknown,
            detailContainer
        )
    }

    private fun updateUI(it: VenueDetailDataModel) {
        nameTv.text = it.name

        when (val category = it.category) {
            is Category.Available -> categoryTv.text = category.value
            is Category.Empty -> categoryTv.setText(category.defaultText)
        }

        when (val rate = it.rate) {
            is Rate.Available -> {
                ratingBar.rating = rate.value
                ratingTv.text = rate.value.toString()
            }
            is Rate.Empty -> {
                ratingTv.setText(rate.defaultText)
            }
        }

        when (val availability = it.availabilityStatus) {
            is AvailabilityStatus.Close,
            is AvailabilityStatus.Open -> {
                openStatusTv.text = availability.status
            }
            is AvailabilityStatus.Undefined -> {
                openStatusTv.setText(availability.defaultValue)
            }
        }
        isOpenIv.setSrcCompat(it.availabilityStatus.icon)

        when (val phoneContact = it.phoneContact) {
            is PhoneContact.Enabled -> {
                callBtn.isEnabled = true
                this.callIntent = CallToAction.Phone(phoneContact.number)
            }
            is PhoneContact.Disabled -> callBtn.isEnabled = false
        }

        when (val direction = it.direction) {
            is Direction.Available -> {
                addressTv.text = direction.address
                this.locationIntent = CallToAction.Location(direction.myPoint)

            }
            is Direction.Empty -> {
                addressTv.text = ""
            }
        }

        when (val description = it.description) {
            is Description.Available -> descriptionTv.text = description.text
            is Description.Empty -> descriptionTv.setText(description.defaultText)
        }

        when (val like = it.likes) {
            is Like.Available -> likesTv.text = like.value.toString()
            is Like.Empty -> likesTv.setText(like.defaultText)
        }
    }

    companion object {
        private const val LOCATION_CTA_TAG = "LOCATION_CTA"
        private const val PHONE_CALL_CTA_TAG = "PHONE_CALL_CTA"
    }

    sealed class CallToAction(val intent: Intent, val tag: String) {
        data class Location(val myPoint: MyPoint) : CallToAction(
            Intent(Intent.ACTION_VIEW)
                .apply {
                    this.data =
                        Uri.parse("http://maps.google.com/maps?daddr=${myPoint.lat},${myPoint.lng}")
                },
            LOCATION_CTA_TAG
        )

        data class Phone(val phoneNumber: String) : CallToAction(
            Intent(Intent.ACTION_DIAL)
                .apply {
                    this.data = Uri.parse("tel:$phoneNumber")
                },
            PHONE_CALL_CTA_TAG
        )
    }

}