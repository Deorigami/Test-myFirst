package com.cmp.template.feature_auth.di

import com.cmp.template.core_navigation.GlobalNavigation
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("com.cmp.template.feature_auth")
class FeatureAuthModule

interface FeatureAuthNavigation : GlobalNavigation {
    fun navigateToSingpassLogin()
}

