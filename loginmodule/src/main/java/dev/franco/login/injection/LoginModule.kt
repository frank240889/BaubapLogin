package dev.franco.login.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.franco.login.data.LoginRepository
import dev.franco.login.data.LoginRepositoryImpl
import dev.franco.login.data.source.FakeLoginApiImpl
import dev.franco.login.data.source.LoginApi
import dev.franco.login.helper.ResourceManager
import dev.franco.login.helper.ResourceManagerImpl
import dev.franco.login.usecase.LoginUseCase
import dev.franco.login.usecase.LoginUseCaseImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class LoginModule {
    @Binds
    abstract fun bindsLoginUseCase(
        loginUseCaseImpl: LoginUseCaseImpl,
    ): LoginUseCase

    @Binds
    abstract fun bindsLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl,
    ): LoginRepository

    @Binds
    abstract fun bindsLoginApi(
        loginApiImpl: FakeLoginApiImpl,
    ): LoginApi

    @Binds
    abstract fun bindsResourceManager(
        resourceManagerImpl: ResourceManagerImpl,
    ): ResourceManager
}
