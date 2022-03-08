package live.bolder.hustest;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieDetails implements Serializable
{
    public static class SpokenLanguage implements Serializable
    {
        @SerializedName("english_name")
        @Expose
        public String englishName;
        @SerializedName("iso_639_1")
        @Expose
        public String iso6391;
        @SerializedName("name")
        @Expose
        public String name;
        private final static long serialVersionUID = -5979818892622252680L;

        @Override
        public int hashCode() {
            int result = 1;
            result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
            result = ((result* 31)+((this.englishName == null)? 0 :this.englishName.hashCode()));
            result = ((result* 31)+((this.iso6391 == null)? 0 :this.iso6391 .hashCode()));
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof SpokenLanguage) == false) {
                return false;
            }
            SpokenLanguage rhs = ((SpokenLanguage) other);
            return ((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.englishName == rhs.englishName)||((this.englishName!= null)&&this.englishName.equals(rhs.englishName))))&&((this.iso6391 == rhs.iso6391)||((this.iso6391 != null)&&this.iso6391 .equals(rhs.iso6391))));
        }
    }

    public static class ProductionCountry implements Serializable
    {
        @SerializedName("iso_3166_1")
        @Expose
        public String iso31661;
        @SerializedName("name")
        @Expose
        public String name;
        private final static long serialVersionUID = -967449403760225842L;

        @Override
        public int hashCode() {
            int result = 1;
            result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
            result = ((result* 31)+((this.iso31661 == null)? 0 :this.iso31661 .hashCode()));
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof ProductionCountry) == false) {
                return false;
            }
            ProductionCountry rhs = ((ProductionCountry) other);
            return (((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.iso31661 == rhs.iso31661)||((this.iso31661 != null)&&this.iso31661 .equals(rhs.iso31661))));
        }

    }

    public static class ProductionCompany implements Serializable
    {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("logo_path")
        @Expose
        public String logoPath;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("origin_country")
        @Expose
        public String originCountry;
        private final static long serialVersionUID = -5772087338997807467L;

        @Override
        public int hashCode() {
            int result = 1;
            result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
            result = ((result* 31)+((this.originCountry == null)? 0 :this.originCountry.hashCode()));
            result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
            result = ((result* 31)+((this.logoPath == null)? 0 :this.logoPath.hashCode()));
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof ProductionCompany) == false) {
                return false;
            }
            ProductionCompany rhs = ((ProductionCompany) other);
            return (((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.originCountry == rhs.originCountry)||((this.originCountry!= null)&&this.originCountry.equals(rhs.originCountry))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.logoPath == rhs.logoPath)||((this.logoPath!= null)&&this.logoPath.equals(rhs.logoPath))));
        }

    }

    public static class Genre implements Serializable
    {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        private final static long serialVersionUID = 1567534305433551194L;

        @Override
        public int hashCode() {
            int result = 1;
            result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
            result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof Genre) == false) {
                return false;
            }
            Genre rhs = ((Genre) other);
            return (((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))));
        }

    }

    public static class BelongsToCollection implements Serializable
    {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("poster_path")
        @Expose
        public String posterPath;
        @SerializedName("backdrop_path")
        @Expose
        public String backdropPath;
        private final static long serialVersionUID = -2384321971741026358L;

        @Override
        public int hashCode() {
            int result = 1;
            result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
            result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
            result = ((result* 31)+((this.backdropPath == null)? 0 :this.backdropPath.hashCode()));
            result = ((result* 31)+((this.posterPath == null)? 0 :this.posterPath.hashCode()));
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof BelongsToCollection) == false) {
                return false;
            }
            BelongsToCollection rhs = ((BelongsToCollection) other);
            return (((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.backdropPath == rhs.backdropPath)||((this.backdropPath!= null)&&this.backdropPath.equals(rhs.backdropPath))))&&((this.posterPath == rhs.posterPath)||((this.posterPath!= null)&&this.posterPath.equals(rhs.posterPath))));
        }

    }

    @SerializedName("adult")
    @Expose
    public Boolean adult;
    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath;
    @SerializedName("belongs_to_collection")
    @Expose
    public BelongsToCollection belongsToCollection;
    @SerializedName("budget")
    @Expose
    public Integer budget;
    @SerializedName("genres")
    @Expose
    public List<Genre> genres = null;
    @SerializedName("homepage")
    @Expose
    public String homepage;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("imdb_id")
    @Expose
    public String imdbId;
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
    @SerializedName("production_companies")
    @Expose
    public List<ProductionCompany> productionCompanies = null;
    @SerializedName("production_countries")
    @Expose
    public List<ProductionCountry> productionCountries = null;
    @SerializedName("release_date")
    @Expose
    public String releaseDate;
    @SerializedName("revenue")
    @Expose
    public Integer revenue;
    @SerializedName("runtime")
    @Expose
    public Integer runtime;
    @SerializedName("spoken_languages")
    @Expose
    public List<SpokenLanguage> spokenLanguages = null;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("tagline")
    @Expose
    public String tagline;
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
    private final static long serialVersionUID = 2377220067244922264L;

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.imdbId == null)? 0 :this.imdbId.hashCode()));
        result = ((result* 31)+((this.video == null)? 0 :this.video.hashCode()));
        result = ((result* 31)+((this.title == null)? 0 :this.title.hashCode()));
        result = ((result* 31)+((this.productionCountries == null)? 0 :this.productionCountries.hashCode()));
        result = ((result* 31)+((this.revenue == null)? 0 :this.revenue.hashCode()));
        result = ((result* 31)+((this.genres == null)? 0 :this.genres.hashCode()));
        result = ((result* 31)+((this.popularity == null)? 0 :this.popularity.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.budget == null)? 0 :this.budget.hashCode()));
        result = ((result* 31)+((this.posterPath == null)? 0 :this.posterPath.hashCode()));
        result = ((result* 31)+((this.overview == null)? 0 :this.overview.hashCode()));
        result = ((result* 31)+((this.voteAverage == null)? 0 :this.voteAverage.hashCode()));
        result = ((result* 31)+((this.releaseDate == null)? 0 :this.releaseDate.hashCode()));
        result = ((result* 31)+((this.belongsToCollection == null)? 0 :this.belongsToCollection.hashCode()));
        result = ((result* 31)+((this.runtime == null)? 0 :this.runtime.hashCode()));
        result = ((result* 31)+((this.originalLanguage == null)? 0 :this.originalLanguage.hashCode()));
        result = ((result* 31)+((this.originalTitle == null)? 0 :this.originalTitle.hashCode()));
        result = ((result* 31)+((this.tagline == null)? 0 :this.tagline.hashCode()));
        result = ((result* 31)+((this.spokenLanguages == null)? 0 :this.spokenLanguages.hashCode()));
        result = ((result* 31)+((this.backdropPath == null)? 0 :this.backdropPath.hashCode()));
        result = ((result* 31)+((this.voteCount == null)? 0 :this.voteCount.hashCode()));
        result = ((result* 31)+((this.adult == null)? 0 :this.adult.hashCode()));
        result = ((result* 31)+((this.productionCompanies == null)? 0 :this.productionCompanies.hashCode()));
        result = ((result* 31)+((this.homepage == null)? 0 :this.homepage.hashCode()));
        result = ((result* 31)+((this.status == null)? 0 :this.status.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MovieDetails) == false) {
            return false;
        }
        MovieDetails rhs = ((MovieDetails) other);
        return ((((((((((((((((((((((((((this.imdbId == rhs.imdbId)||((this.imdbId!= null)&&this.imdbId.equals(rhs.imdbId)))&&((this.video == rhs.video)||((this.video!= null)&&this.video.equals(rhs.video))))&&((this.title == rhs.title)||((this.title!= null)&&this.title.equals(rhs.title))))&&((this.productionCountries == rhs.productionCountries)||((this.productionCountries!= null)&&this.productionCountries.equals(rhs.productionCountries))))&&((this.revenue == rhs.revenue)||((this.revenue!= null)&&this.revenue.equals(rhs.revenue))))&&((this.genres == rhs.genres)||((this.genres!= null)&&this.genres.equals(rhs.genres))))&&((this.popularity == rhs.popularity)||((this.popularity!= null)&&this.popularity.equals(rhs.popularity))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.budget == rhs.budget)||((this.budget!= null)&&this.budget.equals(rhs.budget))))&&((this.posterPath == rhs.posterPath)||((this.posterPath!= null)&&this.posterPath.equals(rhs.posterPath))))&&((this.overview == rhs.overview)||((this.overview!= null)&&this.overview.equals(rhs.overview))))&&((this.voteAverage == rhs.voteAverage)||((this.voteAverage!= null)&&this.voteAverage.equals(rhs.voteAverage))))&&((this.releaseDate == rhs.releaseDate)||((this.releaseDate!= null)&&this.releaseDate.equals(rhs.releaseDate))))&&((this.belongsToCollection == rhs.belongsToCollection)||((this.belongsToCollection!= null)&&this.belongsToCollection.equals(rhs.belongsToCollection))))&&((this.runtime == rhs.runtime)||((this.runtime!= null)&&this.runtime.equals(rhs.runtime))))&&((this.originalLanguage == rhs.originalLanguage)||((this.originalLanguage!= null)&&this.originalLanguage.equals(rhs.originalLanguage))))&&((this.originalTitle == rhs.originalTitle)||((this.originalTitle!= null)&&this.originalTitle.equals(rhs.originalTitle))))&&((this.tagline == rhs.tagline)||((this.tagline!= null)&&this.tagline.equals(rhs.tagline))))&&((this.spokenLanguages == rhs.spokenLanguages)||((this.spokenLanguages!= null)&&this.spokenLanguages.equals(rhs.spokenLanguages))))&&((this.backdropPath == rhs.backdropPath)||((this.backdropPath!= null)&&this.backdropPath.equals(rhs.backdropPath))))&&((this.voteCount == rhs.voteCount)||((this.voteCount!= null)&&this.voteCount.equals(rhs.voteCount))))&&((this.adult == rhs.adult)||((this.adult!= null)&&this.adult.equals(rhs.adult))))&&((this.productionCompanies == rhs.productionCompanies)||((this.productionCompanies!= null)&&this.productionCompanies.equals(rhs.productionCompanies))))&&((this.homepage == rhs.homepage)||((this.homepage!= null)&&this.homepage.equals(rhs.homepage))))&&((this.status == rhs.status)||((this.status!= null)&&this.status.equals(rhs.status))));
    }

}