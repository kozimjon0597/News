package uz.kozimjon.news.paper_db

import io.paperdb.Paper
import uz.kozimjon.news.model.SavedNew

object Favourites {
    private const val FAVOURITES_DB_NAME = "books"

    fun existFavourite(new: SavedNew): Boolean {
        val favourites = Paper.book().read<ArrayList<SavedNew>>(FAVOURITES_DB_NAME)
        var isExist = false
        if (!favourites.isNullOrEmpty()) {
            for (favourite in favourites) {
                if (favourite == new) {
                    isExist = true
                    break
                }
            }
        }
        return isExist
    }

    fun addFavourite(new: SavedNew) {
        val favourites = Paper.book().read<ArrayList<SavedNew>>(FAVOURITES_DB_NAME)
        if (favourites.isNullOrEmpty()) {
            val favourite = ArrayList<SavedNew>()
            favourite.add(new)
            Paper.book().write(FAVOURITES_DB_NAME, favourite)
        } else {
            favourites.add(new)
            Paper.book().write(FAVOURITES_DB_NAME, favourites)
        }
    }

    fun removeFavourite(new: SavedNew) {
        val favourites = Paper.book().read<ArrayList<SavedNew>>(FAVOURITES_DB_NAME)
        favourites?.remove(new)
        Paper.book().write(FAVOURITES_DB_NAME, favourites!!)
    }

    fun getFavourites(): ArrayList<SavedNew> {
        return Paper.book().read(FAVOURITES_DB_NAME, arrayListOf())!!
    }
}