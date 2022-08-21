package uz.kozimjon.news.di.component

import dagger.Component
import uz.kozimjon.news.activity.MainActivity
import uz.kozimjon.news.di.module.NetworkModule
import uz.kozimjon.news.fragment.CategoryFragment
import uz.kozimjon.news.fragment.HomeFragment
import uz.kozimjon.news.fragment.SearchFragment
import uz.kozimjon.news.fragment.ThreeFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(categoryFragment: CategoryFragment)
    fun inject(threeFragment: ThreeFragment)
    fun inject(searchFragment: SearchFragment)
}