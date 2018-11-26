package github.runnlin.io.mygank.http

/**
 * @author Runnlin
 * @date 2018/11/21/0021.
 */
data class ResultsBean(
    var id:String,
    var createAt:String,
    var desc:String,
    var images:List<String>,
    var publishedAt:String,
    var source:String,
    var type:String,
    var url:String,
    var used:Boolean,
    var who:String

)