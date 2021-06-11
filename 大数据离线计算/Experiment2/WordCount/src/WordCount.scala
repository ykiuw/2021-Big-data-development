import org.ansj.recognition.impl.StopRecognition
import org.ansj.splitWord.analysis.ToAnalysis
import org.apache.commons.lang3.StringUtils
import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {
    //spark的相关配置
    val conf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val sc = new SparkContext(conf)
    //对结果增加过滤,支持词性过滤,和词语过滤
    val filter = new StopRecognition()
    filter.insertStopNatures("w")

    //从西游记小说中读取数据
    val rdd = sc.textFile("C:\\Users\\86733\\Documents\\training\\学生所需资料-class2\\02.数据集\\西游记.txt")
      .map { x =>
        val str = if (x.length > 0)
        //开始分词，将结果使用逗号隔开
          ToAnalysis.parse(x).recognition(filter).toStringWithOutNature(",")
        str.toString
      }.flatMap(x => x.split(","))
    val result = rdd.map((_, 1)).groupBy(_._1).mapValues(_.size).filter(x => StringUtils.isNotBlank(x._1))
    //result.foreach(println)
    //统计如来出现的次数并输出结果
    result.filter(x => x._1.equals("如来")).foreach(println)
    val sorted = result.sortBy(_._2, false).filter(x=>x._1.length>=2)
    //输出前五个最多的词
    sorted.collect().take(5).foreach(println)
    //输出所有结果到本文中
    sorted.map(x=>{x._1+","+x._2}).saveAsTextFile("C:\\Users\\86733\\Desktop\\"+System.nanoTime())
  }
}
