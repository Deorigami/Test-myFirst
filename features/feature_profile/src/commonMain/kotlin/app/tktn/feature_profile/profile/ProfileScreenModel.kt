package app.tktn.feature_profile.profile

import app.tktn.core_feature.base.BaseScreenModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ProfileScreenModel : BaseScreenModel<ProfileScreenState, ProfileScreenEvent>(ProfileScreenState()) {
    override fun onEvent(event: ProfileScreenEvent) {

    }
}
