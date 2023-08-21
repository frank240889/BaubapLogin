package dev.franco.baubbap.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.franco.baubbap.validator.CurpValidator
import dev.franco.baubbap.validator.NipValidator
import dev.franco.baubbap.validator.PhoneValidator
import dev.franco.baubbap.validator.Validator
import javax.inject.Qualifier

@InstallIn(ViewModelComponent::class)
@Module
abstract class ValidationsModule {

    @Binds
    @dev.franco.baubbap.injection.CurpValidator
    abstract fun bindsCurpValidator(
        curpValidator: CurpValidator,
    ): Validator

    @Binds
    @dev.franco.baubbap.injection.PhoneValidator
    abstract fun bindsPhoneValidator(
        phoneValidator: PhoneValidator,
    ): Validator

    @Binds
    @dev.franco.baubbap.injection.NipValidator
    abstract fun bindsNipValidator(
        phoneValidator: NipValidator,
    ): Validator
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class CurpValidator

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class PhoneValidator

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class NipValidator
