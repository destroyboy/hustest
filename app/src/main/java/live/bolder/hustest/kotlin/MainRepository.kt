package live.bolder.hustest.kotlin

class MainRepository constructor(private val retrofitService: MovieInterface) {

    suspend fun getAllMovies() = retrofitService.getAllMovies()

}