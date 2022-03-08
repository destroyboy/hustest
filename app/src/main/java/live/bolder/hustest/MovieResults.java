package live.bolder.hustest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MovieResults implements Serializable
{
    public class Result implements Serializable
    {

        @SerializedName("adult")
        @Expose
        public Boolean adult;
        @SerializedName("backdrop_path")
        @Expose
        public String backdropPath;
        @SerializedName("genre_ids")
        @Expose
        public List<Integer> genreIds = null;
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("original_language")
        @Expose
        public String originalLanguage;
        @SerializedName("original_title")
        @Expose
        public String originalTitle;
        @SerializedName("overview")
        @Expose
        public String overview;
        @SerializedName("popularity")
        @Expose
        public Double popularity;
        @SerializedName("poster_path")
        @Expose
        public String posterPath;
        @SerializedName("release_date")
        @Expose
        public String releaseDate;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("video")
        @Expose
        public Boolean video;
        @SerializedName("vote_average")
        @Expose
        public Double voteAverage;
        @SerializedName("vote_count")
        @Expose
        public Integer voteCount;
        private final static long serialVersionUID = 7749919017356853255L;

        @Override
        public int hashCode() {
            int result = 1;
            result = ((result* 31)+((this.overview == null)? 0 :this.overview.hashCode()));
            result = ((result* 31)+((this.voteAverage == null)? 0 :this.voteAverage.hashCode()));
            result = ((result* 31)+((this.releaseDate == null)? 0 :this.releaseDate.hashCode()));
            result = ((result* 31)+((this.video == null)? 0 :this.video.hashCode()));
            result = ((result* 31)+((this.genreIds == null)? 0 :this.genreIds.hashCode()));
            result = ((result* 31)+((this.originalLanguage == null)? 0 :this.originalLanguage.hashCode()));
            result = ((result* 31)+((this.title == null)? 0 :this.title.hashCode()));
            result = ((result* 31)+((this.originalTitle == null)? 0 :this.originalTitle.hashCode()));
            result = ((result* 31)+((this.popularity == null)? 0 :this.popularity.hashCode()));
            result = ((result* 31)+((this.backdropPath == null)? 0 :this.backdropPath.hashCode()));
            result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
            result = ((result* 31)+((this.voteCount == null)? 0 :this.voteCount.hashCode()));
            result = ((result* 31)+((this.adult == null)? 0 :this.adult.hashCode()));
            result = ((result* 31)+((this.posterPath == null)? 0 :this.posterPath.hashCode()));
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof Result) == false) {
                return false;
            }
            Result rhs = ((Result) other);
            return (((((((((((((((this.overview == rhs.overview)||((this.overview!= null)&&this.overview.equals(rhs.overview)))&&((this.voteAverage == rhs.voteAverage)||((this.voteAverage!= null)&&this.voteAverage.equals(rhs.voteAverage))))&&((this.releaseDate == rhs.releaseDate)||((this.releaseDate!= null)&&this.releaseDate.equals(rhs.releaseDate))))&&((this.video == rhs.video)||((this.video!= null)&&this.video.equals(rhs.video))))&&((this.genreIds == rhs.genreIds)||((this.genreIds!= null)&&this.genreIds.equals(rhs.genreIds))))&&((this.originalLanguage == rhs.originalLanguage)||((this.originalLanguage!= null)&&this.originalLanguage.equals(rhs.originalLanguage))))&&((this.title == rhs.title)||((this.title!= null)&&this.title.equals(rhs.title))))&&((this.originalTitle == rhs.originalTitle)||((this.originalTitle!= null)&&this.originalTitle.equals(rhs.originalTitle))))&&((this.popularity == rhs.popularity)||((this.popularity!= null)&&this.popularity.equals(rhs.popularity))))&&((this.backdropPath == rhs.backdropPath)||((this.backdropPath!= null)&&this.backdropPath.equals(rhs.backdropPath))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.voteCount == rhs.voteCount)||((this.voteCount!= null)&&this.voteCount.equals(rhs.voteCount))))&&((this.adult == rhs.adult)||((this.adult!= null)&&this.adult.equals(rhs.adult))))&&((this.posterPath == rhs.posterPath)||((this.posterPath!= null)&&this.posterPath.equals(rhs.posterPath))));
        }

    }

    @SerializedName("page")
    @Expose
    public Integer page;
    @SerializedName("results")
    @Expose
    public List<Result> results = null;
    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;
    @SerializedName("total_results")
    @Expose
    public Integer totalResults;
    private final static long serialVersionUID = 4863039869660721753L;

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.totalPages == null)? 0 :this.totalPages.hashCode()));
        result = ((result* 31)+((this.totalResults == null)? 0 :this.totalResults.hashCode()));
        result = ((result* 31)+((this.page == null)? 0 :this.page.hashCode()));
        result = ((result* 31)+((this.results == null)? 0 :this.results.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MovieResults) == false) {
            return false;
        }
        MovieResults rhs = ((MovieResults) other);
        return (((((this.totalPages == rhs.totalPages)||((this.totalPages!= null)&&this.totalPages.equals(rhs.totalPages)))&&((this.totalResults == rhs.totalResults)||((this.totalResults!= null)&&this.totalResults.equals(rhs.totalResults))))&&((this.page == rhs.page)||((this.page!= null)&&this.page.equals(rhs.page))))&&((this.results == rhs.results)||((this.results!= null)&&this.results.equals(rhs.results))));
    }

}

